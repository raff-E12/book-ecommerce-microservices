import pg from 'pg';
import fs   from 'fs';
import path from 'path';

const { Client } = pg;

// Connessione Client e Raccolta Query in Lista
function parseStatments(file) {
    return file
    .split(';')
    .map(s => s.replace(/--.*$/gm, '').trim()) // Rimuove commenti
    .filter(s => s.length > 0);
}

async function CreateClient(config, database) {
    const client = new Client({
        host:     config.host,
        port:     config.port,
        user:     config.user,
        password: config.password,
        database,
    });
    await client.connect();
    return client;
}

// Lettura e Esecuzione Query Disponibili
export async function RunQueryFile(config, filePath) {
    const resolved = path.resolve(filePath);
    
      if (!fs.existsSync(resolved)) {
        console.error(`X File SQL non trovato: ${resolved}`);
        process.exit(1);
      }
    
      console.log(`--- Lettura file: ${resolved}\n`);
      const sql = fs.readFileSync(resolved, 'utf-8');
      const statements = parseStatments(sql);
      console.log(`${statements.length} statement trovati\n`);
    
      const client = await CreateClient(config, config.dbName);
      let success  = 0;
      let failed   = 0;
    
      for (const stmt of statements) {
        const preview = stmt.slice(0, 70).replace(/\s+/g, ' ');
        try {
          await client.query(stmt);
          console.log(`-- ${preview}${stmt.length > 70 ? '...' : ''}`);
          success++;
        } catch (err) {
          console.error(`  X ${preview}...`);
          console.error(`     → ${err.message}`);
          failed++;
        }
      }
    
      await client.end();
      console.log(`\n--- Risultato: ${success} ok, ${failed} errori\n`);
}

// Ricreazione Database
export async function DropAndRecreateDB(config) {
  console.log(`\n-- Drop e ricreazione di "${config.dbName}"...\n`);
  const client = await CreateClient(config, 'postgres');

  try {

    await client.query(`
      SELECT pg_terminate_backend(pid)
      FROM pg_stat_activity
      WHERE datname = $1 AND pid <> pg_backend_pid()
    `, [config.dbName]);

    await client.query(`DROP DATABASE IF EXISTS "${config.dbName}"`);
    console.log(`-- Database "${config.dbName}" eliminato`);

    await client.query(`CREATE DATABASE "${config.dbName}"`);
    console.log(`-- Database "${config.dbName}" creato\n`);
  } finally {
    await client.end();
  }
}

// Creazione Database
export async function CreateDataBase(config) {
  const client = await CreateClient(config, 'postgres');

  try {
    const res = await client.query(
      `SELECT 1 FROM pg_database WHERE datname = $1`,
      [config.dbName]
    );

    if (res.rowCount === 0) {
      await client.query(`CREATE DATABASE "${config.dbName}"`);
      console.log(`-- Database "${config.dbName}" creato\n`);
    } else {
      console.log(`  ℹ  Database "${config.dbName}" già esistente, salto creazione\n`);
    }
  } finally {
    await client.end();
  }
}