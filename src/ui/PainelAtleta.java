package ui;

import model.treino.Treino;
import service.GerenciadorTreinos;
import javax.swing.*;
import java.awt.BorderLayout;

public class PainelAtleta extends JFrame {

    public PainelAtleta(GerenciadorTreinos g) {
        super("Painel do Atleta");

        JTextArea area = new JTextArea();
        area.setEditable(false);

        StringBuilder sb = new StringBuilder();

        sb.append("=== TREINO DE HOJE ===\n");
        if (g.treinosDoDia().isEmpty()) {
            sb.append("Nenhum treino hoje.\n");
        }
        for (Treino t : g.treinosDoDia()) {
            sb.append(t.gerarFichaTecnica()).append("\n");
        }

        sb.append("\n=== TODOS OS TREINOS ===\n");
        for (Treino t : g.listar()) {
            sb.append(t.gerarFichaTecnica()).append("\n");
        }

        sb.append("\nTempo total nos próximos 7 dias: ")
                .append(g.somarTempoSemana()).append(" min");

        area.setText(sb.toString());

        add(new JScrollPane(area), BorderLayout.CENTER);

        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}