package model.usuario;

public abstract class Usuario {
    private String nome;
    protected String senha;

    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public String getNome() { return nome; }

    public abstract boolean autenticar(String login, String senha);
}
