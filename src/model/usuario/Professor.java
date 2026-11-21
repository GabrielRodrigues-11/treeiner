package model.usuario;

public class Professor extends Usuario {
    private String especialidade;

    // Construtor usado atualmente pela TelaLogin
    public Professor(String nome, String senha) {
        super(nome, senha);
        this.especialidade = "Esportes Coletivos";
    }

    // Sobrecarga de construtor
    public Professor(String nome, String senha, String especialidade) {
        super(nome, senha);
        setEspecialidade(especialidade);
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.trim().isEmpty()) {
            throw new IllegalArgumentException("A especialidade não pode ser vazia.");
        }
        this.especialidade = especialidade.toUpperCase();
    }

    @Override
    public boolean autenticar(String login, String senha) {
        if (login == null || senha == null) {
            return false;
        }
        return getNome().equalsIgnoreCase(login.trim()) && this.senha.equals(senha);
    }

    // Sobrecarga do método autenticar (Polimorfismo Estático)
    public boolean autenticar(String senha) {
        return this.senha != null && this.senha.equals(senha);
    }

    @Override
    public String exibirPerfil() {
        return "Treinador(a): " + getNome() + " | Especialidade: " + this.especialidade;
    }
}