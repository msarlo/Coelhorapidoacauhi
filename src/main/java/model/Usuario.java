package model;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private TipoUsuario tipo;
    private boolean status;

    public enum TipoUsuario {
        cliente,
        motorista,
        administrador
    }

    public Usuario() {
    }

    public Usuario(String id, String nome, String email, String senha, String telefone, TipoUsuario tipo, boolean status) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.tipo = tipo;
        this.status = status;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }
    
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}