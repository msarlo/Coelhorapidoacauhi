package model;

public class Usuario {
    private String id; // Identificador único
    private String nome;
    private String email; // Para login
    private String senha; // Para login
    private String telefone;
    private String tipo; //cliente, motorista, administrador
    private boolean status;

    public Usuario(String id, String nome, String email, String senha, String telefone, String tipo, boolean status) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.tipo = tipo;
        this.status = true;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public boolean isStatus() {return status;}

    public void setStatus(boolean status) {this.status = status;}
}