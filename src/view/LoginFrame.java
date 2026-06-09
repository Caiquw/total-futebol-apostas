package view;

import dao.UsuarioDAO;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_SENHA = "admin123";

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private JTextField tfLogin;
    private JPasswordField tfSenha;

    public LoginFrame() {
        super("Login - Sistema de Apostas");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        construirUI();
        setVisible(true);
    }

    private void construirUI() {
        JPanel painel = new JPanel(new GridLayout(4, 2, 8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        tfLogin = new JTextField();
        tfSenha = new JPasswordField();
        JButton btnEntrar    = new JButton("Entrar");
        JButton btnCadastrar = new JButton("Cadastrar");

        painel.add(new JLabel("Login:")); painel.add(tfLogin);
        painel.add(new JLabel("Senha:")); painel.add(tfSenha);
        painel.add(btnCadastrar);         painel.add(btnEntrar);

        JLabel lblDica = new JLabel("Admin: admin/admin123", SwingConstants.CENTER);
        lblDica.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 10));
        lblDica.setForeground(Color.GRAY);

        add(painel, BorderLayout.CENTER);
        add(lblDica, BorderLayout.SOUTH);

        btnEntrar.addActionListener(e -> tentarLogin());
        btnCadastrar.addActionListener(e -> cadastrarUsuario());
        tfSenha.addActionListener(e -> tentarLogin());
    }

    private void tentarLogin() {
        String login = tfLogin.getText().trim();
        String senha = new String(tfSenha.getPassword()).trim();

        if (ADMIN_LOGIN.equals(login) && ADMIN_SENHA.equals(senha)) {
            dispose();
            new MainFrame(true);
        } else if (usuarioDAO.autenticar(login, senha)) {
            dispose();
            new MainFrame(false);
        } else {
            JOptionPane.showMessageDialog(this, "Login ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            tfSenha.setText("");
        }
    }

    private void cadastrarUsuario() {
        String login = JOptionPane.showInputDialog(this, "Escolha um login:");
        if (login == null || login.isBlank()) return;
        String senha = JOptionPane.showInputDialog(this, "Escolha uma senha:");
        if (senha == null || senha.isBlank()) return;
        boolean ok = usuarioDAO.salvar(login.trim(), senha.trim());
        if (ok) JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
        else    JOptionPane.showMessageDialog(this, "Login '" + login.trim() + "' já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
    }
}