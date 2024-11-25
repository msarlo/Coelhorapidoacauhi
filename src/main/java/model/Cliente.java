package model;

public class Cliente extends Usuario{

    public Cliente(String id, String nome, String email, String senha, String telefone, String tipo, boolean status) {
        super(id, nome, email, senha, telefone, tipo, status);
    }

    public Cliente() {
    }
}