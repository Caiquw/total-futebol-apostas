package dao.dao;

import dao.ConexaoDB;
import model.Clube;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubeDAO {

    public void salvar(Clube clube) {
        String sql = "INSERT INTO clubes (nome, estado) VALUES (?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, clube.getNome());
            stmt.setString(2, clube.getEstado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar clube: " + e.getMessage());
        }
    }

    public List<Clube> listarTodos() {
        List<Clube> clubes = new ArrayList<>();
        String sql = "SELECT * FROM clubes";
        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clubes.add(new Clube(rs.getString("nome"), rs.getString("estado")));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clubes: " + e.getMessage());
        }
        return clubes;
    }

    public Clube buscarPorNome(String nome) {
        String sql = "SELECT * FROM clubes WHERE nome = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Clube(rs.getString("nome"), rs.getString("estado"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar clube: " + e.getMessage());
        }
        return null;
    }

    public int buscarId(String nome) {
        String sql = "SELECT id FROM clubes WHERE nome = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            System.out.println("Erro ao buscar id do clube: " + e.getMessage());
        }
        return -1;
    }
}
