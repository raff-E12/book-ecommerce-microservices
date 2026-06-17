import { input } from '@inquirer/prompts';
import { CheckConnection } from './scripts/heart-db.js';
import { gatherInput } from './scripts/options-launcher.js';
import { CreateDataBase, RunQueryFile, DropAndRecreateDB } from "./scripts/start-db.js"

async function mainCLI(){
    console.log('╔══════════════════════════════╗');
    console.log('║         DB Init Tool         ║');
    console.log('╚══════════════════════════════╝');

    const config = await gatherInput(); // Ritorno input
    const checks = await CheckConnection(config); // Controllo di Connessione DB
    const query = String("./data/Four-Mod_[In Use]/third-scheme-[in_use].sql"); // Path del File Corrente

    switch (config.action) {
        // Creazione del Database
        case 'create':
        console.log('-- Avvio creazione database + schema...\n');
        await CreateDataBase(config);
        await RunQueryFile(config, query);
        break;
       
        // Esecuzione di Modifiche incrementali
        case 'migrate':
        console.log('-- Avvio migration...\n'); 
        await RunQueryFile(config, query);
        break;
       
        // Riscrittura del Database da Zero
        case 'reset':
        console.log('-- Avvio reset completo...\n');
        await DropAndRecreateDB(config);
        await RunQueryFile(config, query);
        break;

        // Cambia File SQL e Riscrive il Database
        case 'change':
        const changeSQL = await input({
            message: "Percorso del file SQL da eseguire:",
            default: "./db/schema.sql"
        });

        await DropAndRecreateDB(config);
        await RunQueryFile(config, changeSQL);
        break;

        // Verifica la connessione al DB
        case 'check':
        await CheckConnection(config, 12);
        break;
       
        // Raccolta errore in Default
        default:
        console.error(`-- Azione non riconosciuta: ${config.action}`);
        process.exit(1);
    }

    console.log('-- Operazione completata con successo!\n');
}

// Cattura errore durante la Creazione
mainCLI().catch(err => {
  console.error('\n-- Errore fatale:', err.message);
  process.exit(1);
});
