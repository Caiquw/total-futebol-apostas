package controller;

import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sistema
{

    private static final int MAXIMO_GRUPOS = 5;

    private static Sistema instancia;

    private final List<Clube> clubes;
    private final List<Campeonato> campeonatos;
    private final List<Partida> partidas;
    private final List<Grupo> grupos;
    private final List<Participante> participantes;
    private final List<Aposta> apostas;
    private final Administrador administrador;

    public List<Clube> getClubes() {
        return clubes;
    }

    public List<Campeonato> getCampeonatos() {
        return campeonatos;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public List<Aposta> getApostas() {
        return apostas;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    private Sistema(){

        clubes = new ArrayList<>();
        campeonatos   = new ArrayList<>();
        partidas      = new ArrayList<>();
        grupos        = new ArrayList<>();
        participantes = new ArrayList<>();
        apostas       = new ArrayList<>();
        administrador = new Administrador("Administrador", "admin");

    }

    public static Sistema getInstancia(){

        if (instancia == null){

            instancia = new Sistema();

        }
        return instancia;
    }

    public String cadastrarClube(String nome, String estado){
        for(Clube c : clubes)
            if (c.getNome().equalsIgnoreCase(nome)){
                return "Clube já cadastrado";
            }
        clubes.add(new Clube(nome,estado));
        return "Clube cadastrado";
    }


    public String cadastrarCampeonato(String nome, int ano){
        for (Campeonato c : campeonatos)
            if (c.getNome().equalsIgnoreCase(nome)){
                return "Campeonato já cadastrado";
            }
        campeonatos.add(new Campeonato(nome,ano));
        return "Campeonato cadastrado";

    }

    public String adicionarClubeAoCampeonato(Campeonato campeonato, Clube clube){
        return campeonato.adicionarClube(clube);
    }

    public String cadastrarPartida(Clube mandante, Clube visitante, LocalDateTime dataHora, Campeonato campeonato){
        if (mandante.equals(visitante)){

            return "Os clubes sao iguais!";
        }
        partidas.add(new Partida(mandante,visitante,dataHora,campeonato));
        return "Partida adicionada";
    }

    public String cadastrarGrupo(String nome, Campeonato campeonato){
        if(grupos.size() > MAXIMO_GRUPOS){
            return "Limite de grupos atingindo (max = 5)";

        }

        for(Grupo g : grupos)
            if (g.getNome().equalsIgnoreCase(nome)){
                return "Esse grupo ja existe";
            }
        grupos.add(new Grupo(nome,campeonato));
        return "Grupo adicionado";
    }

    public String cadastrarParticipante(String nome, Grupo grupo){
        Participante p = new Participante(nome,nome);
        Boolean r = grupo.adicionarParticipante(p);

        if (r.equals(Boolean.TRUE)){
            participantes.add(p);
        }
        return r ? "ok" : "erro";
    }

    public String registrarAposta(Participante participante, Partida partida, int golsMandante, int golsVisitante){
        if (!partida.ApostaPermitida()){
            return "Apostas Encerradas (partida já realizada ou ira acontecer em 20 minutos ou menos";
        }
        for(Aposta a : apostas)
            if (a.getParticipante().equals(participante) && a.getPartida().equals(partida)){
                return participante.getNome() + "ja apostou nesta partida";
            }
        apostas.add(new Aposta(participante,partida,golsMandante,golsVisitante));
        return "ok";
    }

    public void registrarResultado(Partida partida, int golsMandante, int golsVisitante){
        partida.registrarResultado(golsMandante,golsVisitante);
        for (Aposta a : apostas){
            if (a.getPartida().equals(partida)){
                int pontos = a.calcularPontuacao();
                a.getParticipante().adicionarPontos(pontos);
            }
        }
    }

    public boolean jaApostou(Participante participante, Partida partida){
        for (Aposta a : apostas)
            if (a.getParticipante().equals(participante) && a.getPartida().equals(partida)){
                return true;
            }
            return false;
        }

    public Clube buscaClube(String nome){
        for (Clube c : clubes)
            if (c.getNome().equals(nome)){
                return c;
            }
        return null;
    }

    public Campeonato buscaCampeonato(String nome){
        for (Campeonato c : campeonatos)
            if (c.getNome().equals(nome)){
                return c;
            }
        return null;
    }

    public Grupo buscaGrupo(String nome){
        for (Grupo g : grupos)
            if (g.getNome().equals(nome)){
                return g;
            }
        return null;
    }

    public Participante buscaParticipante(String nome){
        for (Participante p : participantes)
            if (p.getNome().equals(nome)){
                return p;
            }
        return null;
    }

    public Partida buscaPartida(String mandante, String visitante, String datahora){
        for (Partida p : partidas)
            if (p.getMandante().getNome().equals(mandante) && p.getVisitante().getNome().equals(visitante) && p.getDataHora().toString().equals(datahora)){

                return p;
        }
        return null;
    }



}


