package model;

public class Partida implements Int_Exibir {
    private Clube mandante;
    private Clube visitante;
    private String dataHora; //controlar o limite de 20 min das apostas
    private int golsMandante; //placar
    private int golsVisitante; //placar
    private boolean finalizada; //resultado já foi registrado ou nao

    // CONSTRUTORES
    public Partida() { // partida vazia - sem info
        this.mandante = null;
        this.visitante = null;
        this.dataHora = "";
        this.golsMandante = 0;
        this.golsVisitante = 0;
        this.finalizada = false;
    }

    public Partida(Clube mandante, Clube visitante, String dataHora) { // partida criada com times e datas
        this.mandante = mandante;
        this.visitante = visitante;
        this.dataHora = dataHora;
        this.golsMandante = 0; //começa em 0 pois ainda n aconteceu
        this.golsVisitante = 0;
        this.finalizada = false; // começa em false pois ainda n aconteceu
    }
    // gols e finalizada n entram nos parametros pois a partida ainda n foi jogada


    // GETTERS E SETTERS
    // golsMandante, golsVisitante e finalizada não tem setter -> só mudam através do metodo registrarResultado()
    public Clube getMandante() {
        return this.mandante;
    }

    public void setMandante(Clube mandante) {
        this.mandante = mandante;
    }

    public Clube getVisitante() {
        return this.visitante;
    }

    public void setVisitante(Clube visitante) {
        this.visitante = visitante;
    }

    public String getDataHora() {
        return this.dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public int getGolsMandante() {
        return this.golsMandante;
    }

    public int getGolsVisitante() {
        return this.golsVisitante;
    }

    public boolean isFinalizada() {
        return this.finalizada;
    }


    //METODO DE REGISTRAR RESULTADO DA PARTIDA
    public void registrarResultado(int golsMandante, int golsVisitante) {
        if (finalizada) {
            System.out.println("Resultado já foi registrado!");
            return;
        }
        this.golsMandante = golsMandante;
        this.golsVisitante = golsVisitante;
        this.finalizada = true;
        // so deixa registrar uma vez —> se jA estiver finalizada, avisa e para
    }


    //METODO PEGAR RESULTADO
    public String getResultado() {
        if (!finalizada) {
            return "Partida ainda não finalizada";
        }
        if (golsMandante > golsVisitante) {
            return mandante.getNome();
        } else if (golsVisitante > golsMandante) {
            return visitante.getNome();
        } else {
            return "Empate";
        }
        // é chamado na APOSTA para calcular os pontos
        // retorna o nome do vencedor ou "empate"
    }

    //INTERFACE
    @Override
    public String Exibir() {
        return "Partida: " + mandante.getNome() +
                " x " + visitante.getNome() +
                " | Data: " + dataHora +
                " | Situação: " + (finalizada ?
                "Finalizada " + golsMandante + "x" + golsVisitante :
                "Aguardando");
        // ee a partida já foi finalizada mostra o placar, senao mostra "aguardando"
    }


}
