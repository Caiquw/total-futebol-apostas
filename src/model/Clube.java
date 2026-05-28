package model;

public class Clube extends EntEsportiva{
    private String estado;

    //Construtor próprio
    public Clube(String estado) {
        this.estado = estado;
    }

    //Construtor subclasse
    public Clube(String nome, String estado) {
        super(nome);
        this.estado = estado;
    }

    public Clube() {
        super();
        this.estado = "";
    }

    //Retorna categoria de entidade esportiva
    @Override
    public String getCategoria() {
        return "Clube";
    }

    @Override
    public String Exibir(){

        return "Clube " + getNome() + " " + estado;
    }


    //Getters - Setter
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
