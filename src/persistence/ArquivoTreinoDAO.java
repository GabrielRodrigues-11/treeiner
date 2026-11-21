package persistence;

import model.treino.Treino;
import model.treino.TreinoBasquete;
import model.treino.TreinoHandebol;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Persiste os treinos em dados/treinos.txt, relativo à pasta de execução.
 * Cada linha UTF-8 contém esporte, data ISO (aaaa-MM-dd), duração e descrição,
 * separados por tabulação. Na descrição, barra invertida, tabulação e quebras
 * de linha são escapadas para preservar o texto original.
 */
public class ArquivoTreinoDAO {
    private final File arquivo = new File("dados", "treinos.txt");

    /**
     * Substitui o conteúdo do arquivo pela lista completa recebida.
     * Uma lista vazia limpa os treinos persistidos.
     */
    public void salvar(List<Treino> treinos) {
        if (treinos == null) {
            throw new IllegalArgumentException("A lista de treinos não pode ser nula.");
        }

        // Prepara e valida tudo antes de abrir o arquivo para sobrescrita.
        StringBuilder conteudo = new StringBuilder();
        for (Treino treino : treinos) {
            if (!(treino instanceof TreinoBasquete) && !(treino instanceof TreinoHandebol)) {
                throw new IllegalArgumentException("Treino nulo ou esporte não suportado.");
            }
            String esporte = treino instanceof TreinoBasquete ? "Basquete" : "Handebol";
            String descricao = treino.getDescricao().replace("\\", "\\\\")
                    .replace("\t", "\\t").replace("\r", "\\r").replace("\n", "\\n");
            conteudo.append(esporte).append('\t')
                    .append(treino.getData()).append('\t')
                    .append(treino.getDuracaoMinutos()).append('\t')
                    .append(descricao).append(System.lineSeparator());
        }

        FileWriter escritor = null;
        RuntimeException falha = null;
        try {
            File diretorio = arquivo.getParentFile();
            verificarCaminho();
            if (!diretorio.exists() && !diretorio.mkdirs()) {
                throw new IOException("Não foi possível criar o diretório de treinos.");
            }
            escritor = new FileWriter(arquivo, StandardCharsets.UTF_8);
            escritor.write(conteudo.toString());
        } catch (IOException e) {
            falha = new UncheckedIOException("Não foi possível salvar os treinos em "
                    + arquivo.getAbsolutePath(), e);
            throw falha;
        } catch (RuntimeException e) {
            falha = e;
            throw e;
        } finally {
            fechar(escritor, falha);
        }
    }

    /**
     * Reconstrói os treinos usando as subclasses existentes.
     * Retorna uma lista vazia quando ainda não há arquivo ou ele está vazio.
     * Arquivos inválidos geram uma exceção; seus registros não são ignorados.
     */
    public List<Treino> carregar() {
        List<Treino> treinos = new ArrayList<>();
        verificarCaminho();
        if (!arquivo.exists()) {
            return treinos;
        }

        FileReader leitor = null;
        RuntimeException falha = null;
        try {
            leitor = new FileReader(arquivo, StandardCharsets.UTF_8);
            BufferedReader buffer = new BufferedReader(leitor);
            String linha;
            int numeroLinha = 0;
            while ((linha = buffer.readLine()) != null) {
                numeroLinha++;
                try {
                    treinos.add(reconstruir(linha));
                } catch (IllegalArgumentException | DateTimeParseException e) {
                    throw new IllegalArgumentException("Registro inválido na linha "
                            + numeroLinha + " de " + arquivo.getAbsolutePath(), e);
                }
            }
        } catch (IOException e) {
            falha = new UncheckedIOException("Não foi possível ler os treinos de "
                    + arquivo.getAbsolutePath(), e);
            throw falha;
        } catch (RuntimeException e) {
            falha = e;
            throw e;
        } finally {
            fechar(leitor, falha);
        }
        return treinos;
    }

    private void verificarCaminho() {
        File diretorio = arquivo.getParentFile();
        if (diretorio.exists() && !diretorio.isDirectory()) {
            throw new IllegalStateException("O caminho não é um diretório: "
                    + diretorio.getAbsolutePath());
        }
        if (arquivo.exists() && !arquivo.isFile()) {
            throw new IllegalStateException("O caminho não é um arquivo: "
                    + arquivo.getAbsolutePath());
        }
    }

    private Treino reconstruir(String linha) {
        // -1 preserva o último campo quando a descrição está vazia.
        String[] campos = linha.split("\t", -1);
        if (campos.length != 4) {
            throw new IllegalArgumentException("O registro deve conter quatro campos.");
        }
        LocalDate data = LocalDate.parse(campos[1]);
        int duracao = Integer.parseInt(campos[2]);
        String descricao = restaurarDescricao(campos[3]);
        if ("Basquete".equals(campos[0])) {
            return new TreinoBasquete(data, duracao, descricao);
        }
        if ("Handebol".equals(campos[0])) {
            return new TreinoHandebol(data, duracao, descricao);
        }
        throw new IllegalArgumentException("Esporte não suportado: " + campos[0]);
    }

    private String restaurarDescricao(String texto) {
        StringBuilder descricao = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char caractere = texto.charAt(i);
            if (caractere == '\\') {
                if (++i >= texto.length()) {
                    throw new IllegalArgumentException("Escape incompleto na descrição.");
                }
                switch (texto.charAt(i)) {
                    case '\\': caractere = '\\'; break;
                    case 't': caractere = '\t'; break;
                    case 'r': caractere = '\r'; break;
                    case 'n': caractere = '\n'; break;
                    default: throw new IllegalArgumentException("Escape inválido na descrição.");
                }
            }
            descricao.append(caractere);
        }
        return descricao.toString();
    }

    /** Fecha o recurso no finally sem esconder uma falha anterior. */
    private void fechar(Closeable recurso, RuntimeException falhaAnterior) {
        if (recurso != null) {
            try {
                recurso.close();
            } catch (IOException e) {
                if (falhaAnterior != null) {
                    falhaAnterior.addSuppressed(e);
                } else {
                    throw new UncheckedIOException("Erro ao fechar o arquivo de treinos.", e);
                }
            }
        }
    }
}
