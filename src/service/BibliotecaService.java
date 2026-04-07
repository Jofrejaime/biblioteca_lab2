package service;

import java.time.LocalDate;
import java.util.List;
import model.Emprestimo;
import model.EstadoEmprestimo;
import model.Livro;
import model.Utilizador;
import repository.EmprestimoRepository;
import repository.LivroRepository;
import repository.UtilizadorRepository;

public class BibliotecaService {
    private final UtilizadorRepository utilizadorRepository;
    private final LivroRepository livroRepository;
    private final EmprestimoRepository emprestimoRepository;

    public BibliotecaService() {
        this.utilizadorRepository = new UtilizadorRepository();
        this.livroRepository = new LivroRepository();
        this.emprestimoRepository = new EmprestimoRepository();
    }

    public void registarUtilizador(Utilizador utilizador) {
        if (utilizadorRepository.existePorId(utilizador.getId())) {
            throw new IllegalStateException("Ja existe utilizador com este ID.");
        }
        utilizadorRepository.inserir(utilizador);
    }

    public Livro registarLivro(Livro livro) {
        if (livroRepository.buscarPorIsbn(livro.getIsbn()) != null) {
            throw new IllegalStateException("Ja existe livro com este ISBN.");
        }
        return livroRepository.inserir(livro);
    }

    public Emprestimo registarEmprestimo(String utilizadorId, String isbnLivro, LocalDate inicio, LocalDate prevista) {
        Utilizador utilizador = utilizadorRepository.buscarPorId(utilizadorId);
        if (utilizador == null) {
            throw new IllegalArgumentException("Utilizador nao registado na biblioteca.");
        }

        Livro livro = livroRepository.buscarPorIsbn(isbnLivro);
        if (livro == null) {
            throw new IllegalArgumentException("Livro nao registado na biblioteca.");
        }

        if (emprestimoRepository.contarAtivosPorUtilizador(utilizadorId) >= 3) {
            throw new IllegalStateException("Utilizador atingiu o limite de 3 emprestimos ativos.");
        }

        if (!livro.verificarDisponibilidade()) {
            throw new IllegalStateException("Nao ha exemplares disponiveis para este livro.");
        }

        Emprestimo emprestimo = new Emprestimo(inicio, prevista, utilizador, livro);
        emprestimo.registar();
        emprestimoRepository.inserir(emprestimo);

        livro.reduzirDisponibilidade();
        livroRepository.atualizarDisponibilidade(livro.getId(), livro.getExemplaresDisponiveis());

        return emprestimo;
    }

    public void devolverLivro(String emprestimoId, LocalDate dataDevolucao) {
        Emprestimo emprestimo = emprestimoRepository.buscarPorId(emprestimoId);
        if (emprestimo == null) {
            throw new IllegalArgumentException("Emprestimo nao encontrado no historico da biblioteca.");
        }

        if (emprestimo.getEstado() == EstadoEmprestimo.DEVOLVIDO) {
            throw new IllegalStateException("Este emprestimo ja foi devolvido.");
        }

        emprestimo.devolver(dataDevolucao);
        emprestimoRepository.atualizarParaDevolvido(emprestimoId, dataDevolucao);

        Livro livro = emprestimo.getLivro();
        livro.reporDisponibilidade();
        livroRepository.atualizarDisponibilidade(livro.getId(), livro.getExemplaresDisponiveis());
    }

    public void atualizarEstadosEmprestimos(LocalDate dataAtual) {
        emprestimoRepository.atualizarAtrasados(dataAtual);
    }

    public List<Utilizador> listarUtilizadores() {
        return utilizadorRepository.listarTodos();
    }

    public List<Livro> listarLivros() {
        return livroRepository.listarTodos();
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.listarTodos();
    }
}