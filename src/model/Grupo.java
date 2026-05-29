package model;

public class Grupo implements Int_Exibir { //classe - molde + interface
    // private -> encapsulamento (somente a classe pode acessar)
    private String nome;
    private Campeonato campeonato;
    private Participante[] participantes; //array pois cada grupo pode ter até 5 part.
    private int totalParticipantes;


    //CONSTRUTORES
    public Grupo() { //construtor padrao sem parametros - sem infos ainda
        this.nome = "";
        this.campeonato = null;
        this.participantes = new Participante[5]; //tamanho 5 limite
        this.totalParticipantes = 0;
    }

    public Grupo(String nome, Campeonato campeonato) { //construtor sobrecarregado - info definidas
        this.nome = nome;
        this.campeonato = campeonato;
        this.participantes = new Participante[5];
        this.totalParticipantes = 0;
        // this significa q o atributo desta classe recebe o valor que veio como parâmetro
    }

    // GETTERS e SETTERS são métodos que permitem acessar/modificar atributos privados de fora da classe
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Campeonato getCampeonato() {
        return this.campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public int getTotalParticipantes() {
        return this.totalParticipantes;
    }

    public Participante[] getParticipantes() {
        return this.participantes;
    }
// totalParticipantes e participantes não têm setter - não pode ser alterado por fora
// isso é encapsular
//--

    // regras de negócio
    // ADD PARTICIPANTE
    public boolean adicionarParticipante(Participante participante) {
        if (totalParticipantes >= 5) { //verifica o limite de 5 antes de adicionar
            System.out.println("Grupo cheio! Máximo de 5 participantes.");
            return false;
        }
        participantes[totalParticipantes] = participante;
        totalParticipantes++;
        return true;
    }

    // VERIFICAR SE GRUPO ESTÁ CHEIO
    // usado para verifica sem adicionar
    public boolean estaCheia() {
        return totalParticipantes >= 5;
    }

    // CALCULAR E EXIBIR CLASSIFICACAO
    public void exibirClassificacao() {
        System.out.println("=== Classificação do Grupo: " + nome + " ===");

        // ordena por pontuação (maior para menor)
        for (int i = 0; i < totalParticipantes - 1; i++) {
            for (int j = i + 1; j < totalParticipantes; j++) {
                if (participantes[j].getPontuacaoTotal() >
                        participantes[i].getPontuacaoTotal()) {
                    Participante temp = participantes[i];
                    participantes[i] = participantes[j];
                    participantes[j] = temp;
                }
            }
        }

        // exibe o resultado
        for (int i = 0; i < totalParticipantes; i++) {
            System.out.println((i + 1) + "º - " +
                    participantes[i].getNome() +
                    " | Pontos: " +
                    participantes[i].getPontuacaoTotal());
        }
    }
    // INTERFACE
// cumprindo o contrato da
    @Override
    public String Exibir() {
        return "Grupo: " + nome +
                " | Campeonato: " + campeonato.getNome() +
                " | Participantes: " + totalParticipantes + "/5";
    }
// exibirInfo -> resumo rapido do grupo
// exibirClassificacao -> ranking detalhado dos participantes + pontuacao
}