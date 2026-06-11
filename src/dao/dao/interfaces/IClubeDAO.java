package dao.dao.interfaces;

import model.Clube;
import java.util.List;

// Contrato que todas as implementações de Clube devem seguir
// Seja JDBC, Hibernate ou Arquivo Binário
public interface IClubeDAO {
    void salvar(Clube clube);
    List<Clube> listarTodos();
    Clube buscarPorNome(String nome);
    int buscarId(String nome); // usado internamente pelos outros DAOs para montar relacionamentos
}
