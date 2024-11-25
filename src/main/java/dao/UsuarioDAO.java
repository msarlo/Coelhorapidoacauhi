package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import banco.ConnectionFactory;
import model.Usuario;
import model.Usuario.TipoUsuario;

public class UsuarioDAO {
    private static final String INSERT = "INSERT INTO usuario (id, nome, email, senha, telefone, tipo, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE usuario SET nome=?, email=?, senha=?, telefone=?, tipo=?, status=? WHERE id=?";
    private static final String DELETE = "DELETE FROM usuario WHERE id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM usuario WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM usuario";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM usuario WHERE email=?";

    public boolean inserir(Usuario obj) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
            
            stmt.setString(1, obj.getId());
            stmt.setString(2, obj.getNome());
            stmt.setString(3, obj.getEmail());
            stmt.setString(4, obj.getSenha());
            stmt.setString(5, obj.getTelefone());
            stmt.setString(6, obj.getTipo().toString());
            stmt.setBoolean(7, obj.isStatus());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario getByEmail(String email) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_EMAIL)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Usuario(
                    rs.getString("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getString("telefone"),
                    TipoUsuario.valueOf(rs.getString("tipo")),
                    rs.getBoolean("status")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean editar(Usuario obj) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getEmail());
            stmt.setString(3, obj.getSenha());
            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getTipo().toString());
            stmt.setBoolean(6, obj.isStatus());
            stmt.setString(7, obj.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario getById(String id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Usuario(
                    rs.getString("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getString("telefone"),
                    TipoUsuario.valueOf(rs.getString("tipo")),
                    rs.getBoolean("status")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deletar(String id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {
            
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Usuario> getAll() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                usuarios.add(new Usuario(
                    rs.getString("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getString("telefone"),
                    TipoUsuario.valueOf(rs.getString("tipo")),
                    rs.getBoolean("status")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}