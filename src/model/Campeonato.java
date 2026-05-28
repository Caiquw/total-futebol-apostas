package model;

public class Campeonato extends EntEsportiva{
    private String nome;

    public Campeonato(String nome) {
        super(nome);
        this.nome = nome;
    }

    @Override
    public String getNome(){
        return this.nome;
    }

}
