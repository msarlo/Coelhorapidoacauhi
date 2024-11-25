package dao;

import banco.ConnectionFactory;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {
    public boolean inserir(Usuario obj) {
        String sql = "INSERT INTO usuario (nome, email, senha, telefone, tipo) VALUES (?, ?, ?, ?, ?)";
         try(Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Configura os parâmetros do SQL
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getEmail());
            stmt.setString(3, obj.getSenha());
            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getTipo());
            // Executa o comando SQL
            return stmt.executeUpdate() > 0; // Retorna true se o cadastro for bem-sucedido
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Mostra o erro no console
        }
        return false; // Retorna false se o cadastro falhar
    }


    public boolean editar(Usuario obj) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, telefone = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Configura os parâmetros do SQL com os valores do objeto Usuario
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getEmail());
            stmt.setString(3, obj.getSenha());
            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getId());

            // Executa o comando SQL
            return stmt.executeUpdate() > 0; // Retorna true se pelo menos um registro foi atualizado
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Mostra o erro no console
        }

        return false; // Retorna false se a edição falhar
    }



    public boolean apagar(String email) {

        String sql = "DELETE FROM usuario WHERE email = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, email);

            // Executa o comando SQL
            return stmt.executeUpdate() > 0; // Retorna true se um registro foi excluído
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Mostra o erro no console
        }
        return false; // Retorna false se a exclusão falhar
    }

    public Usuario getById(String key) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        Usuario usuario = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Configura o parâmetro do SQL
            stmt.setInt(1, Integer.parseInt(key));

            // Executa a consulta
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getString("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setTelefone(rs.getString("telefone"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Mostra o erro no console
        }

        return usuario; // Retorna o usuario ou null se não for encontrado
    }

    public ArrayList<Usuario> getAll() {
        String sql = "SELECT * FROM usuario";
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Processa os resultados da consulta
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getString("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTelefone(rs.getString("telefone"));

                listaUsuarios.add(usuario);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Mostra o erro no console
        }

        return listaUsuarios;
    }

    public int quantidade() {
        String sql = "SELECT COUNT(*) AS total FROM usuario";
        int total = 0;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return total;
    }


    public String login(String email, String senha) {
        String usuarioId = null;
        String sql = "SELECT id FROM usuario WHERE email = ? AND senha = ?"; // Ajuste a tabela e os campos conforme necessário

        try (Connection conn = ConnectionFactory.getConnection(); // Supondo que você tenha uma classe para gerenciar a conexão
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuarioId = String.valueOf(rs.getInt("id")); // Captura o ID do usuário
            }
        } catch (Exception e) {
            e.printStackTrace(); // Trate a exceção conforme necessário
        }

        return usuarioId; // Retorna o ID ou null
    }
}

