package model.usuario;

public class Professor extends Usuario {
    public Professor(String nome, String senha) {
        super(nome, senha);
    }

    @Override
    public boolean autenticar(String login, String senha) {
        return getNome().equalsIgnoreCase(login) && this.senha.equals(senha);
    }
}