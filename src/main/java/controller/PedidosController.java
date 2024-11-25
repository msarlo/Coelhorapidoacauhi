package controller;

import dao.EntregaDAO;
import model.Entrega;
import model.Usuario;
import java.util.ArrayList;
import java.util.UUID;

public class PedidosController {
    private EntregaDAO entregaDAO;
    
    // Preços base por km para cada tipo de veículo
    private static final double PRECO_KM_MOTO = 2.0;
    private static final double PRECO_KM_CARRO = 3.0;
    private static final double PRECO_KM_CAMINHAO = 5.0;
    
    public PedidosController() {
        this.entregaDAO = new EntregaDAO();
    }
    
    public String cadastrarPedido(Usuario cliente, String origem, String destino, double distancia) {
        if (cliente == null || origem == null || destino == null || distancia <= 0) {
            return null; // ou lançar uma exceção
        }
    
        double preco = calcularPreco(distancia);
        
        Entrega novoPedido = new Entrega(
            UUID.randomUUID().toString(),
            cliente.getId(),
            null, // motorista será atribuído depois
            origem,
            destino,
            distancia,
            preco,
            true // status ativo
        );
    
        if (entregaDAO.inserir(novoPedido)) {
            return novoPedido.getId(); // Retorna o ID da nova entrega
        }
        
        return null; // Se a inserção falhar
    }
    
    private double calcularPreco(double distancia) {
        if (distancia <= 5.0) {
            return distancia * PRECO_KM_MOTO;
        } else if (distancia <= 20.0) {
            return distancia * PRECO_KM_CARRO;
        } else {
            return distancia * PRECO_KM_CAMINHAO;
        }
    }
    
    public ArrayList<Entrega> getEntregasCliente(String idCliente) {
        return entregaDAO.getByCliente(idCliente);
    }
    
    public Entrega getPedido(String id) {
        return entregaDAO.getById(id);
    }
    
    public boolean cancelarPedido(String id) {
        Entrega pedido = entregaDAO.getById(id);
        if (pedido != null && pedido.getIdmotorista() == null) {
            pedido.setStatus(false);
            return entregaDAO.editar(pedido);
        }
        return false;
    }
    
    public ArrayList<Entrega> getPedidosAtivos() {
        return entregaDAO.getEntregasAtivas();
    }

    public ArrayList<Entrega> getPedidosDisponiveis() {
        return entregaDAO.getPedidosDisponiveis(); // Implementação que retorna pedidos que não têm motorista atribuído
    }
}