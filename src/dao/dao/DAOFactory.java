package dao.dao;

import dao.binario.*;
import dao.hibernate.*;
import dao.interfaces.*;
import dao.jdbc.*;

// Fábrica central de DAOs — aqui mora a única variável que precisa mudar para trocar a persistência
// Opções: "JDBC" | "HIBERNATE" | "BIN"
public class DAOFactory {

    // ★ ALTERE APENAS ESTA LINHA PARA TROCAR O MODO DE PERSISTÊNCIA ★
    private static final String PERSISTENCIA = "JDBC";

    // cada método retorna a interface — o Sistema nunca sabe qual implementação está por baixo
    public static IClubeDAO getClubeDAO() {
        if ("JDBC".equals(PERSISTENCIA))      return new ClubeDAOJDBC();
        if ("HIBERNATE".equals(PERSISTENCIA)) return new ClubeDAOHibernate();
        if ("BIN".equals(PERSISTENCIA))       return new ClubeDAOBinario();
        throw new IllegalArgumentException("Persistência inválida: " + PERSISTENCIA);
    }

    public static ICampeonatoDAO getCampeonatoDAO() {
        if ("JDBC".equals(PERSISTENCIA))      return new CampeonatoDAOJDBC();
        if ("HIBERNATE".equals(PERSISTENCIA)) return new CampeonatoDAOHibernate();
        if ("BIN".equals(PERSISTENCIA))       return new CampeonatoDAOBinario();
        throw new IllegalArgumentException("Persistência inválida: " + PERSISTENCIA);
    }

    public static IGrupoDAO getGrupoDAO() {
        if ("JDBC".equals(PERSISTENCIA))      return new GrupoDAOJDBC();
        if ("HIBERNATE".equals(PERSISTENCIA)) return new GrupoDAOHibernate();
        if ("BIN".equals(PERSISTENCIA))       return new GrupoDAOBinario();
        throw new IllegalArgumentException("Persistência inválida: " + PERSISTENCIA);
    }

    public static IParticipanteDAO getParticipanteDAO() {
        if ("JDBC".equals(PERSISTENCIA))      return new ParticipanteDAOJDBC();
        if ("HIBERNATE".equals(PERSISTENCIA)) return new ParticipanteDAOHibernate();
        if ("BIN".equals(PERSISTENCIA))       return new ParticipanteDAOBinario();
        throw new IllegalArgumentException("Persistência inválida: " + PERSISTENCIA);
    }

    public static IPartidaDAO getPartidaDAO() {
        if ("JDBC".equals(PERSISTENCIA))      return new PartidaDAOJDBC();
        if ("HIBERNATE".equals(PERSISTENCIA)) return new PartidaDAOHibernate();
        if ("BIN".equals(PERSISTENCIA))       return new PartidaDAOBinario();
        throw new IllegalArgumentException("Persistência inválida: " + PERSISTENCIA);
    }

    public static IApostaDAO getApostaDAO() {
        if ("JDBC".equals(PERSISTENCIA))      return new ApostaDAOJDBC();
        if ("HIBERNATE".equals(PERSISTENCIA)) return new ApostaDAOHibernate();
        if ("BIN".equals(PERSISTENCIA))       return new ApostaDAOBinario();
        throw new IllegalArgumentException("Persistência inválida: " + PERSISTENCIA);
    }
}
