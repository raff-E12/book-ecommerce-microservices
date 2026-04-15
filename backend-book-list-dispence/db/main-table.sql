-- Crea database biblioteca riservata
CREATE DATABASE biblioteca_riservata;

-- Connettersi: \c biblioteca_riservata

-- Schema riservato
CREATE SCHEMA IF NOT EXISTS privato;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA privato FROM PUBLIC;

-- SEQUENZA per ID INTEGER autoincrementale
CREATE SEQUENCE privato.seq_libri_id START 1 INCREMENT 1;

-- SINGOLA TABELLA LIBRI con ID INTEGER
CREATE TABLE privato.libri (
    id INTEGER PRIMARY KEY DEFAULT nextval('privato.seq_libri_id'),
    titolo VARCHAR(200) NOT NULL,
    autore VARCHAR(100) NOT NULL,
    editore VARCHAR(100),
    anno_pubblicazione INTEGER CHECK (anno_pubblicazione > 1800 AND anno_pubblicazione <= 2026),
    isbn VARCHAR(13) UNIQUE,
    categoria VARCHAR(50) DEFAULT 'Narrativa',
    num_copie INTEGER DEFAULT 1 CHECK (num_copie > 0),
    disponibile INTEGER DEFAULT 1 CHECK (disponibile >= 0),
    posizione_scaffale VARCHAR(20),
    note TEXT
);

-- INDICI per performance
CREATE INDEX idx_libri_autore ON privato.libri(autore);
CREATE INDEX idx_libri_categoria ON privato.libri(categoria);
CREATE INDEX idx_libri_disponibile ON privato.libri(disponibile);

-- 12 RECORD FITTIZI (ID generati automaticamente 1-12)
INSERT INTO privato.libri (titolo, autore, editore, anno_pubblicazione, isbn, categoria, num_copie, disponibile, posizione_scaffale, note) VALUES

('Il nome della rosa', 'Umberto Eco', 'Bompiani', 1980, '9788806235944', 'Giallo', 3, 1, 'A-12', 'Capolavoro letterario'),
('Il Gattopardo', 'Giuseppe Tomasi di Lampedusa', 'Feltrinelli', 1958, '9788807880103', 'Narrativa', 2, 2, 'B-05', 'Nobel candidato'),
('I Malavoglia', 'Giovanni Verga', 'Newton Compton', 1881, '9788854134567', 'Verismo', 1, 1, 'C-08', 'Verismo italiano'),
('La solitudine dei numeri primi', 'Paolo Giordano', 'Mondadori', 2008, '9788804576789', 'Narrativa', 4, 0, 'D-15', 'Premio Strega'),
('Io non ho paura', 'Niccolò Ammaniti', 'Einaudi', 2001, '9788806174562', 'Thriller', 2, 1, 'E-03', 'Film omonimo'),
('Seta', 'Alessandro Baricco', 'Rizzoli', 1996, '9788817123456', 'Narrativa', 3, 2, 'F-09', 'Best seller internazionale'),
('Se questo è un uomo', 'Primo Levi', 'Einaudi', 1947, '9788806214568', 'Memoir', 5, 3, 'G-02', 'Testimonianza olocausto'),
('Storia della letteratura italiana', 'Emanuele K. Bevilacqua', 'Laterza', 2015, '9788842098765', 'Saggistica', 1, 1, 'H-11', 'Manuale universitario'),
('Il sabato della memoria', 'Dacia Maraini', 'Rizzoli', 2009, '9788817041234', 'Saggistica', 2, 2, 'I-07', 'Saggi autobiografici'),
('La ragazza con la pistola d''oro', 'Giorgio Scerbanenco', 'Garzanti', 1966, '9788811678901', 'Giallo', 2, 0, 'J-04', 'Padre del giallo italiano'),
('L''isola di Arturo', 'Elsa Morante', 'Einaudi', 1957, '9788806217897', 'Narrativa', 1, 1, 'K-12', 'Premio Strega'),
('Q', 'Luther Blissett', 'Einaudi', 1999, '9788806167890', 'Storico', 3, 1, 'L-06', 'Collective pseudonimo');
