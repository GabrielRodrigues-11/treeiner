package model.treino;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe abstrata que representa um treino genérico.
 * As subclasses (TreinoBasquete, TreinoHandebol) definem o esporte
 * e a forma de gerar a ficha técnica (polimorfismo).
 */
public abstract class Treino {

    /** Formatador de datas padrão do sistema: dd/MM/yyyy. */
    public static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Contador global de treinos criados (compartilhado por todas as instâncias). */
    private static int contador = 0;

    protected LocalDate data;
    protected int duracaoMinutos;
    protected String descricao;

    public Treino(LocalDate data, int duracaoMinutos, String descricao) {
        if (data == null) throw new IllegalArgumentException("Data inválida");
        if (duracaoMinutos <= 0) throw new IllegalArgumentException("Duração deve ser maior que zero");
        this.data = data;
        this.duracaoMinutos = duracaoMinutos;
        this.descricao = (descricao == null) ? "" : descricao;
        contador++;
    }

    // ---------- Membros static ----------

    /** Retorna quantos treinos já foram criados. */
    public static int getContador() { return contador; }

    /** Converte uma LocalDate para texto no formato dd/MM/yyyy. */
    public static String formatarData(LocalDate data) {
        return data.format(FMT);
    }

    /** Converte um texto dd/MM/yyyy para LocalDate. */
    public static LocalDate converterData(String texto) {
        return LocalDate.parse(texto.trim(), FMT);
    }

    // ---------- Getters ----------

    public LocalDate getData() { return data; }
    public int getDuracaoMinutos() { return duracaoMinutos; }
    public String getDescricao() { return descricao; }

    /** Data já formatada (dd/MM/yyyy). */
    public String getDataFormatada() { return formatarData(data); }

    // ---------- Métodos abstratos (polimorfismo) ----------

    public abstract String getEsporte();

    /** Gera a ficha técnica do treino; cada esporte monta a sua. */
    public abstract String gerarFichaTecnica();

    @Override
    public String toString() {
        return gerarFichaTecnica();
    }
}
