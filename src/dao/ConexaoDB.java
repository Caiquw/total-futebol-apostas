// O DAO (Data Access Object) é a camada que conversa com o banco de dados.
// Com o DAO, cada informação é salva no banco. Fecha o programa, abre de novo — os clubes, partidas, apostas, tudo ainda está lá.
// o arquivo ConexaoDB.java -> abre a conexão com o banco
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    // caminho do arquivo do banco - vai criar na pasta do projeto
    private static final String URL = "jdbc:sqlite:apostas.db";


    // getConexao() abre e retorna uma conexão. Quem chamar esse metodo recebe a conexão pronta para usar
    // throws SQLException - se der erro de banco, avisa quem chamou o metodo
    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}