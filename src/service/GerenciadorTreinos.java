package service;

import model.treino.Treino;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorTreinos {
    private ArrayList<Treino> treinos = new ArrayList<>();

    public void cadastrar(Treino t) { treinos.add(t); }

    public List<Treino> listar() { return treinos; }

    public List<Treino> treinosDoDia() {
        List<Treino> res = new ArrayList<>();
        for (Treino t : treinos) {
            if (t.getData().equals(LocalDate.now())) res.add(t);
        }
        return res;
    }

    public int somarTempoSemana() {
        int total = 0;
        LocalDate hoje = LocalDate.now();
        for (Treino t : treinos) {
            if (!t.getData().isBefore(hoje) && !t.getData().isAfter(hoje.plusDays(6))) {
                total += t.getDuracaoMinutos();
            }
        }
        return total;
    }
}