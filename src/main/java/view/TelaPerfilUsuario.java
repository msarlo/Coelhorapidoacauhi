package view;

import controller.PrincipalController;
import model.Usuario;
import javax.swing.*;
import java.awt.*;

public class TelaPerfilUsuario extends JFrame {
  private PrincipalController controller;
  private Usuario usuario;

  private JTextField nomeField;
  private JTextField emailField;
  private JPasswordField senhaField;
  private JTextField telefoneField;
  private JLabel tipoLabel;

  public TelaPerfilUsuario(PrincipalController controller) {
    this.controller = controller;
    this.usuario = controller.getUsuarioLogado();
    initComponents();
  }

  private void initComponents() {
    setTitle("Perfil do Usuário - Coelho Rápido");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(400, 400);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Initialize fields with user data
    nomeField = new JTextField(usuario.getNome(), 20);
    emailField = new JTextField(usuario.getEmail(), 20);
    senhaField = new JPasswordField(usuario.getSenha(), 20);
    telefoneField = new JTextField(usuario.getTelefone(), 20);
    tipoLabel = new JLabel(usuario.getTipo().toString());

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

    gbc.gridx = 0;
    gbc.gridy = 4;
    mainPanel.add(new JLabel("Tipo:"), gbc);
    gbc.gridx = 1;
    mainPanel.add(tipoLabel, gbc);

    // Buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton salvarButton = new JButton("Salvar");
    JButton cancelarButton = new JButton("Cancelar");

    salvarButton.addActionListener(e -> salvarAlteracoes());
    cancelarButton.addActionListener(e -> dispose());

    buttonPanel.add(salvarButton);
    buttonPanel.add(cancelarButton);

    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 2;
    mainPanel.add(buttonPanel, gbc);

    add(mainPanel);
  }

  private void salvarAlteracoes() {
    usuario.setNome(nomeField.getText());
    usuario.setEmail(emailField.getText());
    usuario.setSenha(new String(senhaField.getPassword()));
    usuario.setTelefone(telefoneField.getText());

    // Add method to PrincipalController to save user changes
    if (controller.atualizarUsuario(usuario)) {
      JOptionPane.showMessageDialog(this, "Perfil atualizado com sucesso!");
      dispose();
    } else {
      JOptionPane.showMessageDialog(this, "Erro ao atualizar perfil!");
    }
  }
}