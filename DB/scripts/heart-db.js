import pg from "pg";

const { Client } = pg;

function sleepPromise(minute) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function TryConnectDB(configs) {
  const client = new Client({
    host:     configs.host,
    port:     configs.port,
    user:     configs.user,
    password: configs.password,
    database: 'postgres', 
    connectionTimeoutMillis: 3000
  });

  try {
    await client.connect();
    await client.query('SELECT 1');
    await client.end();
    return true;
  } catch (err) {
    await client.end().catch(() => {});
    throw err;
  }
}

export async function CheckConnection(configs, maxRetry = 5, delayRetry = 2000) {
  console.log(`\n-- Verifica connessione a ${configs.host}:${configs.port}...\n`);

  for (let attempt = 1; attempt <= maxRetry; attempt++) {
    try {
      await TryConnectDB(configs);
      console.log(`-- Database raggiungibile (tentativo ${attempt}/${maxRetry})\n`);
      return;
    } catch (err) {
      const isLast = attempt === maxRetry;
      const msg    = err.message ?? 'Errore sconosciuto';

      if (isLast) {
        console.error(`-- Database non raggiungibile dopo ${maxRetry} tentativi.`);
        console.error(`-- Ultimo errore: ${msg}\n`);
        process.exit(1);
      }

      console.warn(`-- Tentativo ${attempt}/${maxRetry} fallito: ${msg}`);
      console.warn(`-- Nuovo tentativo tra ${delayRetry / 1000}s...\n`);
      await sleepPromise(delayRetry);
    }
  }
}