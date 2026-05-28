package model;

public class EntEsportiva implements Int_Exibir {
    private String nome;

    public EntEsportiva(){
        this.nome = "";
    }

    public EntEsportiva(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String Exibir(){

        return "";
    }

    @Override
    public String toString(){
        return nome;
    }



}
