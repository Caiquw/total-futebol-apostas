package dao;

import model.Campeonato;
import model.Clube;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CampeonatoDAO {

    public void salvar(Campeonato campeonato) {
        String sql = "INSERT INTO campeonatos (nome, ano) VALUES (?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, campeonato.getNome());
            stmt.setInt(2, campeonato.getAno());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar campeonato: " + e.getMessage());
        }
    }

    public List<Campeonato> listarTodos() {
        List<Campeonato> campeonatos = new ArrayList<>();
        String sql = "SELECT * FROM campeonatos";
        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Campeonato c = new Campeonato(rs.getString("nome"), rs.getInt("ano"));
                c.setClubes(listarClubesDoCampeonato(rs.getInt("id")));
                campeonatos.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar campeonatos: " + e.getMessage());
        }
        return campeonatos;
    }

    public Campeonato buscarPorNome(String nome) {
        String sql = "SELECT * FROM campeonatos WHERE nome = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Campeonato c = new Campeonato(rs.getString("nome"), rs.getInt("ano"));
                c.setClubes(listarClubesDoCampeonato(rs.getInt("id")));
                return c;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar campeonato: " + e.getMessage());
        }
        return null;
    }

    public int buscarId(String nome) {
        String sql = "SELECT id FROM campeonatos WHERE nome = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            System.out.println("Erro ao buscar id do campeonato: " + e.getMessage());
        }
        return -1;
    }

    public void adicionarClube(Campeonato campeonato, Clube clube) {
        String sql = "INSERT OR IGNORE INTO campeonato_clubes (campeonato_id, clube_id) VALUES (?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, buscarId(campeonato.getNome()));
            stmt.setInt(2, new ClubeDAO().buscarId(clube.getNome()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar clube ao campeonato: " + e.getMessage());
        }
    }

    private List<Clube> listarClubesDoCampeonato(int campeonatoId) {
        List<Clube> clubes = new ArrayList<>();
        String sql = """
            SELECT c.nome, c.estado FROM clubes c
            JOIN campeonato_clubes cc ON c.id = cc.clube_id
            WHERE cc.campeonato_id = ?
        """;
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, campeonatoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clubes.add(new Clube(rs.getString("nome"), rs.getString("estado")));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clubes do campeonato: " + e.getMessage());
        }
        return clubes;
    }
}
