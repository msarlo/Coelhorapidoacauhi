package model;

public class Administrador extends Usuario{

    public Administrador(String id, String nome, String email, String senha, String telefone, String tipo, boolean status) {
        super(id, nome, email, senha, telefone, tipo, status);
    }

    public Administrador() {

    }
}
