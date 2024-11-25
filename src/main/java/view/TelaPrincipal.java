package view;

import controller.PrincipalController;
import model.Entrega;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;

public class TelaPrincipal extends JFrame {
  private PrincipalController controller;
  private CardLayout cardLayout;
  private JPanel mainPanel;
  private JTable clienteEntregasTable;
  private JTable motoristaEntregasTable;
  private JTable adminEntregasTable;
  private JTable adminUsuariosTable;

  // Panels for different views
  private JPanel loginPanel;
  private JPanel clientePanel;
  private JPanel motoristaPanel;
  private JPanel adminPanel;

  public TelaPrincipal() {
    controller = new PrincipalController();
    initComponents();
  }

  private void initComponents() {
    setTitle("Sistema de Entregas - Coelho Rápido");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null);

    cardLayout = new CardLayout();
    mainPanel = new JPanel(cardLayout);

    // Initialize panels
    createLoginPanel();
    createClientePanel();
    createMotoristaPanel();
    createAdminPanel();

    // Add panels to card layout
    mainPanel.add(loginPanel, "LOGIN");
    mainPanel.add(clientePanel, "CLIENTE");
    mainPanel.add(motoristaPanel, "MOTORISTA");
    mainPanel.add(adminPanel, "ADMIN");

    add(mainPanel);

    // Show login initially
    cardLayout.show(mainPanel, "LOGIN");
  }

  private void createLoginPanel() {
    loginPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    JTextField emailField = new JTextField(20);
    JPasswordField senhaField = new JPasswordField(20);
    JButton loginButton = new JButton("Login");
    JButton cadastroButton = new JButton("Cadastrar-se");

    loginButton.addActionListener(e -> {
      String email = emailField.getText();
      String senha = new String(senhaField.getPassword());

      if (controller.fazerLogin(email, senha)) {
        cardLayout.show(mainPanel, controller.getTipoTelaInicial());
        atualizarTela();
      } else {
        JOptionPane.showMessageDialog(this, "Login inválido!");
      }
    });

    // Layout components
    gbc.gridx = 0;
    gbc.gridy = 0;
    loginPanel.add(new JLabel("Email:"), gbc);
    gbc.gridx = 1;
    loginPanel.add(emailField, gbc);
    gbc.gridx = 0;
    gbc.gridy = 1;
    loginPanel.add(new JLabel("Senha:"), gbc);
    gbc.gridx = 1;
    loginPanel.add(senhaField, gbc);
    gbc.gridx = 1;
    gbc.gridy = 2;
    loginPanel.add(loginButton, gbc);
    gbc.gridy = 3;
    loginPanel.add(cadastroButton, gbc);
  }

  private void createClientePanel() {
    clientePanel = new JPanel(new BorderLayout());

    // Header
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton logoutButton = new JButton("Logout");
    logoutButton.addActionListener(e -> logout());
    headerPanel.add(logoutButton);

    // Content
    JPanel contentPanel = new JPanel(new GridLayout(2, 1));

    // Nova entrega
    JPanel novaEntregaPanel = new JPanel(new GridLayout(0, 2));
    JTextField origemField = new JTextField();
    JTextField destinoField = new JTextField();
    JTextField distanciaField = new JTextField();
    JButton solicitarButton = new JButton("Solicitar Entrega");

    novaEntregaPanel.add(new JLabel("Origem:"));
    novaEntregaPanel.add(origemField);
    novaEntregaPanel.add(new JLabel("Destino:"));
    novaEntregaPanel.add(destinoField);
    novaEntregaPanel.add(new JLabel("Distância (km):"));
    novaEntregaPanel.add(distanciaField);
    novaEntregaPanel.add(solicitarButton);

    // Lista de entregas
    JPanel listPanel = new JPanel(new BorderLayout());
    JTable entregasTable = new JTable();
    listPanel.add(new JScrollPane(entregasTable), BorderLayout.CENTER);

    contentPanel.add(novaEntregaPanel);
    contentPanel.add(listPanel);

    clientePanel.add(headerPanel, BorderLayout.NORTH);
    clientePanel.add(contentPanel, BorderLayout.CENTER);
  }

  private void createMotoristaPanel() {
    motoristaPanel = new JPanel(new BorderLayout());

    // Header with logout
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton logoutButton = new JButton("Logout");
    logoutButton.addActionListener(e -> logout());
    headerPanel.add(logoutButton);

    // Content - Lista de entregas e atualização de posição
    JPanel contentPanel = new JPanel(new BorderLayout());
    JTable entregasTable = new JTable();
    contentPanel.add(new JScrollPane(entregasTable), BorderLayout.CENTER);

    motoristaPanel.add(headerPanel, BorderLayout.NORTH);
    motoristaPanel.add(contentPanel, BorderLayout.CENTER);
  }

  private void createAdminPanel() {
    adminPanel = new JPanel(new BorderLayout());

    // Header
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton logoutButton = new JButton("Logout");
    logoutButton.addActionListener(e -> logout());
    headerPanel.add(logoutButton);

    // Content - Tabs para usuários e entregas
    JTabbedPane tabbedPane = new JTabbedPane();

    // Tab Entregas
    JPanel entregasPanel = new JPanel(new BorderLayout());
    JTable entregasTable = new JTable();
    entregasPanel.add(new JScrollPane(entregasTable), BorderLayout.CENTER);

    // Tab Usuários
    JPanel usuariosPanel = new JPanel(new BorderLayout());
    JTable usuariosTable = new JTable();
    usuariosPanel.add(new JScrollPane(usuariosTable), BorderLayout.CENTER);

    tabbedPane.addTab("Entregas", entregasPanel);
    tabbedPane.addTab("Usuários", usuariosPanel);

    adminPanel.add(headerPanel, BorderLayout.NORTH);
    adminPanel.add(tabbedPane, BorderLayout.CENTER);
  }

  private void logout() {
    controller.logout();
    cardLayout.show(mainPanel, "LOGIN");
  }

  private void atualizarTela() {
    // Atualizar dados específicos de cada tela
    Usuario usuario = controller.getUsuarioLogado();
    if (usuario != null) {
      switch (usuario.getTipo()) {
        case cliente:
          atualizarTelaCliente();
          break;
        case motorista:
          atualizarTelaMotorista();
          break;
        case administrador:
          atualizarTelaAdmin();
          break;
      }
    }
  }

  private void atualizarTelaCliente() {
    ArrayList<Entrega> entregas = controller.listarEntregasCliente();

    // Define table model
    String[] colunas = { "ID", "Origem", "Destino", "Distância", "Preço", "Status" };
    Object[][] dados = new Object[entregas.size()][6];

    // Fill data
    for (int i = 0; i < entregas.size(); i++) {
      Entrega e = entregas.get(i);
      dados[i] = new Object[] {
          e.getId(),
          e.getOrigem(),
          e.getDestino(),
          e.getDistancia() + " km",
          String.format("R$ %.2f", e.getPreco()),
          e.isStatus() ? "Ativa" : "Finalizada"
      };
    }

    clienteEntregasTable.setModel(new DefaultTableModel(dados, colunas));
  }

  private void atualizarTelaMotorista() {
    ArrayList<Entrega> entregas = controller.listarEntregasMotorista();

    String[] colunas = { "ID", "Cliente", "Origem", "Destino", "Distância", "Status" };
    Object[][] dados = new Object[entregas.size()][6];

    for (int i = 0; i < entregas.size(); i++) {
      Entrega e = entregas.get(i);
      dados[i] = new Object[] {
          e.getId(),
          e.getIdcliente(),
          e.getOrigem(),
          e.getDestino(),
          e.getDistancia() + " km",
          e.isStatus() ? "Em andamento" : "Finalizada"
      };
    }

    motoristaEntregasTable.setModel(new DefaultTableModel(dados, colunas));
  }

  private void atualizarTelaAdmin() {
    // Atualiza tabela de entregas
    ArrayList<Entrega> entregas = controller.listarTodasEntregas();

    String[] colunasEntregas = { "ID", "Cliente", "Motorista", "Origem", "Destino", "Preço", "Status" };
    Object[][] dadosEntregas = new Object[entregas.size()][7];

    for (int i = 0; i < entregas.size(); i++) {
      Entrega e = entregas.get(i);
      dadosEntregas[i] = new Object[] {
          e.getId(),
          e.getIdcliente(),
          e.getIdmotorista() != null ? e.getIdmotorista() : "Não atribuído",
          e.getOrigem(),
          e.getDestino(),
          String.format("R$ %.2f", e.getPreco()),
          e.isStatus() ? "Ativa" : "Finalizada"
      };
    }

    adminEntregasTable.setModel(new DefaultTableModel(dadosEntregas, colunasEntregas));

    // Atualiza tabela de usuários
    ArrayList<Usuario> usuarios = controller.getUsuarios();
    String[] colunasUsuarios = { "ID", "Nome", "Email", "Tipo", "Status" };
    Object[][] dadosUsuarios = new Object[usuarios.size()][5];

    for (int i = 0; i < usuarios.size(); i++) {
      Usuario u = usuarios.get(i);
      dadosUsuarios[i] = new Object[] {
          u.getId(),
          u.getNome(),
          u.getEmail(),
          u.getTipo().toString(),
          u.isStatus() ? "Ativo" : "Inativo"
      };
    }

    adminUsuariosTable.setModel(new DefaultTableModel(dadosUsuarios, colunasUsuarios));
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new TelaPrincipal().setVisible(true);
    });
  }
}