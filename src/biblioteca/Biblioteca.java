package biblioteca;

import java.time.LocalDate;
import java.util.List;
import model.Emprestimo;
import model.EstadoEmprestimo;
import model.Livro;
import model.Utilizador;

/**
 * Fachada principal da Biblioteca.
 * Encapsula e delega operações para os managers especializados.
 */
public class Biblioteca {
    
    private final UtilizadorManager utilizadorManager;
    private final LivroManager livroManager;
    private final EmprestimoManager emprestimoManager;

    public Biblioteca() {
        this.utilizadorManager = new UtilizadorManager();
        this.livroManager = new LivroManager();
        this.emprestimoManager = new EmprestimoManager();
    }

    // ========== UTILIZADORES ==========
    
    public Utilizador registarUtilizador(Utilizador utilizador) {
        return utilizadorManager.registar(utilizador);
    }

    public Utilizador buscarUtilizadorPorId(int id) {
        return utilizadorManager.buscarPorId(id);
    }

    public List<Utilizador> listarUtilizadores() {
        return utilizadorManager.listarTodos();
    }

    // ========== LIVROS ==========
    
    public Livro registarLivro(Livro livro) {
        return livroManager.registar(livro);
    }

    public Livro buscarLivroPorIsbn(String isbn) {
        return livroManager.buscarPorIsbn(isbn);
    }

    public Livro buscarLivroPorId(int id) {
        return livroManager.buscarPorId(id);
    }

    public List<Livro> listarLivros() {
        return livroManager.listarTodos();
    }

    // ========== EMPRÉSTIMOS ==========
    
    public Emprestimo registarEmprestimo(int utilizadorId, String isbnLivro, LocalDate inicio, LocalDate prevista) {
        Utilizador utilizador = buscarUtilizadorPorId(utilizadorId);
        if (utilizador == null) {
            throw new IllegalArgumentException("Utilizador nao registado na biblioteca.");
        }

        Livro livro = buscarLivroPorIsbn(isbnLivro);
        if (livro == null) {
            throw new IllegalArgumentException("Livro nao registado na biblioteca.");
        }

        if (emprestimoManager.contarAtivosPorUtilizador(utilizadorId) >= 3) {
            throw new IllegalStateException("Utilizador atingiu o limite de 3 emprestimos ativos.");
        }

        if (!livro.verificarDisponibilidade()) {
            throw new IllegalStateException("Nao ha exemplares disponiveis para este livro.");
        }

        Emprestimo emprestimo = new Emprestimo(inicio, prevista, utilizador, livro);
        emprestimoManager.inserir(emprestimo);

        livro.reduzirDisponibilidade();
        livroManager.atualizarDisponibilidade(livro.getId(), livro.getExemplaresDisponiveis());

        return emprestimo;
    }

    public void devolverLivro(int emprestimoId, LocalDate dataDevolucao) {
        Emprestimo emprestimo = emprestimoManager.buscarPorId(emprestimoId);
        if (emprestimo == null) {
            throw new IllegalArgumentException("Emprestimo nao encontrado no historico da biblioteca.");
        }

        if (emprestimo.getEstado() == EstadoEmprestimo.DEVOLVIDO) {
            throw new IllegalStateException("Este emprestimo ja foi devolvido.");
        }

        emprestimo.devolver(dataDevolucao);
        emprestimoManager.atualizarParaDevolvido(emprestimoId, dataDevolucao);

        Livro livro = emprestimo.getLivro();
        livro.reporDisponibilidade();
        livroManager.atualizarDisponibilidade(livro.getId(), livro.getExemplaresDisponiveis());
    }

    public void atualizarEstadosEmprestimos(LocalDate dataAtual) {
        emprestimoManager.atualizarAtrasados(dataAtual);
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimoManager.listarTodos();
    }
}
