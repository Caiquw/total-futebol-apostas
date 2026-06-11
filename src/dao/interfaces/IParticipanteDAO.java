package dao.interfaces;

import model.Grupo;
import model.Participante;
import java.util.List;

// Contrato para persistência de Participante
public interface IParticipanteDAO {
    void salvar(Participante participante, Grupo grupo);
    List<Participante> listarTodos();
    Participante buscarPorNome(String nome);
    int buscarId(String nome);
    void atualizarPontuacao(Participante participante); // chamado após registrar resultado de partida
    List<Participante> listarPorGrupo(String nomeGrupo);
}
