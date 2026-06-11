package dao.jdbc;

import dao.ConexaoDB;
import dao.interfaces.IApostaDAO;
import model.Aposta;
import model.Clube;
import model.Participante;
import model.Partida;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Implementação JDBC de IApostaDAO
public class ApostaDAOJDBC implements IApostaDAO {

    @Override
    public void salvar(Aposta aposta) {
        String sql = "INSERT INTO apostas (participante_id, partida_id, gols_mandante_palpite, gols_visitante_palpite, pontuacao) VALUES (?, ?, ?, ?, ?)";
        PartidaDAOJDBC partidaDAO = new PartidaDAOJDBC();
        ParticipanteDAOJDBC participanteDAO = new ParticipanteDAOJDBC();
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            Partida p = aposta.getPartida();
            stmt.setInt(1, participanteDAO.buscarId(aposta.getParticipante().getNome()));
            stmt.setInt(2, partidaDAO.buscarId(
                p.getMandante().getNome(),
                p.getVisitante().getNome(),
                p.getDataHora().toString()
            ));
            stmt.setInt(3, aposta.getGolsMandantePalpite());
            stmt.setInt(4, aposta.getGolsVisitantePalpite());
            stmt.setInt(5, aposta.getPontuacao());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar aposta: " + e.getMessage());
        }
    }

    @Override
    public List<Aposta> listarTodos() {
        List<Aposta> apostas = new ArrayList<>();
        String sql = """
            SELECT a.gols_mandante_palpite, a.gols_visitante_palpite, a.pontuacao,
                   p.nome as part_nome, p.login,
                   cm.nome as mandante_nome, cm.estado as mandante_estado,
                   cv.nome as visitante_nome, cv.estado as visitante_estado,
                   pt.data_hora, pt.gols_mandante, pt.gols_visitante, pt.finalizada
            FROM apostas a
            JOIN participantes p  ON a.participante_id = p.id
            JOIN partidas pt      ON a.partida_id      = pt.id
            JOIN clubes cm        ON pt.mandante_id    = cm.id
            JOIN clubes cv        ON pt.visitante_id   = cv.id
        """;
        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Participante part = new Participante(rs.getString("part_nome"), rs.getString("login"));
                Clube mandante   = new Clube(rs.getString("mandante_nome"),  rs.getString("mandante_estado"));
                Clube visitante  = new Clube(rs.getString("visitante_nome"), rs.getString("visitante_estado"));
                LocalDateTime dataHora = LocalDateTime.parse(rs.getString("data_hora"));
                Partida partida = new Partida(mandante, visitante, dataHora);
                if (rs.getInt("finalizada") == 1) {
                    partida.registrarResultado(rs.getInt("gols_mandante"), rs.getInt("gols_visitante"));
                }
                apostas.add(new Aposta(part, partida, rs.getInt("gols_mandante_palpite"), rs.getInt("gols_visitante_palpite")));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar apostas: " + e.getMessage());
        }
        return apostas;
    }

    @Override
    public void atualizarPontuacao(Aposta aposta) {
        String sql = """
            UPDATE apostas SET pontuacao = ?
            WHERE participante_id = (SELECT id FROM participantes WHERE nome = ?)
              AND partida_id = (SELECT pt.id FROM partidas pt
                                JOIN clubes cm ON pt.mandante_id  = cm.id
                                JOIN clubes cv ON pt.visitante_id = cv.id
                                WHERE cm.nome = ? AND cv.nome = ? AND pt.data_hora = ?)
        """;
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            Partida p = aposta.getPartida();
            stmt.setInt(1, aposta.getPontuacao());
            stmt.setString(2, aposta.getParticipante().getNome());
            stmt.setString(3, p.getMandante().getNome());
            stmt.setString(4, p.getVisitante().getNome());
            stmt.setString(5, p.getDataHora().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pontuação da aposta: " + e.getMessage());
        }
    }
}
