CREATE TABLE IF NOT EXISTS "utenti" (
	"id" INTEGER,
	"nome_completo" VARCHAR(256) DEFAULT 'Vuoto',
	"email" VARCHAR(273) DEFAULT 'Vuoto',
	"password" VARCHAR(264) DEFAULT 'Vuoto',
	"role" VARCHAR(255) DEFAULT 'Vuoto',
	"verified" BOOLEAN DEFAULT false,
	PRIMARY KEY("id")
);




CREATE TABLE IF NOT EXISTS "libri" (
	"id" INTEGER,
	"titolo" VARCHAR(200) NOT NULL,
	"autore" VARCHAR(100) NOT NULL,
	"editore" VARCHAR(100),
	"anno_pubblicazione" INTEGER,
	"isbn" VARCHAR(13),
	"categoria" VARCHAR(50) DEFAULT 'Narrativa',
	"num_copie" INTEGER DEFAULT 1,
	"disponibile" INTEGER DEFAULT 1,
	"posizione_scaffale" VARCHAR(20),
	"note" TEXT,
	"prezzo" NUMERIC(10,2) NOT NULL,
	"cover_color" VARCHAR(20),
	"cover_img" TEXT DEFAULT 'Not Found',
	"trashed" BOOLEAN NOT NULL DEFAULT false,
	"trash_date" DATE DEFAULT 'Not Found',
	PRIMARY KEY("id")
);




CREATE TABLE IF NOT EXISTS "ordini" (
	"id" INTEGER,
	"prezzo_totale" NUMERIC(10,2),
	"ordinato" BOOLEAN DEFAULT false,
	"id_utente" INTEGER,
	PRIMARY KEY("id")
);




CREATE TABLE IF NOT EXISTS "checkout" (
	"id" INTEGER,
	"ordine_id" INTEGER NOT NULL,
	"libro_id" INTEGER NOT NULL,
	"libro_prezzo" NUMERIC(10,2) NOT NULL,
	"quantita" INTEGER NOT NULL DEFAULT 1,
	"prezzo_subtotale" NUMERIC(10,2) NOT NULL,
	PRIMARY KEY("id")
);




CREATE TABLE IF NOT EXISTS "recensioni" (
	"id" INTEGER,
	"libro_id" INTEGER NOT NULL,
	"descrizione" VARCHAR(500) DEFAULT 'Vuoto',
	"voto" INTEGER DEFAULT 0,
	"check" BOOLEAN DEFAULT false,
	"id_utente" INTEGER NOT NULL,
	PRIMARY KEY("id")
);



ALTER TABLE "ordini"
ADD FOREIGN KEY("id_utente") REFERENCES "utenti"("id")
ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE "checkout"
ADD FOREIGN KEY("libro_id") REFERENCES "libri"("id")
ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE "checkout"
ADD FOREIGN KEY("ordine_id") REFERENCES "ordini"("id")
ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE "recensioni"
ADD FOREIGN KEY("libro_id") REFERENCES "libri"("id")
ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE "recensioni"
ADD FOREIGN KEY("id_utente") REFERENCES "utenti"("id")
ON UPDATE NO ACTION ON DELETE NO ACTION;