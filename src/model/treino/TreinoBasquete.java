package model.treino;

import java.time.LocalDate;

public class TreinoBasquete extends Treino {
    public TreinoBasquete(LocalDate data, int duracaoMinutos, String descricao) {
        super(data, duracaoMinutos, descricao);
    }

    @Override
    public String getEsporte() { return "Basquete"; }

    @Override
    public String gerarFichaTecnica() {
        return "[Basquete] " + data.format(FMT) + " | " + duracaoMinutos + " min | " + descricao;
    }
}