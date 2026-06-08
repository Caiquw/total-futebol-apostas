package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InicializarDB {

    public static void inicializar() {
        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement()) {

            // tabela de clubes
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS clubes (
                    id      INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome    TEXT NOT NULL,
                    estado  TEXT NOT NULL
                )
            """);

            // tabela de campeonatos
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS campeonatos (
                    id   INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    ano  INTEGER NOT NULL
                )
            """);

            // tabela de grupos
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS grupos (
                    id             INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome           TEXT NOT NULL,
                    campeonato_id  INTEGER,
                    FOREIGN KEY (campeonato_id) REFERENCES campeonatos(id)
                )
            """);

            // tabela de participantes
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

            // tabela de partidas
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS partidas (
                    id              INTEGER PRIMARY KEY AUTOINCREMENT,
                    mandante_id     INTEGER,
                    visitante_id    INTEGER,
                    data_hora       TEXT NOT NULL,
                    gols_mandante   INTEGER DEFAULT 0,
                    gols_visitante  INTEGER DEFAULT 0,
                    finalizada      INTEGER DEFAULT 0,
                    FOREIGN KEY (mandante_id)  REFERENCES clubes(id),
                    FOREIGN KEY (visitante_id) REFERENCES clubes(id)
                )
            """);

            // tabela de apostas
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS apostas (
                    id                    INTEGER PRIMARY KEY AUTOINCREMENT,
                    participante_id       INTEGER,
                    partida_id            INTEGER,
                    gols_mandante_palpite INTEGER DEFAULT 0,
                    gols_visitante_palpite INTEGER DEFAULT 0,
                    pontuacao             INTEGER DEFAULT 0,
                    FOREIGN KEY (participante_id) REFERENCES participantes(id),
                    FOREIGN KEY (partida_id)      REFERENCES partidas(id)
                )
            """);

            System.out.println("Banco inicializado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inicializar banco: " + e.getMessage());
        }
    }
}
// ENTENDENDO OS CONCEITOS
// CREATE TABLE IF NOT EXISTS — cria a tabela só se ela ainda não existir. Isso é essencial porque esse metodo vai ser chamado toda vez que o sistema iniciar
// é o relacionamento entre tabelas. Por exemplo, um grupo pertence a um campeonato — então campeonato_id na tabela grupos aponta para o id na tabela campeonatos.
// try-with-resources — o try (Connection conn = ...) fecha a conexão automaticamente ao terminar, mesmo se der erro. Boa prática essencial com banco de dados.