package model.usuario;

public class Atleta extends Usuario {
    private String equipe;
    private static int totalAcessosEquipe = 0;

    // Construtor padrão usado pela TelaLogin
    public Atleta() {
        super("atleta", "");
        this.equipe = "Equipe Profissional";
    }

    // Sobrecarga de construtor
    public Atleta(String nome, String equipe) {
        super(nome, "");
        setEquipe(equipe);
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        if (equipe == null || equipe.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da equipe não pode ser vazio.");
        }
        this.equipe = equipe;
    }

    public static int getTotalAcessosEquipe() {
        return totalAcessosEquipe;
    }

    @Override
    public boolean autenticar(String login, String senha) {
        totalAcessosEquipe++;
        return true; // Acesso compartilhado liberado para visualização dos treinos do dia
    }

    @Override
    public String exibirPerfil() {
        return "Conta Compartilhada | " + this.equipe + " (Acessos: " + totalAcessosEquipe + ")";
    }
}
