package dao.binario;

import dao.interfaces.IClubeDAO;
import model.Clube;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Implementação de arquivo binário de IClubeDAO
// Persiste a lista inteira de clubes num arquivo .bin usando serialização Java
// Clube precisa implementar Serializable para isso funcionar
public class ClubeDAOBinario implements IClubeDAO {

    // cada entidade tem seu próprio arquivo binário
    private static final String ARQUIVO = "clubes.bin";

    @Override
    public void salvar(Clube clube) {
        // lê a lista atual, adiciona o novo clube e regrava o arquivo inteiro
        List<Clube> clubes = listarTodos();
        clubes.add(clube);
        salvarLista(clubes);
    }

    @Override
    public List<Clube> listarTodos() {
        // tenta abrir o arquivo; se não existir ainda, retorna lista vazia
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Clube>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao ler clubes do arquivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Clube buscarPorNome(String nome) {
        for (Clube c : listarTodos())
            if (c.getNome().equals(nome)) return c;
        return null;
    }

    @Override
    public int buscarId(String nome) {
        // arquivo binário não usa IDs numéricos de banco — retorna posição na lista como identificador
        List<Clube> clubes = listarTodos();
        for (int i = 0; i < clubes.size(); i++)
            if (clubes.get(i).getNome().equals(nome)) return i;
        return -1;
    }

    // método auxiliar que grava a lista inteira no arquivo
    private void salvarLista(List<Clube> clubes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(clubes);
        } catch (IOException e) {
            System.out.println("Erro ao gravar clubes no arquivo: " + e.getMessage());
        }
    }
}
