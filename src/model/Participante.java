package model;

public class Participante extends Pessoa{
    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    public void setPontuacaoTotal(int pontuacaoTotal) {
        this.pontuacaoTotal = pontuacaoTotal;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    private int pontuacaoTotal;
    // grupo em que o participante faz parte
    private Grupo grupo;

    public Participante(String nome, String login) {
        super(nome, login);
        this.pontuacaoTotal = 0;
    }

    public String getTipo(){
        return "Participante";
    }

    public Participante(){
        super();
        this.pontuacaoTotal = 0 ;
    }

    public void adicionarPontos(int pontos) {
        this.pontuacaoTotal += pontos;
    }
    @Override
    public String getTipoUsuario() {
        return "";
    }
}
