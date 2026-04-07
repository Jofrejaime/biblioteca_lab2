package repository;

import config.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Emprestimo;
import model.EstadoEmprestimo;
import model.Livro;
import model.Utilizador;

public class EmprestimoRepository {

    public void inserir(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (id, data_inicio, data_prevista_devolucao, data_devolucao, estado, utilizador_id, livro_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emprestimo.getId());
            stmt.setDate(2, Date.valueOf(emprestimo.getDataInicio()));
            stmt.setDate(3, Date.valueOf(emprestimo.getDataPrevistaDevolucao()));
            if (emprestimo.getDataDevolucao() != null) {
                stmt.setDate(4, Date.valueOf(emprestimo.getDataDevolucao()));
            } else {
                stmt.setDate(4, null);
            }
            stmt.setInt(5, toEstadoId(emprestimo.getEstado()));
            stmt.setString(6, emprestimo.getUtilizador().getId());
            stmt.setInt(7, emprestimo.getLivro().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir emprestimo.", e);
        }
    }

    public Emprestimo buscarPorId(String id) {
        String sql = baseSelect() + " WHERE e.id = ?";
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapEmprestimo(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar emprestimo.", e);
        }
    }

    public List<Emprestimo> listarTodos() {
        String sql = baseSelect() + " ORDER BY e.data_inicio";
        List<Emprestimo> emprestimos = new ArrayList<>();
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                emprestimos.add(mapEmprestimo(rs));
            }
            return emprestimos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar emprestimos.", e);
        }
    }

    public int contarAtivosPorUtilizador(String utilizadorId) {
        String sql = "SELECT COUNT(*) FROM emprestimo WHERE utilizador_id = ? AND estado IN (1, 3)";
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, utilizadorId);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar emprestimos ativos.", e);
        }
    }

    public void atualizarParaDevolvido(String emprestimoId, java.time.LocalDate dataDevolucao) {
        String sql = "UPDATE emprestimo SET estado = 2, data_devolucao = ? WHERE id = ?";
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(dataDevolucao));
            stmt.setString(2, emprestimoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar devolucao do emprestimo.", e);
        }
    }

    public void atualizarAtrasados(java.time.LocalDate dataAtual) {
        String sql = "UPDATE emprestimo SET estado = 3 WHERE estado = 1 AND data_prevista_devolucao < ?";
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(dataAtual));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar emprestimos atrasados.", e);
        }
    }

    private String baseSelect() {
        return "SELECT e.id AS e_id, e.data_inicio, e.data_prevista_devolucao, e.data_devolucao, e.estado, "
                + "u.id AS u_id, u.nome_completo, u.contacto, "
                + "l.id AS l_id, l.isbn, l.titulo, l.autor, l.ano_publicacao, l.total_exemplares, l.exemplares_disponiveis "
                + "FROM emprestimo e "
                + "JOIN utilizador u ON u.id = e.utilizador_id "
                + "JOIN livro l ON l.id = e.livro_id";
    }

    private Emprestimo mapEmprestimo(ResultSet rs) throws SQLException {
        Utilizador utilizador = new Utilizador(
                rs.getString("u_id"),
                rs.getString("nome_completo"),
                rs.getString("contacto")
        );

        Livro livro = new Livro(
                rs.getInt("l_id"),
                rs.getString("isbn"),
                rs.getString("titulo"),
                rs.getInt("ano_publicacao"),
                rs.getString("autor"),
                rs.getInt("total_exemplares"),
                rs.getInt("exemplares_disponiveis")
        );

        Date dataDevolucaoSql = rs.getDate("data_devolucao");
        java.time.LocalDate dataDevolucao = dataDevolucaoSql == null ? null : dataDevolucaoSql.toLocalDate();

        return new Emprestimo(
                rs.getString("e_id"),
                rs.getDate("data_inicio").toLocalDate(),
                rs.getDate("data_prevista_devolucao").toLocalDate(),
                dataDevolucao,
                fromEstadoId(rs.getInt("estado")),
                utilizador,
                livro
        );
    }

    private int toEstadoId(EstadoEmprestimo estado) {
        return switch (estado) {
            case ATIVO -> 1;
            case DEVOLVIDO -> 2;
            case ATRASADO -> 3;
        };
    }

    private EstadoEmprestimo fromEstadoId(int estadoId) {
        return switch (estadoId) {
            case 1 -> EstadoEmprestimo.ATIVO;
            case 2 -> EstadoEmprestimo.DEVOLVIDO;
            case 3 -> EstadoEmprestimo.ATRASADO;
            default -> throw new IllegalStateException("Estado de emprestimo desconhecido: " + estadoId);
        };
    }
}