package model.usuario;

public abstract class Usuario {
    private String nome;
    protected String senha;

    // Construtor padrão
    public Usuario() {
        this.nome = "Convidado";
        this.senha = "";
    }

    // Construtor parametrizado (mantém compatibilidade com o código atual)
    public Usuario(String nome, String senha) {
        setNome(nome);
        setSenha(senha);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário não pode ser nulo ou vazio.");
        }
        this.nome = nome.trim();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null) {
            throw new IllegalArgumentException("A senha não pode ser nula.");
        }
        this.senha = senha;
    }

    // Método abstrato que obriga as subclasses a implementarem (Polimorfismo)
    public abstract boolean autenticar(String login, String senha);

    // Método concreto que pode ser sobrescrito pelas subclasses
    public String exibirPerfil() {
        return "Usuário: " + this.nome;
    }
}