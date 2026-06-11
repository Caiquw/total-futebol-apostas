package dao.dao.binario;

import dao.interfaces.IParticipanteDAO;
import model.Grupo;
import model.Participante;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParticipanteDAOBinario implements IParticipanteDAO {

    private static final String ARQUIVO = "participantes.bin";

    // Par interno para guardar participante + nome do grupo dele
    private static class Entrada implements Serializable {
        Participante participante;
        String nomeGrupo;
        Entrada(Participante p, String g) { this.participante = p; this.nomeGrupo = g; }
    }

    @Override
    public void salvar(Participante participante, Grupo grupo) {
        List<Entrada> entradas = lerEntradas();
        entradas.add(new Entrada(participante, grupo.getNome()));
        salvarEntradas(entradas);
    }

    @Override
    public List<Participante> listarTodos() {
        return lerEntradas().stream().map(e -> e.participante).collect(Collectors.toList());
    }

    @Override
    public Participante buscarPorNome(String nome) {
        for (Entrada e : lerEntradas())
            if (e.participante.getNome().equals(nome)) return e.participante;
        return null;
    }

    @Override
    public int buscarId(String nome) {
        List<Entrada> entradas = lerEntradas();
        for (int i = 0; i < entradas.size(); i++)
            if (entradas.get(i).participante.getNome().equals(nome)) return i;
        return -1;
    }

    @Override
    public void atualizarPontuacao(Participante participante) {
        List<Entrada> entradas = lerEntradas();
        for (Entrada e : entradas) {
            if (e.participante.getNome().equals(participante.getNome())) {
                e.participante.setPontuacaoTotal(participante.getPontuacaoTotal());
                break;
            }
        }
        salvarEntradas(entradas);
    }

    @Override
    public List<Participante> listarPorGrupo(String nomeGrupo) {
        return lerEntradas().stream()
                .filter(e -> e.nomeGrupo.equals(nomeGrupo))
                .map(e -> e.participante)
                .collect(Collectors.toList());
    }

    private List<Entrada> lerEntradas() {
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Entrada>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao ler participantes do arquivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void salvarEntradas(List<Entrada> entradas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(entradas);
        } catch (IOException e) {
            System.out.println("Erro ao gravar participantes no arquivo: " + e.getMessage());
        }
    }
}
