package model.usuario;

public class Atleta extends Usuario {
    public Atleta() {
        super("atleta", "");
    }

    @Override
    public boolean autenticar(String login, String senha) {
        return true;
    }
}
