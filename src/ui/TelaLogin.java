package ui;

import model.usuario.Professor;
import model.usuario.Atleta;
import persistence.ArquivoTreinoDAO;
import service.GerenciadorTreinos;
import javax.swing.*;
import java.awt.GridLayout;

public class TelaLogin extends JFrame {

    public TelaLogin(GerenciadorTreinos g, ArquivoTreinoDAO dao) {
        super("Login");

        Professor professor = new Professor("professor", "1234");
        Atleta atleta = new Atleta();

        JTextField txtUsuario = new JTextField();
        JPasswordField txtSenha = new JPasswordField();
        JButton btnProfessor = new JButton("Entrar como Professor");
        JButton btnAtleta = new JButton("Entrar como Atleta");

        setLayout(new GridLayout(6, 1, 5, 5));
        add(new JLabel("Usuário:"));
        add(txtUsuario);
        add(new JLabel("Senha:"));
        add(txtSenha);
        add(btnProfessor);
        add(btnAtleta);

        btnProfessor.addActionListener(e -> {
            String senha = new String(txtSenha.getPassword());
            if (professor.autenticar(txtUsuario.getText(), senha)) {
                new PainelProfessor(g, dao).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos");
            }
        });

        btnAtleta.addActionListener(e -> {
            if (atleta.autenticar("", "")) {
                new PainelAtleta(g).setVisible(true);
                dispose();
            }
        });

        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}