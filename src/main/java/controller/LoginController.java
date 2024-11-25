package controller;

import dao.UsuarioDAO;
import model.Usuario;

public class LoginController {
    private UsuarioDAO usuarioDAO;
    
    public LoginController() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public Usuario autenticar(String email, String senha) {
        // Validação básica
        if (email == null || email.trim().isEmpty() || 
            senha == null || senha.trim().isEmpty()) {
            return null;
        }
        
        // Busca usuário pelo email
        Usuario usuario = usuarioDAO.getByEmail(email);
        
        // Verifica se existe e se a senha está correta
        if (usuario != null && usuario.getSenha().equals(senha) && usuario.isStatus()) {
            return usuario;
        }
        
        return null;
    }
}