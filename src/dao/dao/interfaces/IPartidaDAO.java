package dao.dao.interfaces;

import model.Campeonato;
import model.Partida;
import java.util.List;

// Contrato para persistência de Partida
public interface IPartidaDAO {
    void salvar(Partida partida, Campeonato campeonato);
    List<Partida> listarTodos();
    int buscarId(String mandante, String visitante, String dataHora);
    void atualizarResultado(Partida partida); // chamado quando o admin registra o placar final
}
