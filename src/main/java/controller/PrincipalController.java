package controller;

import model.Usuario;
import model.Usuario.TipoUsuario;
import model.Entrega;
import java.util.ArrayList;

public class PrincipalController {
    private LoginController loginController;
    private CadastroController cadastroController;
    private PedidosController pedidoController;
    private MotoristaController motoristaController;
    private AdminController adminController;
    private Usuario usuarioLogado;

    public PrincipalController() {
        this.loginController = new LoginController();
        this.cadastroController = new CadastroController();
        this.pedidoController = new PedidosController();
        this.motoristaController = new MotoristaController();
        this.adminController = new AdminController();
    }

    // Login e navegação
    public boolean fazerLogin(String email, String senha) {
        usuarioLogado = loginController.autenticar(email, senha);
        return usuarioLogado != null;
    }

    public boolean cadastrarUsuario(String nome, String email, String senha, String telefone, TipoUsuario tipo) {
        // Apenas admin pode cadastrar outros admins/motoristas
        if (tipo != TipoUsuario.cliente) {
            if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.administrador) {
                return false;
            }
        }

        return cadastroController.cadastrarUsuario(nome, email, senha, telefone, tipo);
    }

    public String getTipoTelaInicial() {
        if (usuarioLogado == null)
            return "LOGIN";

        switch (usuarioLogado.getTipo()) {
            case cliente:
                return "CLIENTE";
            case motorista:
                return "MOTORISTA";
            case administrador:
                return "ADMIN";
            default:
                return "LOGIN";
        }
    }

    public ArrayList<Usuario> getUsuarios() {
        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.administrador) {
            return new ArrayList<>();
        }
        return adminController.getTodosUsuarios();
    }

    // Funcionalidades Cliente
    public String solicitarEntrega(String origem, String destino, double distancia) {
        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.cliente) {
            return null;
        }
        return pedidoController.cadastrarPedido(usuarioLogado, origem, destino, distancia);
    }

    public ArrayList<Entrega> listarEntregasCliente() {
        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.cliente) {
            return new ArrayList<>();
        }
        return pedidoController.getEntregasCliente(usuarioLogado.getId());
    }

    // Funcionalidades Motorista
    public boolean atualizarPosicaoEntrega(String idEntrega, String posicaoAtual) {
        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.motorista) {
            return false;
        }
        return motoristaController.atualizarPosicao(idEntrega, posicaoAtual);
    }

    public ArrayList<Entrega> listarEntregasMotorista() {
        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.motorista) {
            return new ArrayList<>();
        }
        return motoristaController.getEntregasMotorista(usuarioLogado.getId());
    }

    // Funcionalidades Admin
    public ArrayList<Entrega> listarTodasEntregas() {
        if (usuarioLogado == null || usuarioLogado.getTipo() != TipoUsuario.administrador) {
            return new ArrayList<>();
        }
        return adminController.getTodasEntregas();
    }

    public boolean atualizarUsuario(Usuario usuario) {
        return cadastroController.atualizarUsuario(usuario);
    }

    public PedidosController getPedidoController() {
        return pedidoController;
    }

    public MotoristaController getMotoristaController() {
        return motoristaController;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public ArrayList<Usuario> getMotoristas() {
        return adminController.getMotoristas();
    }

    public boolean atribuirMotoristaEntrega(String entregaId, String motoristaId) {
        return adminController.atribuirMotorista(entregaId, motoristaId);
    }

    public void logout() {
        this.usuarioLogado = null;
    }

    public ArrayList<Entrega> getPedidosDisponiveis() {
        // Aqui você deve implementar a lógica para buscar todos os pedidos que ainda não foram atribuídos a um motorista
        return pedidoController.getPedidosDisponiveis(); // Certifique-se de que este método existe no seu PedidosController
    }
}