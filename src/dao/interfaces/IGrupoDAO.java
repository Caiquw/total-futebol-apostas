package dao.interfaces;

import model.Grupo;
import java.util.List;

// Contrato para persistência de Grupo
public interface IGrupoDAO {
    void salvar(Grupo grupo);
    List<Grupo> listarTodos();
    Grupo buscarPorNome(String nome);
    int buscarId(String nome);
}
