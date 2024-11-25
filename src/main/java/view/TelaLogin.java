package view;

import controller.PrincipalController;
import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
  private PrincipalController controller;
  private JTextField emailField;
  private JPasswordField senhaField;

  public TelaLogin() {
    controller = new PrincipalController();
    initComponents();
  }

  private void initComponents() {
    setTitle("Login - Coelho Rápido");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 300);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);

    emailField = new JTextField(20);
    senhaField = new JPasswordField(20);
    JButton loginButton = new JButton("Entrar");
    JButton cadastroButton = new JButton("Cadastrar-se");

    // Layout
    gbc.gridx = 0;
    gbc.gridy = 0;
    mainPanel.add(new JLabel("Email:"), gbc);

    gbc.gridx = 1;
    mainPanel.add(emailField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    mainPanel.add(new JLabel("Senha:"), gbc);

    gbc.gridx = 1;
    mainPanel.add(senhaField, gbc);

    gbc.gridx = 1;
    gbc.gridy = 2;
    mainPanel.add(loginButton, gbc);

    gbc.gridy = 3;
    mainPanel.add(cadastroButton, gbc);

    // Actions
    loginButton.addActionListener(e -> fazerLogin());
    cadastroButton.addActionListener(e -> abrirTelaCadastro());

    add(mainPanel);
  }

  private void fazerLogin() {
    String email = emailField.getText();
    String senha = new String(senhaField.getPassword());

    if (controller.fazerLogin(email, senha)) {
      new TelaPrincipal().setVisible(true);
      dispose();
    } else {
      JOptionPane.showMessageDialog(this, "Email ou senha inválidos!");
    }
  }

  private void abrirTelaCadastro() {
    new TelaCadastro().setVisible(true);
    dispose();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new TelaLogin().setVisible(true);
    });
  }
}