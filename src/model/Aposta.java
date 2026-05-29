package model;

public class Aposta implements Int_Exibir {
    private Participante participante; //quem fez a aposta
    private Partida partida; //em qual jogo apostou
    private int golsMandantePalpite;
    private int golsVisitantePalpite;
    private int pontuacao; //pontos ganhos após o resultado

    //CONSTRUTORES
    public Aposta() { // padrao sem info
        this.participante = null;
        this.partida = null;
        this.golsMandantePalpite = 0;
        this.golsVisitantePalpite = 0;
        this.pontuacao = 0;
    }

    public Aposta(Participante participante, Partida partida,
                  int golsMandantePalpite, int golsVisitantePalpite) { // sobrecarregado
        this.participante = participante;
        this.partida = partida;
        this.golsMandantePalpite = golsMandantePalpite;
        this.golsVisitantePalpite = golsVisitantePalpite;
        this.pontuacao = 0; //comeca 0 pois so dps da partida terminar será calculada
    }

    // GETTERS E SETTERS
    // pontuacao não tem setter —> ela só muda através do calcularPontuacao()
    public Participante getParticipante() {
        return this.participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Partida getPartida() {
        return this.partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public int getGolsMandantePalpite() {
        return this.golsMandantePalpite;
    }

    public void setGolsMandantePalpite(int golsMandantePalpite) {
        this.golsMandantePalpite = golsMandantePalpite;
    }

    public int getGolsVisitantePalpite() {
        return this.golsVisitantePalpite;
    }

    public void setGolsVisitantePalpite(int golsVisitantePalpite) {
        this.golsVisitantePalpite = golsVisitantePalpite;
    }

    public int getPontuacao() {
        return this.pontuacao;
    }

    // METODO DE CALCULAR PONTUACAO
    public int calcularPontuacao() {
        if (!partida.isFinalizada()) {
            System.out.println("Partida ainda não foi finalizada!");
            return 0;
        }

        String resultadoReal = partida.getResultado();
        String resultadoPalpite = calcularResultadoPalpite();

        if (!resultadoReal.equals(resultadoPalpite)) {
            pontuacao = 0; //errou
        } else if (golsMandantePalpite == partida.getGolsMandante()
                && golsVisitantePalpite == partida.getGolsVisitante()) {
            pontuacao = 10; //acertou resultado e o placar
        } else {
            pontuacao = 5; //acertou resultado
        }

        return pontuacao;
    }

    // METODO DE AUXILIO
    // é private pois somente o calcularPontuacao usa
    private String calcularResultadoPalpite() {
        if (golsMandantePalpite > golsVisitantePalpite) {
            return partida.getMandante().getNome();
        } else if (golsVisitantePalpite > golsMandantePalpite) {
            return partida.getVisitante().getNome();
        } else {
            return "Empate";
        }
    }

    // INTERFACE
    @Override
    public String Exibir() {
        return "Aposta de: " + participante.getNome() +
                " | Partida: " + partida.getMandante().getNome() +
                " x " + partida.getVisitante().getNome() +
                " | Palpite: " + golsMandantePalpite +
                "x" + golsVisitantePalpite +
                " | Pontuação: " + pontuacao;
    }

}
