
CREATE TABLE IF NOT EXISTS paises (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    nome_oficial TEXT,
    capital TEXT,
    regiao TEXT,
    sub_regiao TEXT,
    populacao INTEGER,
    area REAL,
    bandeira_url TEXT,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE INDEX IF NOT EXISTS idx_nome ON paises(nome);
CREATE INDEX IF NOT EXISTS idx_regiao ON paises(regiao);

