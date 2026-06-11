package dao.dao.jdbc;

import dao.ConexaoDB;
import dao.interfaces.IGrupoDAO;
import dao.jdbc.CampeonatoDAOJDBC;
import model.Campeonato;
import model.Grupo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementação JDBC de IGrupoDAO
public class GrupoDAOJDBC implements IGrupoDAO {

    @Override
    public void salvar(Grupo grupo) {
        String sql = "INSERT INTO grupos (nome, campeonato_id) VALUES (?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, grupo.getNome());
            stmt.setInt(2, new CampeonatoDAOJDBC().buscarId(grupo.getCampeonato().getNome()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar grupo: " + e.getMessage());
        }
    }

    @Override
    public List<Grupo> listarTodos() {
        List<Grupo> grupos = new ArrayList<>();
        String sql = "SELECT g.id, g.nome, c.nome as camp_nome, c.ano FROM grupos g JOIN campeonatos c ON g.campeonato_id = c.id";
        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Campeonato camp = new Campeonato(rs.getString("camp_nome"), rs.getInt("ano"));
                grupos.add(new Grupo(rs.getString("nome"), camp));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar grupos: " + e.getMessage());
        }
        return grupos;
    }

    @Override
    public Grupo buscarPorNome(String nome) {
        String sql = "SELECT g.nome, c.nome as camp_nome, c.ano FROM grupos g JOIN campeonatos c ON g.campeonato_id = c.id WHERE g.nome = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Campeonato camp = new Campeonato(rs.getString("camp_nome"), rs.getInt("ano"));
                return new Grupo(rs.getString("nome"), camp);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar grupo: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int buscarId(String nome) {
        String sql = "SELECT id FROM grupos WHERE nome = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            System.out.println("Erro ao buscar id do grupo: " + e.getMessage());
        }
        return -1;
    }
}
