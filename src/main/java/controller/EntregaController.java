package controller;

import dao.EntregaDAO;
import dao.UsuarioDAO;
import model.Entrega;


public class EntregaController {
    public UsuarioDAO usuarioDAO;

    public EntregaController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean realizarEntrega(String enderecoOrigem, String enderecoDestino, double distancia, double valor) {
        Entrega entrega = new Entrega();

        entrega.setOrigem(enderecoOrigem);
        entrega.setDestino(enderecoDestino);
        entrega.setDistancia(distancia);
        entrega.setPreco(valor);
        entrega.setIdMotorista(null); // ID do motorista como null

        EntregaDAO dao = new EntregaDAO();
        return dao.inserir(entrega);
    }
}