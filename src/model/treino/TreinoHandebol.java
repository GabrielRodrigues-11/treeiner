package model.treino;

import java.time.LocalDate;

public class TreinoHandebol extends Treino {
    public TreinoHandebol(LocalDate data, int duracaoMinutos, String descricao) {
        super(data, duracaoMinutos, descricao);
    }

    @Override
    public String getEsporte() { return "Handebol"; }

    @Override
    public String gerarFichaTecnica() {
        return "[Handebol] " + data.format(FMT) + " | " + duracaoMinutos + " min | " + descricao;
    }
}