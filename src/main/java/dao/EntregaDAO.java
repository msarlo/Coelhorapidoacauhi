package dao;

import banco.ConnectionFactory;
import model.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EntregaDAO {

    public boolean inserir(Entrega obj) {

        String sql = "INSERT INTO entrega ( idusuario, origem, destino, distancia, preco, status) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, obj.getIdcliente());
            stmt.setString(2, obj.getIdmotorista());
            stmt.setString(3, obj.getOrigem());
            stmt.setString(4, obj.getDestino());
            stmt.setDouble(5, obj.getDistancia());
            stmt.setDouble(6, obj.getPreco());
            stmt.setBoolean(7, obj.isStatus());

            // Executa o comando SQL
            return stmt.executeUpdate() > 0; // Retorna true se o cadastro for bem-sucedido
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Mostra o erro no console
        }
        return false; // Retorna false se o cadastro falhar
    }

    public boolean editar(Entrega obj) {
        String sql = "UPDATE entrega SET idcliente = ?, idmotorista = ?, origem = ?, destino = ?, distancia = ?, preco = ?, status = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Configura os parâmetros do SQL com os valores do objeto Entrega
            stmt.setString(1, obj.getIdcliente());
            stmt.setString(2, obj.getIdmotorista());
            stmt.setString(3, obj.getOrigem());
            stmt.setString(4, obj.getDestino());
            stmt.setDouble(5, obj.getDistancia());
            stmt.setDouble(6, obj.getPreco());
            stmt.setBoolean(7, obj.isStatus());
            stmt.setString(8, obj.getId());

            // Executa o comando SQL
            return stmt.executeUpdate() > 0; // Retorna true se pelo menos um registro foi atualizado
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Mostra o erro no console
        }

        return false; // Retorna false se a edição falhar
    }

    public boolean apagar(String id) {
        String sql = "DELETE FROM entrega WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);

            // Executa o comando SQL
            return stmt.executeUpdate() > 0; // Retorna true se um registro foi excluído
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Mostra o erro no console
        }
        return false; // Retorna false se a exclusão falhar
    }

    public Entrega getById(String id) {
        String sql = "SELECT * FROM entrega WHERE id = ?";
        Entrega entrega = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Configura o parâmetro do SQL
            stmt.setString(1, id);

            // Executa a consulta
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    entrega = new Entrega(
                            rs.getString("id"),
                            rs.getString("idcliente"),
                            rs.getString("idmotorista"),
                            rs.getString("origem"),
                            rs.getString("destino"),
                            rs.getDouble("distancia"),
                            rs.getDouble("preco"),
                            rs.getBoolean("status")
                    );
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Mostra o erro no console
        }

        return entrega; // Retorna a entrega ou null se não for encontrada
    }

    public ArrayList<Entrega> getAll() {
        String sql = "SELECT * FROM entrega";
        ArrayList<Entrega> listaEntregas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Processa os resultados da consulta
            while (rs.next()) {
                Entrega entrega = new Entrega(
                        rs.getString("id"),
                        rs.getString("idcliente"),
                        rs.getString("idmotorista"),
                        rs.getString("origem"),
                        rs.getString("destino"),
                        rs.getDouble("distancia"),
                        rs.getDouble("preco"),
                        rs.getBoolean("status")
                );

                listaEntregas.add(entrega);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Mostra o erro no console
        }

        return listaEntregas; // Retorna a lista de entregas
    }

    public int quantidade() {
        String sql = "SELECT COUNT(*) AS total FROM entrega";
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

        return total; // Retorna a quantidade total de entregas
    }
}