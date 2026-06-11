package dao.dao;

import dao.CampeonatoDAO;
import dao.ClubeDAO;
import dao.ConexaoDB;
import model.Campeonato;
import model.Clube;
import model.Partida;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {

    public void salvar(Partida partida, Campeonato campeonato) {
        String sql = "INSERT INTO partidas (mandante_id, visitante_id, campeonato_id, data_hora, gols_mandante, gols_visitante, finalizada) VALUES (?, ?, ?, ?, ?, ?, ?)";
        dao.ClubeDAO clubeDAO = new ClubeDAO();
        try (Connection conn = dao.ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clubeDAO.buscarId(partida.getMandante().getNome()));
            stmt.setInt(2, clubeDAO.buscarId(partida.getVisitante().getNome()));
            stmt.setInt(3, new CampeonatoDAO().buscarId(campeonato.getNome()));
            stmt.setString(4, partida.getDataHora().toString());
            stmt.setInt(5, partida.getGolsMandante());
            stmt.setInt(6, partida.getGolsVisitante());
            stmt.setInt(7, partida.isFinalizada() ? 1 : 0);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar partida: " + e.getMessage());
        }
    }

    public List<Partida> listarTodos() {
        List<Partida> partidas = new ArrayList<>();
        String sql = """
            SELECT p.id, p.data_hora, p.gols_mandante, p.gols_visitante, p.finalizada,
                   cm.nome as mandante_nome, cm.estado as mandante_estado,
                   cv.nome as visitante_nome, cv.estado as visitante_estado
            FROM partidas p
            JOIN clubes cm ON p.mandante_id  = cm.id
            JOIN clubes cv ON p.visitante_id = cv.id
        """;
        try (Connection conn = dao.ConexaoDB.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Clube mandante  = new Clube(rs.getString("mandante_nome"),  rs.getString("mandante_estado"));
                Clube visitante = new Clube(rs.getString("visitante_nome"), rs.getString("visitante_estado"));
                LocalDateTime dataHora = LocalDateTime.parse(rs.getString("data_hora"));
                Partida partida = new Partida(mandante, visitante, dataHora);
                if (rs.getInt("finalizada") == 1) {
                    partida.registrarResultado(rs.getInt("gols_mandante"), rs.getInt("gols_visitante"));
                }
                partidas.add(partida);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar partidas: " + e.getMessage());
        }
        return partidas;
    }

    public int buscarId(String mandante, String visitante, String dataHora) {
        String sql = """
            SELECT p.id FROM partidas p
            JOIN clubes cm ON p.mandante_id  = cm.id
            JOIN clubes cv ON p.visitante_id = cv.id
            WHERE cm.nome = ? AND cv.nome = ? AND p.data_hora = ?
        """;
        try (Connection conn = dao.ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mandante);
            stmt.setString(2, visitante);
            stmt.setString(3, dataHora);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            System.out.println("Erro ao buscar id da partida: " + e.getMessage());
        }
        return -1;
    }

    public void atualizarResultado(Partida partida) {
        String sql = """
            UPDATE partidas SET gols_mandante = ?, gols_visitante = ?, finalizada = 1
            WHERE mandante_id  = (SELECT id FROM clubes WHERE nome = ?)
              AND visitante_id = (SELECT id FROM clubes WHERE nome = ?)
              AND data_hora    = ?
        """;
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, partida.getGolsMandante());
            stmt.setInt(2, partida.getGolsVisitante());
            stmt.setString(3, partida.getMandante().getNome());
            stmt.setString(4, partida.getVisitante().getNome());
            stmt.setString(5, partida.getDataHora().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar resultado: " + e.getMessage());
        }
    }
}
