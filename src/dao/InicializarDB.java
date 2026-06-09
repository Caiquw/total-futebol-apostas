package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InicializarDB {

    public static void inicializar() {
        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS clubes (
                    id      INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome    TEXT NOT NULL,
                    estado  TEXT NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS campeonatos (
                    id   INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    ano  INTEGER NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS campeonato_clubes (
                    campeonato_id INTEGER,
                    clube_id      INTEGER,
                    PRIMARY KEY (campeonato_id, clube_id),
                    FOREIGN KEY (campeonato_id) REFERENCES campeonatos(id),
                    FOREIGN KEY (clube_id)      REFERENCES clubes(id)
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS grupos (
                    id             INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome           TEXT NOT NULL,
                    campeonato_id  INTEGER,
                    FOREIGN KEY (campeonato_id) REFERENCES campeonatos(id)
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS participantes (
                    id              INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome            TEXT NOT NULL,
                    login           TEXT NOT NULL,
                    pontuacao_total INTEGER DEFAULT 0,
                    grupo_id        INTEGER,
                    FOREIGN KEY (grupo_id) REFERENCES grupos(id)
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS partidas (
                    id             INTEGER PRIMARY KEY AUTOINCREMENT,
                    mandante_id    INTEGER,
                    visitante_id   INTEGER,
                    campeonato_id  INTEGER,
                    data_hora      TEXT NOT NULL,
                    gols_mandante  INTEGER DEFAULT 0,
                    gols_visitante INTEGER DEFAULT 0,
                    finalizada     INTEGER DEFAULT 0,
                    FOREIGN KEY (mandante_id)   REFERENCES clubes(id),
                    FOREIGN KEY (visitante_id)  REFERENCES clubes(id),
                    FOREIGN KEY (campeonato_id) REFERENCES campeonatos(id)
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS apostas (
                    id                     INTEGER PRIMARY KEY AUTOINCREMENT,
                    participante_id        INTEGER,
                    partida_id             INTEGER,
                    gols_mandante_palpite  INTEGER DEFAULT 0,
                    gols_visitante_palpite INTEGER DEFAULT 0,
                    pontuacao              INTEGER DEFAULT 0,
                    FOREIGN KEY (participante_id) REFERENCES participantes(id),
                    FOREIGN KEY (partida_id)      REFERENCES partidas(id)
                )
            """);

        } catch (SQLException e) {
            System.out.println("Erro ao inicializar banco: " + e.getMessage());
        }
    }
}
