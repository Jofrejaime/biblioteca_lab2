package app;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import model.Livro;
import model.Utilizador;
import service.BibliotecaService;

public class App {
    public static void main(String[] args) {
        BibliotecaService bibliotecaService = new BibliotecaService();
        Scanner scanner = new Scanner(System.in);

        boolean executar = true;
        while (executar) {
            mostrarMenu();
            String opcao = scanner.nextLine().trim();

            try {
                switch (opcao) {
                    case "1" -> registarUtilizador(scanner, bibliotecaService);
                    case "2" -> registarLivro(scanner, bibliotecaService);
                    case "3" -> registarEmprestimo(scanner, bibliotecaService);
                    case "4" -> devolverLivro(scanner, bibliotecaService);
                    case "5" -> atualizarEmprestimos(scanner, bibliotecaService);
                    case "0" -> executar = false;
                    default -> System.out.println("Opcao invalida.");
                }
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

    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE BIBLIOTECA ===");
        System.out.println("1 - Registar utilizador");
        System.out.println("2 - Registar livro");
        System.out.println("3 - Realizar emprestimo");
        System.out.println("4 - Devolver livro");
        System.out.println("5 - Atualizar estado dos emprestimos");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private static void registarUtilizador(Scanner scanner, BibliotecaService bibliotecaService) {
        System.out.print("ID do utilizador: ");
        String id = scanner.nextLine().trim();
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Contacto: ");
        String contacto = scanner.nextLine().trim();

        bibliotecaService.registarUtilizador(new Utilizador(id, nome, contacto));
        System.out.println("Utilizador registado com sucesso.");
    }

    private static void registarLivro(Scanner scanner, BibliotecaService bibliotecaService) {
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

        bibliotecaService.registarLivro(new Livro(isbn, titulo, ano, autor, totalExemplares));
        System.out.println("Livro registado com sucesso.");
    }

    private static void registarEmprestimo(Scanner scanner, BibliotecaService bibliotecaService) {
        System.out.print("ID do utilizador: ");
        String utilizadorId = scanner.nextLine().trim();
        System.out.print("ISBN do livro: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Data de inicio (AAAA-MM-DD): ");
        LocalDate inicio = parseData(scanner.nextLine().trim());
        System.out.print("Data prevista de devolucao (AAAA-MM-DD): ");
        LocalDate prevista = parseData(scanner.nextLine().trim());

        String emprestimoId = bibliotecaService.registarEmprestimo(utilizadorId, isbn, inicio, prevista).getId();
        System.out.println("Emprestimo registado: " + emprestimoId);
    }

    private static void devolverLivro(Scanner scanner, BibliotecaService bibliotecaService) {
        System.out.print("ID do emprestimo: ");
        String emprestimoId = scanner.nextLine().trim();
        System.out.print("Data de devolucao (AAAA-MM-DD): ");
        LocalDate dataDevolucao = parseData(scanner.nextLine().trim());

        bibliotecaService.devolverLivro(emprestimoId, dataDevolucao);
        System.out.println("Devolucao registada com sucesso.");
    }

    private static void atualizarEmprestimos(Scanner scanner, BibliotecaService bibliotecaService) {
        System.out.print("Data de referencia (AAAA-MM-DD): ");
        LocalDate dataAtual = parseData(scanner.nextLine().trim());
        bibliotecaService.atualizarEstadosEmprestimos(dataAtual);
        System.out.println("Estados dos emprestimos atualizados.");
    }

    private static LocalDate parseData(String valor) {
        try {
            return LocalDate.parse(valor);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Data invalida. Use o formato AAAA-MM-DD.");
        }
    }
}