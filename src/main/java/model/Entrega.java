package model;

import java.util.Date;

public class Entrega {
    private String id;
    private String idcliente;
    private String idmotorista;
    private String origem;
    private String destino;
    private double distancia;
    private double valor;
    private boolean status;


    public Entrega() {
    }

    public Entrega(String id, String idcliente, String idmotorista, String origem, String destino, double distancia, double preco, boolean status) {
        this.id = id;
        this.idcliente = idcliente;
        this.idmotorista = idmotorista;
        this.origem = origem;
        this.destino = destino;
        this.distancia = distancia;
        this.valor = preco;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public String getIdmotorista() {
        return idmotorista;
    }

    public void setIdmotorista(String idmotorista) {
        this.idmotorista = idmotorista;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getPreco() {
        return valor;
    }

    public void setPreco(double preco) {
        this.valor = preco;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setIdMotorista(String id)
    {

    }
}