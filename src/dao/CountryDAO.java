package dao;

import model.Country;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList; 
import java.util.List;

public class CountryDAO {

    public boolean inserir(Country country) {
        String sql = "INSERT INTO paises (nome, nome_oficial, capital, regiao, sub_regiao, " +
                     "populacao, area, bandeira_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, country.getNome());
            pstmt.setString(2, country.getNomeOficial());
            pstmt.setString(3, country.getCapital());
            pstmt.setString(4, country.getRegiao());
            pstmt.setString(5, country.getSubRegiao());
            pstmt.setLong(6, country.getPopulacao());
            pstmt.setDouble(7, country.getArea());
            pstmt.setString(8, country.getBandeiraUrl());

            int linhasAfetadas = pstmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✓ País inserido com sucesso no banco de dados!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("✗ Erro ao inserir país: " + e.getMessage());
        }
        return false;
    }

    public List<Country> listarTodos() {
        List<Country> paises = new ArrayList<>();
        String sql = "SELECT * FROM paises ORDER BY nome";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country pais = new Country();
                pais.setId(rs.getInt("id"));
                pais.setNome(rs.getString("nome"));
                pais.setNomeOficial(rs.getString("nome_oficial"));
                pais.setCapital(rs.getString("capital"));
                pais.setRegiao(rs.getString("regiao"));
                pais.setSubRegiao(rs.getString("sub_regiao"));
                pais.setPopulacao(rs.getLong("populacao"));
                pais.setArea(rs.getDouble("area"));
                pais.setBandeiraUrl(rs.getString("bandeira_url"));
                paises.add(pais);
            }
        } catch (SQLException e) {
            System.err.println("✗ Erro ao listar países: " + e.getMessage());
        }
        return paises;
    }

    public Country buscarPorId(int id) {
        String sql = "SELECT * FROM paises WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Country pais = new Country();
                pais.setId(rs.getInt("id"));
                pais.setNome(rs.getString("nome"));
                pais.setNomeOficial(rs.getString("nome_oficial"));
                pais.setCapital(rs.getString("capital"));
                pais.setRegiao(rs.getString("regiao"));
                pais.setSubRegiao(rs.getString("sub_regiao"));
                pais.setPopulacao(rs.getLong("populacao"));
                pais.setArea(rs.getDouble("area"));
                pais.setBandeiraUrl(rs.getString("bandeira_url"));
                return pais;
            }
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar país: " + e.getMessage());
        }
        return null;
    }

    public List<Country> buscarPorNome(String nome) {
        List<Country> paises = new ArrayList<>();
        String sql = "SELECT * FROM paises WHERE nome LIKE ? ORDER BY nome";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nome + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Country pais = new Country();
                pais.setId(rs.getInt("id"));
                pais.setNome(rs.getString("nome"));
                pais.setNomeOficial(rs.getString("nome_oficial"));
                pais.setCapital(rs.getString("capital"));
                pais.setRegiao(rs.getString("regiao"));
                pais.setSubRegiao(rs.getString("sub_regiao"));
                pais.setPopulacao(rs.getLong("populacao"));
                pais.setArea(rs.getDouble("area"));
                pais.setBandeiraUrl(rs.getString("bandeira_url"));
                paises.add(pais);
            }
        } catch (SQLException e) {
            System.err.println("✗ Erro ao buscar países por nome: " + e.getMessage());
        }
        return paises;
    }

    public boolean atualizar(Country country) {
        String sql = "UPDATE paises SET nome = ?, nome_oficial = ?, capital = ?, " +
                     "regiao = ?, sub_regiao = ?, populacao = ?, area = ?, bandeira_url = ? " +
                     "WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, country.getNome());
            pstmt.setString(2, country.getNomeOficial());
            pstmt.setString(3, country.getCapital());
            pstmt.setString(4, country.getRegiao());
            pstmt.setString(5, country.getSubRegiao());
            pstmt.setLong(6, country.getPopulacao());
            pstmt.setDouble(7, country.getArea());
            pstmt.setString(8, country.getBandeiraUrl());
            pstmt.setInt(9, country.getId());

            int linhasAfetadas = pstmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✓ País atualizado com sucesso!");
                return true;
            } else {
                System.out.println("✗ Nenhum país encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            System.err.println("✗ Erro ao atualizar país: " + e.getMessage());
        }
        return false;
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM paises WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int linhasAfetadas = pstmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✓ País deletado com sucesso!");
                return true;
            } else {
                System.out.println("✗ Nenhum país encontrado com o ID informado.");
            }
        } catch (SQLException e) {
            System.err.println("✗ Erro ao deletar país: " + e.getMessage());
        }
        return false;
    }

    public int contarPaises() {
        String sql = "SELECT COUNT(*) as total FROM paises";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("✗ Erro ao contar países: " + e.getMessage());
        }
        return 0;
    }
}
