package dao.dao.jdbc;

import dao.ConexaoDB;
import dao.interfaces.IParticipanteDAO;
import dao.jdbc.GrupoDAOJDBC;
import model.Grupo;
import model.Participante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementação JDBC de IParticipanteDAO
public class ParticipanteDAOJDBC implements IParticipanteDAO {

    @Override
    public void salvar(Participante participante, Grupo grupo) {
        String sql = "INSERT INTO participantes (nome, login, pontuacao_total, grupo_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, participante.getNome());
            stmt.setString(2, participante.getLogin());
            stmt.setInt(3, participante.getPontuacaoTotal());
            stmt.setInt(4, new GrupoDAOJDBC().buscarId(grupo.getNome()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar participante: " + e.getMessage());
        }
    }

    @Override
    public List<Participante> listarTodos() {
        List<Participante> participantes = new ArrayList<>();
        String sql = "SELECT nome, login, pontuacao_total FROM participantes";
        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Participante p = new Participante(rs.getString("nome"), rs.getString("login"));
                p.setPontuacaoTotal(rs.getInt("pontuacao_total"));
                participantes.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar participantes: " + e.getMessage());
        }
        return participantes;
    }

    @Override
    public Participante buscarPorNome(String nome) {
        String sql = "SELECT nome, login, pontuacao_total FROM participantes WHERE nome = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Participante p = new Participante(rs.getString("nome"), rs.getString("login"));
                p.setPontuacaoTotal(rs.getInt("pontuacao_total"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar participante: " + e.getMessage());
        }
        return null;
    }

    @Override
    public int buscarId(String nome) {
        String sql = "SELECT id FROM participantes WHERE nome = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            System.out.println("Erro ao buscar id do participante: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public void atualizarPontuacao(Participante participante) {
        String sql = "UPDATE participantes SET pontuacao_total = ? WHERE nome = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, participante.getPontuacaoTotal());
            stmt.setString(2, participante.getNome());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pontuação: " + e.getMessage());
        }
    }

    @Override
    public List<Participante> listarPorGrupo(String nomeGrupo) {
        List<Participante> participantes = new ArrayList<>();
        String sql = """
            SELECT p.nome, p.login, p.pontuacao_total FROM participantes p
            JOIN grupos g ON p.grupo_id = g.id
            WHERE g.nome = ?
        """;
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeGrupo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Participante p = new Participante(rs.getString("nome"), rs.getString("login"));
                p.setPontuacaoTotal(rs.getInt("pontuacao_total"));
                participantes.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar participantes do grupo: " + e.getMessage());
        }
        return participantes;
    }
}
