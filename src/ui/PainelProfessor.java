package ui;

import model.treino.Treino;
import model.treino.TreinoBasquete;
import model.treino.TreinoHandebol;
import persistence.ArquivoTreinoDAO;
import service.GerenciadorTreinos;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PainelProfessor extends JFrame {

    private GerenciadorTreinos g;
    private ArquivoTreinoDAO dao;
    private JTextArea area = new JTextArea();

    public PainelProfessor(GerenciadorTreinos g, ArquivoTreinoDAO dao) {
        super("Painel do Professor");
        this.g = g;
        this.dao = dao;

        JComboBox<String> cbEsporte = new JComboBox<>(new String[]{"Basquete", "Handebol"});
        JTextField txtData = new JTextField(LocalDate.now().format(Treino.FMT));
        JTextField txtDuracao = new JTextField();
        JTextField txtDescricao = new JTextField();
        JButton btnCadastrar = new JButton("Cadastrar treino");

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.add(new JLabel("Esporte:"));
        form.add(cbEsporte);
        form.add(new JLabel("Data (dd/MM/aaaa):"));
        form.add(txtData);
        form.add(new JLabel("Duração (min):"));
        form.add(txtDuracao);
        form.add(new JLabel("Descrição:"));
        form.add(txtDescricao);

        area.setEditable(false);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(area), BorderLayout.CENTER);
        add(btnCadastrar, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> {
            try {
                LocalDate data = LocalDate.parse(txtData.getText().trim(), Treino.FMT);
                int duracao = Integer.parseInt(txtDuracao.getText().trim());
                String descricao = txtDescricao.getText();

                Treino t;
                if (cbEsporte.getSelectedItem().equals("Basquete")) {
                    t = new TreinoBasquete(data, duracao, descricao);
                } else {
                    t = new TreinoHandebol(data, duracao, descricao);
                }

                g.cadastrar(t);
                dao.salvar(g.listar());

                txtDuracao.setText("");
                txtDescricao.setText("");
                atualizarLista();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data inválida. Use dd/MM/aaaa");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Duração deve ser um número inteiro");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        atualizarLista();

        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void atualizarLista() {
        StringBuilder sb = new StringBuilder();
        for (Treino t : g.listar()) {
            sb.append(t.gerarFichaTecnica()).append("\n");
        }
        sb.append("\nTreinos criados nesta sessão: ").append(Treino.getContador());
        area.setText(sb.toString());
    }
}
