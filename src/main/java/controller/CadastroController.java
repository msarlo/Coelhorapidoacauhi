package controller;

import dao.UsuarioDAO;
import model.Usuario;

public class CadastroController {
    
    public boolean cadastrarUsuario(String nome, String email, String senha, String telefone,String tipo) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setTelefone(telefone);
        usuario.setTipo(tipo);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.inserir(usuario);
    }
}
