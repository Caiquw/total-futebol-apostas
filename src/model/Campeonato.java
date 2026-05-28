package model;
import java.util.ArrayList;
import java.util.List;

public class Campeonato extends EntEsportiva{
    private List<Clube> clubes;
    private int ano;
    private static final int MAXIMO_CLUBES = 8;

    //Construtores
    public Campeonato(String nome, List<Clube> clubes, int ano) {
        super(nome);
        this.clubes = clubes;
        this.ano = ano;
    }

    public Campeonato() {
        super();
        this.ano = 2025;
        this.clubes = new ArrayList<>();
    }

    public Campeonato(String nome, int ano) {
        super(nome);
        this.ano = ano;
        this.clubes = new ArrayList<>();
    }

    //Retorna categoria da entidade esportiva
    @Override
    public String getCategoria() {
        return "Campeonato";
    }

    //Exibe (puxa da classe mae que puxa da interface)
    @Override
    public String Exibir(){

        return "[Campeonato] " + getNome() + " " + ano + " | Clubes: " + clubes.size() + "/" + MAXIMO_CLUBES;
    }

    // add na lista de clube pelo metodo da lista - antes valida a insercao
    public String adicionarClube(Clube clube){

        if (clubes.size() > MAXIMO_CLUBES){

            return "Limite de clubes atingido";
        }

        if (clubes.contains(clube)){

            return "Clube ja cadastrado";
        }
        clubes.add(clube);
        return "adicao deu certo";
    }


    //Getters - setters
    public List<Clube> getClubes() {
        return clubes;
    }

    public void setClubes(List<Clube> clubes) {
        this.clubes = clubes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
