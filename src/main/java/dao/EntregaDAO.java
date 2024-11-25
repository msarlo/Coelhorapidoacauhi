package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import banco.ConnectionFactory;
import model.Entrega;

public class EntregaDAO {
    private static final String INSERT = "INSERT INTO entrega (id, idcliente, idmotorista, origem, destino, distancia, preco, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE entrega SET idcliente=?, idmotorista=?, origem=?, destino=?, distancia=?, preco=?, status=? WHERE id=?";
    private static final String DELETE = "DELETE FROM entrega WHERE id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM entrega WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM entrega";
    private static final String SELECT_BY_CLIENT = "SELECT * FROM entrega WHERE idcliente=?";
    private static final String SELECT_BY_DRIVER = "SELECT * FROM entrega WHERE idmotorista=?";
    private static final String SELECT_ACTIVE = "SELECT * FROM entrega WHERE status=1";

    public boolean inserir(Entrega obj) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {
            
            stmt.setString(1, obj.getId());
            stmt.setString(2, obj.getIdcliente());
            stmt.setString(3, obj.getIdmotorista());
            stmt.setString(4, obj.getOrigem());
            stmt.setString(5, obj.getDestino());
            stmt.setDouble(6, obj.getDistancia());
            stmt.setDouble(7, obj.getPreco());
            stmt.setBoolean(8, obj.isStatus());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editar(Entrega obj) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {
            
            stmt.setString(1, obj.getIdcliente());
            stmt.setString(2, obj.getIdmotorista());
            stmt.setString(3, obj.getOrigem());
            stmt.setString(4, obj.getDestino());
            stmt.setDouble(5, obj.getDistancia());
            stmt.setDouble(6, obj.getPreco());
            stmt.setBoolean(7, obj.isStatus());
            stmt.setString(8, obj.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Entrega> getAll() {
        ArrayList<Entrega> entregas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                entregas.add(new Entrega(
                    rs.getString("id"),
                    rs.getString("idcliente"),
                    rs.getString("idmotorista"),
                    rs.getString("origem"),
                    rs.getString("destino"),
                    rs.getDouble("distancia"),
                    rs.getDouble("preco"),
                    rs.getBoolean("status")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return entregas;
    }

    public Entrega getById(String id) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Entrega(
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

    public ArrayList<Entrega> getByCliente(String idcliente) {
        ArrayList<Entrega> entregas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_CLIENT)) {
            
            stmt.setString(1, idcliente);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                entregas.add(new Entrega(
                    rs.getString("id"),
                    rs.getString("idcliente"),
                    rs.getString("idmotorista"),
                    rs.getString("origem"),
                    rs.getString("destino"),
                    rs.getDouble("distancia"),
                    rs.getDouble("preco"),
                    rs.getBoolean("status")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return entregas;
    }

    public ArrayList<Entrega> getByMotorista(String idmotorista) {
        ArrayList<Entrega> entregas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_DRIVER)) {
            
            stmt.setString(1, idmotorista);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                entregas.add(new Entrega(
                    rs.getString("id"),
                    rs.getString("idcliente"),
                    rs.getString("idmotorista"),
                    rs.getString("origem"),
                    rs.getString("destino"),
                    rs.getDouble("distancia"),
                    rs.getDouble("preco"),
                    rs.getBoolean("status")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return entregas;
    }

    public ArrayList<Entrega> getEntregasAtivas() {
        ArrayList<Entrega> entregas = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ACTIVE);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                entregas.add(new Entrega(
                    rs.getString("id"),
                    rs.getString("idcliente"),
                    rs.getString("idmotorista"),
                    rs.getString("origem"),
                    rs.getString("destino"),
                    rs.getDouble("distancia"),
                    rs.getDouble("preco"),
                    rs.getBoolean("status")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return entregas;
    }
}