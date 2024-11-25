package controller;

import dao.EntregaDAO;
import model.Entrega;
import java.util.ArrayList;

public class MotoristaController {
    private EntregaDAO entregaDAO;
    
    public MotoristaController() {
        this.entregaDAO = new EntregaDAO();
    }
    
    public ArrayList<Entrega> getEntregasMotorista(String idMotorista) {
        return entregaDAO.getByMotorista(idMotorista);
    }
    
    public boolean atualizarPosicao(String idEntrega, String posicaoAtual) {
        if (idEntrega == null || posicaoAtual == null || posicaoAtual.trim().isEmpty()) {
            return false;
        }
        
        Entrega entrega = entregaDAO.getById(idEntrega);
        if (entrega != null && entrega.isStatus()) {
            return true;
        }
        return false;
    }
    
    public boolean finalizarEntrega(String idEntrega) {
        Entrega entrega = entregaDAO.getById(idEntrega);
        if (entrega != null && entrega.isStatus()) {
            entrega.setStatus(false);
            return entregaDAO.editar(entrega);
        }
        return false;
    }
    
    public boolean aceitarEntrega(String idEntrega, String idMotorista) {
        Entrega entrega = entregaDAO.getById(idEntrega);
        if (entrega != null && entrega.isStatus() && entrega.getIdmotorista() == null) {
            entrega.setIdmotorista(idMotorista);
            return entregaDAO.editar(entrega);
        }
        return false;
    }
}