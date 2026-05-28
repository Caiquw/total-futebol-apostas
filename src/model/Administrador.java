package model;

public class Administrador extends Pessoa {

    // construtor
    public Administrador(){
        super(); //serve para chamar o constru de pessoa
    }

    public Administrador(String nome, String login) {
        super(nome, login);
    }

    @Override
    public String getTipoUsuario() {   // no pessoa é abstrato os filhos que se virem - definiu string
        return "Administrador"; // definiu que é um adm
    }

    @Override
    public String Exibir() {  // sobrescreve falando "adm carlos na hora de exibir
        return "[ADMINISTRADOR] " + getNome();
    }

}


