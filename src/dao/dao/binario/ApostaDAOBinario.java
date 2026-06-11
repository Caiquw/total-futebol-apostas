package dao.dao.binario;

import dao.interfaces.IApostaDAO;
import model.Aposta;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ApostaDAOBinario implements IApostaDAO {

    private static final String ARQUIVO = "apostas.bin";

    @Override
    public void salvar(Aposta aposta) {
        List<Aposta> lista = listarTodos();
        lista.add(aposta);
        salvarLista(lista);
    }

    @Override
    public List<Aposta> listarTodos() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Aposta>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao ler apostas do arquivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void atualizarPontuacao(Aposta aposta) {
        List<Aposta> lista = listarTodos();
        for (int i = 0; i < lista.size(); i++) {
            Aposta a = lista.get(i);
            if (a.getParticipante().getNome().equals(aposta.getParticipante().getNome())
                    && a.getPartida().getMandante().getNome().equals(aposta.getPartida().getMandante().getNome())
                    && a.getPartida().getVisitante().getNome().equals(aposta.getPartida().getVisitante().getNome())
                    && a.getPartida().getDataHora().equals(aposta.getPartida().getDataHora())) {
                lista.set(i, aposta);
                break;
            }
        }
        salvarLista(lista);
    }

    private void salvarLista(List<Aposta> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.out.println("Erro ao gravar apostas no arquivo: " + e.getMessage());
        }
    }
}
