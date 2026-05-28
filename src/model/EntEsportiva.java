package model;

public abstract class EntEsportiva implements Int_Exibir {
    private String nome;

    //Classe abstrata
    public abstract String getCategoria();

    //Construtores
    public EntEsportiva(){
        this.nome = "";
    }

    public EntEsportiva(String nome) {
        this.nome = nome;
    }

    //Getters - Setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Exibir padrão das classes mãe/super
    @Override
    public String Exibir(){

        return "[" + getCategoria() + "] " + nome;
    }

    @Override
    public String toString(){
        return nome;
    }
}
