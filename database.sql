-- Script SQL para criação do banco de dados
-- Banco: SQLite
-- Tabela: paises

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

-- Índices para melhorar performance de busca
CREATE INDEX IF NOT EXISTS idx_nome ON paises(nome);
CREATE INDEX IF NOT EXISTS idx_regiao ON paises(regiao);

-- Exemplos de consultas úteis:

-- Listar todos os países
-- SELECT * FROM paises;

-- Buscar país por nome
-- SELECT * FROM paises WHERE nome LIKE '%Brazil%';

-- Contar países por região
-- SELECT regiao, COUNT(*) as total FROM paises GROUP BY regiao;

-- Países com maior população
-- SELECT nome, populacao FROM paises ORDER BY populacao DESC LIMIT 10;
