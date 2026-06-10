package controller;

import dao.*;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sistema {

    private static final int MAXIMO_GRUPOS = 5;
    private static Sistema instancia;

    private final List<Clube> clubes;
    private final List<Campeonato> campeonatos;
    private final List<Partida> partidas;
    private final List<Grupo> grupos;
    private final List<Participante> participantes;
    private final List<Aposta> apostas;
    private final Administrador administrador;

    private final ClubeDAO clubeDAO             = new ClubeDAO();
    private final CampeonatoDAO campeonatoDAO   = new CampeonatoDAO();
    private final GrupoDAO grupoDAO             = new GrupoDAO();
    private final ParticipanteDAO participanteDAO = new ParticipanteDAO();
    private final PartidaDAO partidaDAO         = new PartidaDAO();
    private final ApostaDAO apostaDAO           = new ApostaDAO();

    public List<Clube> getClubes()               { return clubes; }
    public List<Campeonato> getCampeonatos()      { return campeonatos; }
    public List<Partida> getPartidas()           { return partidas; }
    public List<Grupo> getGrupos()               { return grupos; }
    public List<Participante> getParticipantes() { return participantes; }
    public List<Aposta> getApostas()             { return apostas; }
    public Administrador getAdministrador()      { return administrador; }

    private Sistema() {
        InicializarDB.inicializar();

        clubes        = new ArrayList<>(clubeDAO.listarTodos());
        campeonatos   = new ArrayList<>(campeonatoDAO.listarTodos());
        partidas      = new ArrayList<>(partidaDAO.listarTodos());
        grupos        = new ArrayList<>(grupoDAO.listarTodos());
        participantes = new ArrayList<>(participanteDAO.listarTodos());
        apostas       = new ArrayList<>(apostaDAO.listarTodos());
        administrador = new Administrador("Administrador", "admin");
    }

    public static Sistema getInstancia() {
        if (instancia == null) {
            instancia = new Sistema();
        }
        return instancia;
    }

    public String cadastrarClube(String nome, String estado) {
        for (Clube c : clubes)
            if (c.getNome().equalsIgnoreCase(nome))
                return "Clube já cadastrado";
        Clube clube = new Clube(nome, estado);
        clubes.add(clube);
        clubeDAO.salvar(clube);
        return "ok";
    }

    public String cadastrarCampeonato(String nome, int ano) {
        for (Campeonato c : campeonatos)
            if (c.getNome().equalsIgnoreCase(nome))
                return "Campeonato já cadastrado";
        Campeonato campeonato = new Campeonato(nome, ano);
        campeonatos.add(campeonato);
        campeonatoDAO.salvar(campeonato);
        return "ok";
    }

    public String adicionarClubeAoCampeonato(Campeonato campeonato, Clube clube) {
        String res = campeonato.adicionarClube(clube);
        if ("adicao deu certo".equals(res)) {
            campeonatoDAO.adicionarClube(campeonato, clube);
            return "ok";
        }
        return res;
    }

    public String cadastrarPartida(Clube mandante, Clube visitante, LocalDateTime dataHora, Campeonato campeonato) {
        if (mandante.equals(visitante))
            return "Os clubes sao iguais!";
        Partida partida = new Partida(mandante, visitante, dataHora);
        partidas.add(partida);
        partidaDAO.salvar(partida, campeonato);
        return "ok";
    }

    public String cadastrarGrupo(String nome, Campeonato campeonato) {
        if (grupos.size() >= MAXIMO_GRUPOS)
            return "Limite de grupos atingindo (max = 5)";
        for (Grupo g : grupos)
            if (g.getNome().equalsIgnoreCase(nome))
                return "Esse grupo ja existe";
        Grupo grupo = new Grupo(nome, campeonato);
        grupos.add(grupo);
        grupoDAO.salvar(grupo);
        return "ok";
    }

    public String cadastrarParticipante(String nome, Grupo grupo) {
        Participante p = new Participante(nome, nome);
        boolean r = grupo.adicionarParticipante(p);
        if (r) {
            participantes.add(p);
            participanteDAO.salvar(p, grupo);
        }
        return r ? "ok" : "erro";
    }

    public String registrarAposta(Participante participante, Partida partida, int golsMandante, int golsVisitante) {
        if (golsMandante < 0 || golsVisitante < 0)
            throw new SistemaException("Gols não podem ser negativos", "Aposta");

        if (partida.isFinalizada())
            throw new SistemaException("Partida já finalizada", "Aposta");

        if (!partida.ApostaPermitida())
            throw new SistemaException("Apostas encerradas — menos de 20 min para a partida", "Aposta");

        for (Aposta a : apostas)
            if (a.getParticipante().equals(participante) && a.getPartida().equals(partida))
                throw new SistemaException(participante.getNome() + " já apostou nesta partida", "Aposta");
        Aposta aposta = new Aposta(participante, partida, golsMandante, golsVisitante);
        apostas.add(aposta);
        apostaDAO.salvar(aposta);
        return "ok";
    }

    public void registrarResultado(Partida partida, int golsMandante, int golsVisitante) {
        if (golsMandante < 0 || golsVisitante < 0) {
            System.out.println("Erro: gols não podem ser negativos");
            return;
        }

        partida.registrarResultado(golsMandante, golsVisitante);
        partidaDAO.atualizarResultado(partida);
        for (Aposta a : apostas) {
            if (a.getPartida().equals(partida)) {
                int pontos = a.calcularPontuacao();
                a.getParticipante().adicionarPontos(pontos);
                apostaDAO.atualizarPontuacao(a);
                participanteDAO.atualizarPontuacao(a.getParticipante());
            }
        }
    }

    public boolean jaApostou(Participante participante, Partida partida) {
        for (Aposta a : apostas)
            if (a.getParticipante().equals(participante) && a.getPartida().equals(partida))
                return true;
        return false;
    }

    public Clube buscaClube(String nome) {
        for (Clube c : clubes)
            if (c.getNome().equals(nome))
                return c;
        return null;
    }

    public Campeonato buscaCampeonato(String nome) {
        for (Campeonato c : campeonatos)
            if (c.getNome().equals(nome))
                return c;
        return null;
    }

    public Grupo buscaGrupo(String nome) {
        for (Grupo g : grupos)
            if (g.getNome().equals(nome))
                return g;
        return null;
    }

    public Participante buscaParticipante(String nome) {
        for (Participante p : participantes)
            if (p.getNome().equals(nome))
                return p;
        return null;
    }

    public Partida buscaPartida(String mandante, String visitante, String datahora) {
        for (Partida p : partidas)
            if (p.getMandante().getNome().equals(mandante)
                    && p.getVisitante().getNome().equals(visitante)
                    && p.getDataHora().toString().equals(datahora))
                return p;
        return null;
    }
}
