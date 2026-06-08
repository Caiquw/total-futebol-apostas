// ClubeDAO.java — vai ter os métodos para salvar e buscar clubes no banco.
package dao;

import model.Clube;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubeDAO {

    // SALVAR um clube no banco
    public void salvar(Clube clube) {
        String sql = "INSERT INTO clubes (nome, estado) VALUES (?, ?)";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, clube.getNome());
            stmt.setString(2, clube.getEstado());
            stmt.executeUpdate();
            // executeUpdate() — executa SQLs que modificam o banco: INSERT, UPDATE, DELETE.

        } catch (SQLException e) {
            System.out.println("Erro ao salvar clube: " + e.getMessage());
        }
    }

    // BUSCAR todos os clubes do banco
    public List<Clube> listarTodos() {
        List<Clube> clubes = new ArrayList<>();
        String sql = "SELECT * FROM clubes";

        try (Connection conn = ConexaoDB.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // executeQuery() — executa SQLs que buscam dados: SELECT.
            // ResultSet — é a tabela de resultados que volta do banco.

            while (rs.next()) {
                Clube clube = new Clube(
                        rs.getString("nome"),
                        rs.getString("estado")
                );
                // rs.next() avança linha por linha, como uma lista.
                clubes.add(clube);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar clubes: " + e.getMessage());
        }

        return clubes;
    }
}