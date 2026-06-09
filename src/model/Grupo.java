package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Grupo implements Int_Exibir {
    private String nome;
    private Campeonato campeonato;
    private List<Participante> participantes;
    private int totalParticipantes;

    public Grupo() {
        this.nome = "";
        this.campeonato = null;
        this.participantes = new ArrayList<>(5);
        this.totalParticipantes = 0;
    }

    public Grupo(String nome, Campeonato campeonato) {
        this.nome = nome;
        this.campeonato = campeonato;
        this.participantes = new ArrayList<>(5);
        this.totalParticipantes = 0;
    }

    public String getNome()                              { return this.nome; }
    public void setNome(String nome)                     { this.nome = nome; }
    public Campeonato getCampeonato()                    { return this.campeonato; }
    public void setCampeonato(Campeonato campeonato)     { this.campeonato = campeonato; }
    public int getTotalParticipantes()                   { return this.totalParticipantes; }
    public List<Participante> getParticipantes()         { return this.participantes; }
    public void setParticipantes(List<Participante> p)   { this.participantes = p; }
    public void setTotalParticipantes(int total)         { this.totalParticipantes = total; }

    public List<Participante> getClassificacao() {
        List<Participante> ranking = new ArrayList<>(participantes);
        ranking.sort(Comparator.comparingInt(Participante::getPontuacaoTotal).reversed());
        return ranking;
    }

    public boolean adicionarParticipante(Participante participante) {
        if (totalParticipantes >= 5) {
            System.out.println("Grupo cheio! Máximo de 5 participantes.");
            return false;
        }
        participantes.add(participante);
        totalParticipantes++;
        return true;
    }

    public boolean estaCheia() {
        return totalParticipantes >= 5;
    }

    public void exibirClassificacao() {
        System.out.println("=== Classificação do Grupo: " + nome + " ===");
        List<Participante> ranking = getClassificacao();
        for (int i = 0; i < ranking.size(); i++) {
            System.out.println((i + 1) + "º - " + ranking.get(i).getNome() + " | Pontos: " + ranking.get(i).getPontuacaoTotal());
        }
    }

    @Override
    public String Exibir() {
        return "Grupo: " + nome +
                " | Campeonato: " + campeonato.getNome() +
                " | Participantes: " + totalParticipantes + "/5";
    }

    @Override
    public String toString() {
        return nome;
    }
}