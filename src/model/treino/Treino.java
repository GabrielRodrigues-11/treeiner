package model.treino;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Treino {
    public static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static int contador = 0;

    protected LocalDate data;
    protected int duracaoMinutos;
    protected String descricao;

    public Treino(LocalDate data, int duracaoMinutos, String descricao) {
        if (data == null) throw new IllegalArgumentException("Data inválida");
        if (duracaoMinutos <= 0) throw new IllegalArgumentException("Duração deve ser maior que zero");
        this.data = data;
        this.duracaoMinutos = duracaoMinutos;
        this.descricao = descricao;
        contador++;
    }

    public static int getContador() { return contador; }
    public LocalDate getData() { return data; }
    public int getDuracaoMinutos() { return duracaoMinutos; }
    public String getDescricao() { return descricao; }

    public abstract String getEsporte();
    public abstract String gerarFichaTecnica();
}