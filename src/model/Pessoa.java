package model;

abstract class Pessoa implements Int_Exibir {

    public Pessoa(String nome, String login) {
        this.nome = nome;
        this.login = login;
    }

    private String nome;
    private String login;

    public Pessoa() {
        this.nome = "";
        this.login = "";
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    // identificar tipo no ADM ou PARTICIPANTE
    public abstract String  getTipoUsuario();


    @Override
    public String Exibir() {

        return "[ " + getTipoUsuario() + " ] " + nome + " (login: " + login + ")";
    }

    @Override
    public String toString(){
        return nome;
    }

}
