package model;

import java.time.LocalDate;

public class Emprestimo {
    private int id;
    private final LocalDate dataInicio;
    private final LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private EstadoEmprestimo estado;
    private final Utilizador utilizador;
    private final Livro livro;

    public Emprestimo(LocalDate dataInicio, LocalDate dataPrevistaDevolucao, Utilizador utilizador, Livro livro) {
        if (dataInicio == null || dataPrevistaDevolucao == null) {
            throw new IllegalArgumentException("Datas do emprestimo sao obrigatorias.");
        }
        if (dataPrevistaDevolucao.isBefore(dataInicio)) {
            throw new IllegalArgumentException("A data prevista de devolucao nao pode ser anterior a data de inicio.");
        }
        if (utilizador == null || livro == null) {
            throw new IllegalArgumentException("Utilizador e livro sao obrigatorios.");
        }

        this.dataInicio = dataInicio;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.dataDevolucao = null;
        this.estado = EstadoEmprestimo.ATIVO;
        this.utilizador = utilizador;
        this.livro = livro;
    }

    // Construtor para hidratar da BD (com ID e todos os campos)
    public Emprestimo(int id, LocalDate dataInicio, LocalDate dataPrevistaDevolucao, LocalDate dataDevolucao,
            EstadoEmprestimo estado, Utilizador utilizador, Livro livro) {
        this(dataInicio, dataPrevistaDevolucao, utilizador, livro);
        this.id = id;
        this.dataDevolucao = dataDevolucao;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public EstadoEmprestimo getEstado() {
        return estado;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public Livro getLivro() {
        return livro;
    }

    public void registar() {
        estado = EstadoEmprestimo.ATIVO;
    }

    public void devolver(LocalDate data) {
        if (estado == EstadoEmprestimo.DEVOLVIDO) {
            throw new IllegalStateException("Este emprestimo ja foi devolvido.");
        }

        dataDevolucao = data;
        estado = EstadoEmprestimo.DEVOLVIDO;
    }

    public void verificarAtraso(LocalDate dataAtual) {
        if (estado == EstadoEmprestimo.ATIVO && dataAtual.isAfter(dataPrevistaDevolucao)) {
            estado = EstadoEmprestimo.ATRASADO;
        }
    }

    @Override
    public String toString() {
        return "Emprestimo{id=" + id + ", utilizador='" + utilizador.getNomeCompleto() + "', livro='"
                + livro.getTitulo() + "', inicio=" + dataInicio + ", prevista=" + dataPrevistaDevolucao
                + ", estado=" + estado + "}";
    }
}