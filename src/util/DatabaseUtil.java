package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String URL = "jdbc:sqlite:paises.db";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
                System.out.println("✓ Conexão com banco de dados estabelecida.");
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("✗ Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    public static void inicializarBanco() {
        String sql = "CREATE TABLE IF NOT EXISTS paises (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "nome TEXT NOT NULL," +
                     "nome_oficial TEXT," +
                     "capital TEXT," +
                     "regiao TEXT," +
                     "sub_regiao TEXT," +
                     "populacao INTEGER," +
                     "area REAL," +
                     "bandeira_url TEXT," +
                     "data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                     ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✓ Banco de dados inicializado com sucesso.");
        } catch (SQLException e) {
            System.err.println("✗ Erro ao inicializar banco de dados: " + e.getMessage());
        }
    }

    public static void fecharConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Conexão com banco de dados fechada.");
            }
        } catch (SQLException e) {
            System.err.println("✗ Erro ao fechar conexão: " + e.getMessage());
        }
    }

    public static boolean testarConexao() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
