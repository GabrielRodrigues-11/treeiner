package service;

import model.treino.Treino;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Regras de negócio dos treinos, usando uma ArrayList<Treino>.
 */
public class GerenciadorTreinos {
    private final ArrayList<Treino> treinos = new ArrayList<>();

    // ---------- Cadastro ----------
    public void cadastrar(Treino t) {
        if (t == null) throw new IllegalArgumentException("Treino não pode ser nulo");
        treinos.add(t);
    }

    // ---------- Listagem (for-each) ----------
    public List<Treino> listar() {
        List<Treino> copia = new ArrayList<>();
        for (Treino t : treinos) {
            copia.add(t);
        }
        return copia;
    }

    /** Monta um texto com a ficha técnica de todos os treinos (for-each). */
    public String listarFichas() {
        StringBuilder sb = new StringBuilder();
        for (Treino t : treinos) {
            sb.append(t.gerarFichaTecnica()).append("\n");
        }
        return sb.toString();
    }

    // ---------- Filtro: treino do dia ----------
    public List<Treino> treinosDoDia() {
        return buscar(LocalDate.now());
    }

    // ---------- Busca (sobrecarga) ----------
    /** Busca os treinos de uma data. */
    public List<Treino> buscar(LocalDate data) {
        List<Treino> res = new ArrayList<>();
        for (Treino t : treinos) {
            if (t.getData().equals(data)) res.add(t);
        }
        return res;
    }

    /** Busca os treinos de uma data e de um esporte. */
    public List<Treino> buscar(LocalDate data, String esporte) {
        List<Treino> res = new ArrayList<>();
        for (Treino t : buscar(data)) {
            if (t.getEsporte().equalsIgnoreCase(esporte)) res.add(t);
        }
        return res;
    }

    // ---------- Soma recursiva: tempo total da semana ----------
    /** Soma os minutos de treino de hoje até hoje + 6 dias. */
    public int somarTempoSemana() {
        LocalDate hoje = LocalDate.now();
        return somarTempoRecursivo(0, hoje, hoje.plusDays(6));
    }

    /** Percorre a lista recursivamente, somando só os treinos dentro do período. */
    private int somarTempoRecursivo(int indice, LocalDate inicio, LocalDate fim) {
        if (indice >= treinos.size()) return 0; // caso base

        Treino t = treinos.get(indice);
        int minutos = (!t.getData().isBefore(inicio) && !t.getData().isAfter(fim))
                ? t.getDuracaoMinutos()
                : 0;

        return minutos + somarTempoRecursivo(indice + 1, inicio, fim);
    }
}
