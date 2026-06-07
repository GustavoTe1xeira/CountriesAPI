# Sistema de Gerenciamento de Países

## Descrição
Sistema Java que consome a API REST Countries para buscar informações sobre países e armazená-las em um banco de dados SQLite com operações CRUD completas.

## Tecnologias Utilizadas
- Java 11+
- SQLite (banco de dados)
- Gson (conversão JSON)
- JDBC (persistência)

## Estrutura do Projeto
```
src/
├── model/
│   └── Country.java
├── dao/
│   └── CountryDAO.java
├── service/
│   └── ApiService.java
├── util/
│   └── DatabaseUtil.java
└── Main.java
```

## Como Executar

### Pré-requisitos
- Java JDK 11 ou superior instalado
- Bibliotecas necessárias:
  - sqlite-jdbc-3.42.0.0.jar
  - gson-2.10.1.jar

### Passos para Execução

1. **Compilar o projeto:**
```bash
javac -cp ".;lib/*" -d bin src/**/*.java src/*.java
```

2. **Executar o sistema:**
```bash
java -cp ".;bin;lib/*" Main
```

### Verificar o ambiente (rápido)

Antes de compilar, você pode checar rapidamente se o JDK e as bibliotecas estão disponíveis rodando o script de diagnóstico:

```bat
.\verify-environment.bat
```
O script valida `java`, `javac`, a presença das JARs em `lib/` e arquivos-fonte essenciais.

### Opção alternativa (se tiver Maven)
Se preferir usar Maven, o sistema criará automaticamente as dependências.

## Funcionalidades

### Menu Principal
1. **Buscar país da API e salvar** - Busca um país pelo nome na API e salva no banco
2. **Listar todos os países** - Exibe todos os países armazenados
3. **Buscar país por ID** - Busca um país específico pelo ID
4. **Atualizar país** - Atualiza informações de um país existente
5. **Deletar país** - Remove um país do banco de dados
6. **Sair** - Encerra o sistema

## Estrutura do Banco de Dados

### Tabela: paises
```sql
CREATE TABLE paises (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    nome_oficial TEXT,
    capital TEXT,
    regiao TEXT,
    sub_regiao TEXT,
    populacao INTEGER,
    area REAL,
    bandeira_url TEXT
);
```

## Exemplo de Uso
1. Execute o programa
2. Escolha a opção 1 para buscar um país (ex: "Brazil")
3. O sistema buscará na API e salvará no banco
4. Use as outras opções para listar, atualizar ou deletar

## Autor
Gustavo Teixeira 
