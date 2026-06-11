package dao.binario;

import dao.interfaces.ICampeonatoDAO;
import model.Campeonato;
import model.Clube;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CampeonatoDAOBinario implements ICampeonatoDAO {

    private static final String ARQUIVO = "campeonatos.bin";

    @Override
    public void salvar(Campeonato campeonato) {
        List<Campeonato> lista = listarTodos();
        lista.add(campeonato);
        salvarLista(lista);
    }

    @Override
    public List<Campeonato> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Campeonato>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao ler campeonatos do arquivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Campeonato buscarPorNome(String nome) {
        for (Campeonato c : listarTodos())
            if (c.getNome().equals(nome)) return c;
        return null;
    }

    @Override
    public int buscarId(String nome) {
        List<Campeonato> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++)
            if (lista.get(i).getNome().equals(nome)) return i;
        return -1;
    }

    @Override
    public void adicionarClube(Campeonato campeonato, Clube clube) {
        List<Campeonato> lista = listarTodos();
        for (Campeonato c : lista) {
            if (c.getNome().equals(campeonato.getNome())) {
                c.adicionarClube(clube);
                break;
            }
        }
        salvarLista(lista);
    }

    private void salvarLista(List<Campeonato> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao gravar campeonatos no arquivo: " + e.getMessage());
        }
    }
}
