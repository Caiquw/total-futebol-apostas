package dao.dao.binario;

import dao.interfaces.IGrupoDAO;
import model.Grupo;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAOBinario implements IGrupoDAO {

    private static final String ARQUIVO = "grupos.bin";

    @Override
    public void salvar(Grupo grupo) {
        List<Grupo> lista = listarTodos();
        lista.add(grupo);
        salvarLista(lista);
    }

    @Override
    public List<Grupo> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Grupo>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao ler grupos do arquivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Grupo buscarPorNome(String nome) {
        for (Grupo g : listarTodos())
            if (g.getNome().equals(nome)) return g;
        return null;
    }

    @Override
    public int buscarId(String nome) {
        List<Grupo> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++)
            if (lista.get(i).getNome().equals(nome)) return i;
        return -1;
    }

    private void salvarLista(List<Grupo> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao gravar grupos no arquivo: " + e.getMessage());
        }
    }
}
