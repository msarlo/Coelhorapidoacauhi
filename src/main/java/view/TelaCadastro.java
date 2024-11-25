package view;

import controller.PrincipalController;
import model.Usuario.TipoUsuario;
import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JFrame {
  private PrincipalController controller;
  private JTextField nomeField;
  private JTextField emailField;
  private JPasswordField senhaField;
  private JTextField telefoneField;

  public TelaCadastro() {
    controller = new PrincipalController();
    initComponents();
  }

  private void initComponents() {
    setTitle("Cadastro - Coelho Rápido");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 400);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);

    nomeField = new JTextField(20);
    emailField = new JTextField(20);
    senhaField = new JPasswordField(20);
    telefoneField = new JTextField(20);
    JButton cadastrarButton = new JButton("Cadastrar");
    JButton voltarButton = new JButton("Voltar");

    // Layout
    gbc.gridx = 0;
    gbc.gridy = 0;
    mainPanel.add(new JLabel("Nome:"), gbc);

    gbc.gridx = 1;
    mainPanel.add(nomeField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    mainPanel.add(new JLabel("Email:"), gbc);

    gbc.gridx = 1;
    mainPanel.add(emailField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    mainPanel.add(new JLabel("Senha:"), gbc);

    gbc.gridx = 1;
    mainPanel.add(senhaField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    mainPanel.add(new JLabel("Telefone:"), gbc);

    gbc.gridx = 1;
    mainPanel.add(telefoneField, gbc);

    gbc.gridx = 1;
    gbc.gridy = 4;
    mainPanel.add(cadastrarButton, gbc);

    gbc.gridy = 5;
    mainPanel.add(voltarButton, gbc);

    // Actions
    cadastrarButton.addActionListener(e -> fazerCadastro());
    voltarButton.addActionListener(e -> voltarLogin());

    add(mainPanel);
  }

  private void fazerCadastro() {
    String nome = nomeField.getText();
    String email = emailField.getText();
    String senha = new String(senhaField.getPassword());
    String telefone = telefoneField.getText();

    if (controller.cadastrarUsuario(nome, email, senha, telefone, TipoUsuario.cliente)) {
      JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
      voltarLogin();
    } else {
      JOptionPane.showMessageDialog(this, "Erro ao realizar cadastro!");
    }
  }

  private void voltarLogin() {
    new TelaLogin().setVisible(true);
    dispose();
  }
}