import { CheckConnection } from './scripts/heart-db.js';
import { gatherInput } from './scripts/options-launcher.js';
import { CreateDataBase, RunQueryFile, DropAndRecreateDB } from "./scripts/start-db.js"

async function mainCLI(){
    console.log('╔══════════════════════════════╗');
    console.log('║         DB Init Tool         ║');
    console.log('╚══════════════════════════════╝');

    const config = await gatherInput(); // Ritorno input
    const checks = await CheckConnection(config);
    const query = String("./data/Four-Mod_[In Use]/third-scheme-[in_use].sql");

    switch (config.action) {
        case 'create':
        console.log('-- Avvio creazione database + schema...\n');
        await CreateDataBase(config);
        await RunQueryFile(config, query);
        break;
       
        case 'migrate':
        console.log('-- Avvio migration...\n');
        await RunQueryFile(config, query);
        break;
       
        case 'reset':
        console.log('-- Avvio reset completo...\n');
        await DropAndRecreateDB(config);
        await RunQueryFile(config, query);
        break;
       
        default:
        console.error(`-- Azione non riconosciuta: ${config.action}`);
        process.exit(1);
    }

    console.log('-- Operazione completata con successo!\n');
}

mainCLI().catch(err => {
  console.error('\n-- Errore fatale:', err.message);
  process.exit(1);
});
