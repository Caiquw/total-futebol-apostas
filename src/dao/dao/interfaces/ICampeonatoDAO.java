package dao.dao.interfaces;

import model.Campeonato;
import model.Clube;
import java.util.List;

// Contrato para persistência de Campeonato
public interface ICampeonatoDAO {
    void salvar(Campeonato campeonato);
    List<Campeonato> listarTodos();
    Campeonato buscarPorNome(String nome);
    int buscarId(String nome);
    void adicionarClube(Campeonato campeonato, Clube clube); // relacionamento campeonato <-> clube
}
