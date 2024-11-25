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
    
    public boolean cadastrarPedido(Usuario cliente, String origem, String destino, double distancia) {
        if (cliente == null || origem == null || destino == null || distancia <= 0) {
            return false;
        }
        
        // Seleciona tipo de veículo baseado na distância
        // Valores exemplo: até 5km moto, até 20km carro, acima caminhão
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
        
        return entregaDAO.inserir(novoPedido);
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
}