package controller;

import dao.EntregaDAO;
import dao.UsuarioDAO;
import model.Entrega;
import model.Usuario;
import model.Usuario.TipoUsuario;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AdminController {
    private EntregaDAO entregaDAO;
    private UsuarioDAO usuarioDAO;
    
    public AdminController() {
        this.entregaDAO = new EntregaDAO();
        this.usuarioDAO = new UsuarioDAO();
    }
    
    // Gerenciamento de Entregas
    public ArrayList<Entrega> getTodasEntregas() {
        return entregaDAO.getAll();
    }
    
    public ArrayList<Entrega> getEntregasAtivas() {
        return entregaDAO.getEntregasAtivas();
    }
    
    // Gerenciamento de Usuários
    public ArrayList<Usuario> getTodosUsuarios() {
        return usuarioDAO.getAll();
    }
    
    public ArrayList<Usuario> getMotoristas() {
        return usuarioDAO.getAll().stream()
            .filter(u -> u.getTipo() == TipoUsuario.motorista)
            .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public boolean ativarDesativarUsuario(String userId, boolean status) {
        Usuario usuario = usuarioDAO.getById(userId);
        if (usuario != null) {
            usuario.setStatus(status);
            return usuarioDAO.editar(usuario);
        }
        return false;
    }
    
    public boolean atribuirMotorista(String entregaId, String motoristaId) {
        Entrega entrega = entregaDAO.getById(entregaId);
        Usuario motorista = usuarioDAO.getById(motoristaId);
        
        if (entrega != null && motorista != null && 
            motorista.getTipo() == TipoUsuario.motorista && 
            motorista.isStatus()) {
            entrega.setIdmotorista(motoristaId);
            return entregaDAO.editar(entrega);
        }
        return false;
    }
    
    public boolean cancelarEntrega(String entregaId) {
        Entrega entrega = entregaDAO.getById(entregaId);
        if (entrega != null) {
            entrega.setStatus(false);
            return entregaDAO.editar(entrega);
        }
        return false;
    }
}