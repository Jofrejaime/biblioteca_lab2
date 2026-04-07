package biblioteca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Utilizador;
import config.Conexao;

/**
 * Gerencia operações relacionadas com Utilizadores.
 */
public class UtilizadorManager {

    public Utilizador registar(Utilizador utilizador) {
        String sql = "INSERT INTO utilizador (nome_completo, contacto) VALUES (?, ?)";
        try (Connection conn = Conexao.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, utilizador.getNomeCompleto());
            stmt.setString(2, utilizador.getContacto());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    utilizador.setId(keys.getInt(1));
                }
            }
            return utilizador;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir utilizador.", e);
        }
    }

    public Utilizador buscarPorId(int id) {
        String sql = "SELECT id, nome_completo, contacto FROM utilizador WHERE id = ?";
        try (Connection conn = Conexao.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilizador(
                            rs.getInt("id"),
                            rs.getString("nome_completo"),
                            rs.getString("contacto")
                    );
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar utilizador.", e);
        }
    }

    public boolean existe(int id) {
        String sql = "SELECT 1 FROM utilizador WHERE id = ?";
        try (Connection conn = Conexao.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar utilizador.", e);
        }
    }

    public List<Utilizador> listarTodos() {
        String sql = "SELECT id, nome_completo, contacto FROM utilizador ORDER BY nome_completo";
        List<Utilizador> utilizadores = new ArrayList<>();
        try (Connection conn = Conexao.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                utilizadores.add(new Utilizador(
                        rs.getInt("id"),
                        rs.getString("nome_completo"),
                        rs.getString("contacto")
                ));
            }
            return utilizadores;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar utilizadores.", e);
        }
    }
}
