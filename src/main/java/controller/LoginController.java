package controller;

import dao.UsuarioDAO;
import model.Usuario;

public class LoginController {
    private UsuarioDAO usuarioDAO;
    
    public LoginController() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioDAO.getByEmail(email);
        if (usuario != null) {
            // Aqui você deve verificar se a senha está correta
            // Se você estiver usando hashing, você deve comparar o hash da senha
            // Exemplo: se a senha armazenada for um hash, você deve fazer o hash da senha inserida e comparar
            if (usuario.getSenha().equals(senha)) { // Substitua por comparação de hash se necessário
                return usuario; // Retorna o usuário autenticado
            }
        }
        return null; // Retorna null se a autenticação falhar
    }
        /*  Validação básica
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
    }*/
}