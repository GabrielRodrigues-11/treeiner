import model.treino.Treino;
import persistence.ArquivoTreinoDAO;
import service.GerenciadorTreinos;
import ui.TelaLogin;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        GerenciadorTreinos g = new GerenciadorTreinos();
        ArquivoTreinoDAO dao = new ArquivoTreinoDAO();

        // carrega os treinos salvos no arquivo
        for (Treino t : dao.carregar()) {
            g.cadastrar(t);
        }

        SwingUtilities.invokeLater(() -> new TelaLogin(g, dao).setVisible(true));
    }
}