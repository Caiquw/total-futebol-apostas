package dao;

import java.sql.*;

public class UsuarioDAO {

    public boolean salvar(String login, String senha) {
        if (buscarPorLogin(login) != null) return false;
        String sql = "INSERT INTO usuarios (login, senha) VALUES (?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senha);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
            return false;
        }
    }

    public String buscarPorLogin(String login) {
        String sql = "SELECT senha FROM usuarios WHERE login = ?";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("senha");
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    public boolean autenticar(String login, String senha) {
        String senhaBanco = buscarPorLogin(login);
        return senhaBanco != null && senhaBanco.equals(senha);
    }
}
