import { program } from "commander";
import { input, select, confirm, password } from '@inquirer/prompts';

// Validazione degli errori
function askValidDetail(config) {
  const errors = [];
  const port = Number(config.port);

  if (!config.host || /\s/.test(config.host)) errors.push('X Host non valido (vuoto o con spazi)');

  if (isNaN(port) || port < 1 || port > 65535) errors.push('X Porta non valida (deve essere un numero tra 1 e 65535)');

  if (!config.user || config.user.trim() === '') errors.push('X Utente non può essere vuoto');

  if (!config.password || config.password.length < 4) errors.push('X Password troppo corta (minimo 4 caratteri)');

  if (!config.dbName || !/^[a-zA-Z0-9_-]+$/.test(config.dbName)) errors.push('X Nome database non valido (solo lettere, numeri, underscore e trattini)');

  if (config.dbName && config.dbName.length > 63) errors.push('X Nome database troppo lungo (massimo 63 caratteri)');

  return errors;
}

// Raccolta Input Utente
async function collectionsInputs(options){
    
      const host = options.host ?? await input({
        message: 'Host DB:',
        default: process.env.DB_HOST ?? 'localhost',
      });
    
      let port = options.ports ?? await input({
        message: 'Porta DB:',
        default: process.env.DB_PORT ?? '5432',
      });
    
      const user = options.user ?? await input({
        message: 'Utente DB:',
        default: process.env.DB_USER ?? 'postgres',
      });
    
      const pwd = await password({
        message: 'Password DB:',
        mask: '*',
      });
    
      const dbName = options.db ?? await input({
        message: 'Nome database:',
        default: process.env.DB_NAME ?? 'book-api-dispence',
      });
    
      const action = options.action ?? await select({
        message: '\nCosa vuoi eseguire?',
        choices: [
          { name: '-- Crea database + schema', value: 'create'  },
          { name: '-- Esegui migration',        value: 'migrate' },
          { name: '-- Drop e ricrea tutto',     value: 'reset'   },
          { name: '-- Cambia SQL e ricrea tutto', value: 'change' },
          { name: '-- Verifica Connessione al DB', value: 'check' },
        ],
      });
      
      return { host, port: Number(port), user, password: pwd, dbName, action };
}

// Visione e Gestione Errori
export async function gatherInput() {

      program
        .name('db-init')
        .description('Tool per inizializzazione database PostgreSQL')
        .option('--host <host>',     'Host del database')
        .option('--port <port>',     'Porta del database')
        .option('--user <user>',     'Utente del database')
        .option('--db <name>',       'Nome del database')
        .option('--action <action>', 'Azione: create | migrate | reset | change | check')
        .parse(process.argv);
    
     const options = program.opts();  // Elenco degli input di raccolta

     while(true){
      console.log('\n-- Configurazione database\n');

      const config = await collectionsInputs(options); // Raccolta
      const errors = askValidDetail(config); // Gestione

      if (errors.length === 0) {

        console.log('\n─────────────────────────────────');
        console.log('-- Riepilogo configurazione:');
        console.log(`   Host:     ${config.host}`);
        console.log(`   Porta:    ${config.port}`);
        console.log(`   Utente:   ${config.user}`);
        console.log(`   Database: ${config.dbName}`);
        console.log(`   Azione:   ${config.action}`);
        console.log('─────────────────────────────────\n');

        const confirms = await confirm({ message: 'Confermi e procedi?', default: true });

        if (confirms) return { ...config, port: Number(config.port) };

        console.log('\n-- Ricomincia dall\'inizio...\n');
        continue;
      }

      console.log('\n─────────────────────────────────');
      console.log('-- Trovati errori di validazione:\n');
      errors.forEach(e => console.log(`   ${e}`));
      console.log('─────────────────────────────────\n');

      const retry = await confirm({ message: 'Vuoi riprovare?', default: true });

      if (!retry) {
        console.log('\n-- Operazione annullata.\n');
        process.exit(0);
      }

      console.log('\n-- Ricomincia dall\'inizio...\n');
     }
}