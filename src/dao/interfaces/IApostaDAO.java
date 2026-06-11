package dao.interfaces;

import model.Aposta;
import java.util.List;

// Contrato para persistência de Aposta
public interface IApostaDAO {
    void salvar(Aposta aposta);
    List<Aposta> listarTodos();
    void atualizarPontuacao(Aposta aposta); // chamado após calcular pontos de cada aposta
}
