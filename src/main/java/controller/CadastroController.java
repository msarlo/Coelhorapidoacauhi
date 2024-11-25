package controller;

import dao.UsuarioDAO;
import model.Usuario;
import model.Usuario.TipoUsuario;
import java.util.UUID;

public class CadastroController {
    private UsuarioDAO usuarioDAO;

    public CadastroController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean cadastrarUsuario(String nome, String email, String senha, String telefone, TipoUsuario tipo) {
        // Validação básica
        if (nome == null || nome.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                senha == null || senha.trim().isEmpty() ||
                telefone == null || telefone.trim().isEmpty() ||
                tipo == null) {
            return false;
        }

        // Verifica se email já existe
        if (usuarioDAO.getByEmail(email) != null) {
            return false;
        }

        try {
            Usuario novoUsuario = new Usuario(
                    UUID.randomUUID().toString(),
                    nome.trim(),
                    email.trim(),
                    senha,
                    telefone.trim(),
                    tipo,
                    true // status inicial ativo
            );

            return usuarioDAO.inserir(novoUsuario);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarUsuario(Usuario usuario) {
        // Validação básica
        if (usuario == null ||
                usuario.getNome() == null || usuario.getNome().trim().isEmpty() ||
                usuario.getEmail() == null || usuario.getEmail().trim().isEmpty() ||
                usuario.getSenha() == null || usuario.getSenha().trim().isEmpty() ||
                usuario.getTelefone() == null || usuario.getTelefone().trim().isEmpty()) {
            return false;
        }

        // Verifica se o email já existe para outro usuário
        Usuario usuarioExistente = usuarioDAO.getByEmail(usuario.getEmail());
        if (usuarioExistente != null && !usuarioExistente.getId().equals(usuario.getId())) {
            return false;
        }

        try {
            return usuarioDAO.editar(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}