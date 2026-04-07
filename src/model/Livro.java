package model;

import java.time.LocalDate;
import java.util.Objects;

public class Livro {
    private Integer id;
    private final String isbn;
    private final String titulo;
    private final int anoPublicacao;
    private final String autor;
    private final int totalExemplares;
    private int exemplaresDisponiveis;

    public Livro(String isbn, String titulo, int anoPublicacao, String autor, int totalExemplares) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN nao pode ser vazio.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Titulo nao pode ser vazio.");
        }
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Autor nao pode ser vazio.");
        }
        if (anoPublicacao < 1000 || anoPublicacao > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Ano de publicacao invalido.");
        }
        if (totalExemplares < 1) {
            throw new IllegalArgumentException("Um livro deve ter pelo menos 1 exemplar.");
        }

        this.isbn = isbn;
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.autor = autor;
        this.totalExemplares = totalExemplares;
        this.exemplaresDisponiveis = totalExemplares;
    }

    public Livro(Integer id, String isbn, String titulo, int anoPublicacao, String autor, int totalExemplares, int exemplaresDisponiveis) {
        this(isbn, titulo, anoPublicacao, autor, totalExemplares);
        this.id = id;
        this.exemplaresDisponiveis = exemplaresDisponiveis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public String getAutor() {
        return autor;
    }

    public int getTotalExemplares() {
        return totalExemplares;
    }

    public int getExemplaresDisponiveis() {
        return exemplaresDisponiveis;
    }

    public boolean verificarDisponibilidade() {
        return exemplaresDisponiveis > 0;
    }

    public void reduzirDisponibilidade() {
        if (!verificarDisponibilidade()) {
            throw new IllegalStateException("Nao ha exemplares disponiveis para o livro: " + titulo);
        }
        exemplaresDisponiveis--;
    }

    public void reporDisponibilidade() {
        if (exemplaresDisponiveis >= totalExemplares) {
            throw new IllegalStateException("A disponibilidade ja esta no maximo para o livro: " + titulo);
        }
        exemplaresDisponiveis++;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Livro other)) {
            return false;
        }
        return isbn.equals(other.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Livro{isbn='" + isbn + "', titulo='" + titulo + "', autor='" + autor + "', disponiveis="
                + exemplaresDisponiveis + "/" + totalExemplares + "}";
    }
}