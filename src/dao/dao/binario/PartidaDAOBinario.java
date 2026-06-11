package dao.dao.binario;

import dao.interfaces.IPartidaDAO;
import model.Campeonato;
import model.Partida;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAOBinario implements IPartidaDAO {

    private static final String ARQUIVO = "partidas.bin";

    @Override
    public void salvar(Partida partida, Campeonato campeonato) {
        List<Partida> lista = listarTodos();
        lista.add(partida);
        salvarLista(lista);
    }

    @Override
    public List<Partida> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Partida>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao ler partidas do arquivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public int buscarId(String mandante, String visitante, String dataHora) {
        List<Partida> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            Partida p = lista.get(i);
            if (p.getMandante().getNome().equals(mandante)
                    && p.getVisitante().getNome().equals(visitante)
                    && p.getDataHora().toString().equals(dataHora)) return i;
        }
        return -1;
    }

    @Override
    public void atualizarResultado(Partida partida) {
        List<Partida> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            Partida p = lista.get(i);
            if (p.getMandante().getNome().equals(partida.getMandante().getNome())
                    && p.getVisitante().getNome().equals(partida.getVisitante().getNome())
                    && p.getDataHora().equals(partida.getDataHora())) {
                lista.set(i, partida);
                break;
            }
        }
        salvarLista(lista);
    }

    private void salvarLista(List<Partida> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao gravar partidas no arquivo: " + e.getMessage());
        }
    }
}
