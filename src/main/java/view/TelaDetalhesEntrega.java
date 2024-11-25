package view;

import controller.PrincipalController;
import model.Entrega;
import model.Usuario;
import model.Usuario.TipoUsuario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaDetalhesEntrega extends JDialog {
  private PrincipalController controller;
  private Entrega entrega;
  private Usuario usuarioLogado;

  public TelaDetalhesEntrega(JFrame parent, PrincipalController controller, String entregaId) {
    super(parent, "Detalhes da Entrega", true);
    this.controller = controller;
    this.usuarioLogado = controller.getUsuarioLogado();
    this.entrega = controller.getPedidoController().getPedido(entregaId);

    if (entrega == null) {
      JOptionPane.showMessageDialog(this, "Entrega não encontrada!");
      dispose();
      return;
    }

    initComponents();
  }

  private void initComponents() {
    setSize(500, 400);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout(10, 10));

    // Painel de informações
    JPanel infoPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.WEST;

    // Dados da entrega
    addField(infoPanel, gbc, "ID:", entrega.getId());
    addField(infoPanel, gbc, "Origem:", entrega.getOrigem());
    addField(infoPanel, gbc, "Destino:", entrega.getDestino());
    addField(infoPanel, gbc, "Distância:", entrega.getDistancia() + " km");
    addField(infoPanel, gbc, "Preço:", String.format("R$ %.2f", entrega.getPreco()));
    addField(infoPanel, gbc, "Status:", entrega.isStatus() ? "Ativa" : "Finalizada");

    if (entrega.getIdmotorista() != null) {
      addField(infoPanel, gbc, "Motorista:", entrega.getIdmotorista());
    }

    // Painel de ações
    JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    // Botões específicos por tipo de usuário
    if (usuarioLogado.getTipo() == TipoUsuario.cliente) {
      if (entrega.isStatus() && entrega.getIdmotorista() == null) {
        JButton cancelarButton = new JButton("Cancelar Entrega");
        cancelarButton.addActionListener(e -> cancelarEntrega());
        actionPanel.add(cancelarButton);
      }
    } else if (usuarioLogado.getTipo() == TipoUsuario.motorista) {
      if (entrega.isStatus()) {
        if (entrega.getIdmotorista() == null) {
          JButton aceitarButton = new JButton("Aceitar Entrega");
          aceitarButton.addActionListener(e -> aceitarEntrega());
          actionPanel.add(aceitarButton);
        } else if (entrega.getIdmotorista().equals(usuarioLogado.getId())) {
          JButton atualizarButton = new JButton("Atualizar Posição");
          JButton finalizarButton = new JButton("Finalizar Entrega");

          atualizarButton.addActionListener(e -> atualizarPosicao());
          finalizarButton.addActionListener(e -> finalizarEntrega());

          actionPanel.add(atualizarButton);
          actionPanel.add(finalizarButton);
        }
      }
    } else if (usuarioLogado.getTipo() == TipoUsuario.administrador) {
      JButton atribuirButton = new JButton("Atribuir Motorista");
      JButton cancelarButton = new JButton("Cancelar Entrega");

      atribuirButton.addActionListener(e -> atribuirMotorista());
      cancelarButton.addActionListener(e -> cancelarEntrega());

      actionPanel.add(atribuirButton);
      actionPanel.add(cancelarButton);
    }

    JButton fecharButton = new JButton("Fechar");
    fecharButton.addActionListener(e -> dispose());
    actionPanel.add(fecharButton);

    add(infoPanel, BorderLayout.CENTER);
    add(actionPanel, BorderLayout.SOUTH);
  }

  private void addField(JPanel panel, GridBagConstraints gbc, String label, String value) {
    gbc.gridx = 0;
    gbc.gridy++;
    panel.add(new JLabel(label), gbc);

    gbc.gridx = 1;
    panel.add(new JLabel(value), gbc);
  }

  private void cancelarEntrega() {
    if (controller.getPedidoController().cancelarPedido(entrega.getId())) {
      JOptionPane.showMessageDialog(this, "Entrega cancelada com sucesso!");
      dispose();
    } else {
      JOptionPane.showMessageDialog(this, "Erro ao cancelar entrega!");
    }
  }

  private void aceitarEntrega() {
    if (controller.getMotoristaController().aceitarEntrega(entrega.getId(), usuarioLogado.getId())) {
      JOptionPane.showMessageDialog(this, "Entrega aceita com sucesso!");
      dispose();
    } else {
      JOptionPane.showMessageDialog(this, "Erro ao aceitar entrega!");
    }
  }

  private void atualizarPosicao() {
    String novaPosicao = JOptionPane.showInputDialog(this, "Digite a posição atual:");
    if (novaPosicao != null && !novaPosicao.trim().isEmpty()) {
      if (controller.atualizarPosicaoEntrega(entrega.getId(), novaPosicao)) {
        JOptionPane.showMessageDialog(this, "Posição atualizada com sucesso!");
      } else {
        JOptionPane.showMessageDialog(this, "Erro ao atualizar posição!");
      }
    }
  }

  private void finalizarEntrega() {
    if (controller.getMotoristaController().finalizarEntrega(entrega.getId())) {
      JOptionPane.showMessageDialog(this, "Entrega finalizada com sucesso!");
      dispose();
    } else {
      JOptionPane.showMessageDialog(this, "Erro ao finalizar entrega!");
    }
  }

  private void atribuirMotorista() {
    // Get available drivers
    ArrayList<Usuario> motoristas = controller.getMotoristas();

    if (motoristas.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Não há motoristas disponíveis!");
      return;
    }

    // Create dialog
    JDialog dialog = new JDialog(this, "Selecionar Motorista", true);
    dialog.setLayout(new BorderLayout(10, 10));
    dialog.setSize(400, 300);
    dialog.setLocationRelativeTo(this);

    // Create table model
    String[] colunas = { "ID", "Nome", "Telefone" };
    Object[][] dados = new Object[motoristas.size()][3];

    for (int i = 0; i < motoristas.size(); i++) {
      Usuario m = motoristas.get(i);
      dados[i] = new Object[] {
          m.getId(),
          m.getNome(),
          m.getTelefone()
      };
    }

    JTable table = new JTable(dados, colunas);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // Add components
    dialog.add(new JScrollPane(table), BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton selecionarButton = new JButton("Selecionar");
    JButton cancelarButton = new JButton("Cancelar");

    selecionarButton.addActionListener(e -> {
      int selectedRow = table.getSelectedRow();
      if (selectedRow >= 0) {
        String motoristaId = (String) table.getValueAt(selectedRow, 0);
        if (controller.atribuirMotoristaEntrega(entrega.getId(), motoristaId)) {
          JOptionPane.showMessageDialog(dialog, "Motorista atribuído com sucesso!");
          dialog.dispose();
          dispose(); // Fecha a tela de detalhes
        } else {
          JOptionPane.showMessageDialog(dialog, "Erro ao atribuir motorista!");
        }
      } else {
        JOptionPane.showMessageDialog(dialog, "Selecione um motorista!");
      }
    });

    cancelarButton.addActionListener(e -> dialog.dispose());

    buttonPanel.add(selecionarButton);
    buttonPanel.add(cancelarButton);

    dialog.add(buttonPanel, BorderLayout.SOUTH);
    dialog.setVisible(true);
  }
}