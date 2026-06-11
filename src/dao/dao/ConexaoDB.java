package dao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    private static final String URL = "jdbc:sqlite:apostas.db";

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
