package repository;

import config.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Livro;

public class LivroRepository {

    public Livro inserir(Livro livro) {
        String sql = "INSERT INTO livro (isbn, titulo, autor, ano_publicacao, total_exemplares, exemplares_disponiveis) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, livro.getIsbn());
            stmt.setString(2, livro.getTitulo());
            stmt.setString(3, livro.getAutor());
            stmt.setInt(4, livro.getAnoPublicacao());
            stmt.setInt(5, livro.getTotalExemplares());
            stmt.setInt(6, livro.getExemplaresDisponiveis());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    livro.setId(keys.getInt(1));
                }
            }
            return livro;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir livro.", e);
        }
    }

    public Livro buscarPorIsbn(String isbn) {
        String sql = "SELECT id, isbn, titulo, autor, ano_publicacao, total_exemplares, exemplares_disponiveis FROM livro WHERE isbn = ?";
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapLivro(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livro por ISBN.", e);
        }
    }

    public Livro buscarPorId(int id) {
        String sql = "SELECT id, isbn, titulo, autor, ano_publicacao, total_exemplares, exemplares_disponiveis FROM livro WHERE id = ?";
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapLivro(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livro por id.", e);
        }
    }

    public List<Livro> listarTodos() {
        String sql = "SELECT id, isbn, titulo, autor, ano_publicacao, total_exemplares, exemplares_disponiveis FROM livro ORDER BY titulo";
        List<Livro> livros = new ArrayList<>();
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                livros.add(mapLivro(rs));
            }
            return livros;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar livros.", e);
        }
    }

    public void atualizarDisponibilidade(int livroId, int exemplaresDisponiveis) {
        String sql = "UPDATE livro SET exemplares_disponiveis = ? WHERE id = ?";
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, exemplaresDisponiveis);
            stmt.setInt(2, livroId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar disponibilidade do livro.", e);
        }
    }

    private Livro mapLivro(ResultSet rs) throws SQLException {
        return new Livro(
                rs.getInt("id"),
                rs.getString("isbn"),
                rs.getString("titulo"),
                rs.getInt("ano_publicacao"),
                rs.getString("autor"),
                rs.getInt("total_exemplares"),
                rs.getInt("exemplares_disponiveis")
        );
    }
}