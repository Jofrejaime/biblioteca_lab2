package app;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import model.Livro;
import model.Utilizador;
import model.Emprestimo;
import biblioteca.Biblioteca;

public class MenuController {
    private final Biblioteca biblioteca;
    private final Scanner scanner;

    public MenuController(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        this.scanner = new Scanner(System.in);
    }

    public void executar() {
        boolean executar = true;
        while (executar) {
            mostrarMenu();
            String opcao = scanner.nextLine().trim();

            try {
                executar = processarOpcao(opcao);
            } catch (RuntimeException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }

            if (executar) {
                System.out.println("\nPrima ENTER para continuar...");
                scanner.nextLine();
            }
        }
        System.out.println("Aplicacao terminada.");
    }

    private boolean processarOpcao(String opcao) {
        switch (opcao) {
            case "1" -> registarUtilizador();
            case "2" -> registarLivro();
            case "3" -> registarEmprestimo();
            case "4" -> devolverLivro();
            case "5" -> atualizarEmprestimos();
            case "0" -> {
                return false;
            }
            default -> System.out.println("Opcao invalida.");
        }
        return true;
    }

    private void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE BIBLIOTECA ===");
        System.out.println("1 - Registar utilizador");
        System.out.println("2 - Registar livro");
        System.out.println("3 - Realizar emprestimo");
        System.out.println("4 - Devolver livro");
        System.out.println("5 - Atualizar estado dos emprestimos");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private void registarUtilizador() {
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Contacto: ");
        String contacto = scanner.nextLine().trim();

        Utilizador util = biblioteca.registarUtilizador(new Utilizador(nome, contacto));
        System.out.println("Utilizador registado com sucesso. ID: " + util.getId());
    }

    private void registarLivro() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Titulo: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Autor: ");
        String autor = scanner.nextLine().trim();
        System.out.print("Ano de publicacao: ");
        int ano = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Total de exemplares: ");
        int totalExemplares = Integer.parseInt(scanner.nextLine().trim());

        biblioteca.registarLivro(new Livro(isbn, titulo, ano, autor, totalExemplares));
        System.out.println("Livro registado com sucesso.");
    }

    private void registarEmprestimo() {
        System.out.print("ID do utilizador: ");
        int utilizadorId = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("ISBN do livro: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Data de inicio (AAAA-MM-DD): ");
        LocalDate inicio = parseData(scanner.nextLine().trim());
        System.out.print("Data prevista de devolucao (AAAA-MM-DD): ");
        LocalDate prevista = parseData(scanner.nextLine().trim());

        Emprestimo emp = biblioteca.registarEmprestimo(utilizadorId, isbn, inicio, prevista);
        System.out.println("Emprestimo registado. ID: " + emp.getId());
    }

    private void devolverLivro() {
        System.out.print("ID do emprestimo: ");
        int emprestimoId = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Data de devolucao (AAAA-MM-DD): ");
        LocalDate dataDevolucao = parseData(scanner.nextLine().trim());

        biblioteca.devolverLivro(emprestimoId, dataDevolucao);
        System.out.println("Devolucao registada com sucesso.");
    }

    private void atualizarEmprestimos() {
        System.out.print("Data de referencia (AAAA-MM-DD): ");
        LocalDate dataAtual = parseData(scanner.nextLine().trim());
        biblioteca.atualizarEstadosEmprestimos(dataAtual);
        System.out.println("Estados dos emprestimos atualizados.");
    }

    private LocalDate parseData(String valor) {
        try {
            return LocalDate.parse(valor);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Data invalida. Use o formato AAAA-MM-DD.");
        }
    }
}
