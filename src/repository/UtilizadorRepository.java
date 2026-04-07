package repository;

import config.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Utilizador;

public class UtilizadorRepository {

    public void inserir(Utilizador utilizador) {
        String sql = "INSERT INTO utilizador (id, nome_completo, contacto) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, utilizador.getId());
            stmt.setString(2, utilizador.getNomeCompleto());
            stmt.setString(3, utilizador.getContacto());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir utilizador.", e);
        }
    }

    public boolean existePorId(String id) {
        String sql = "SELECT 1 FROM utilizador WHERE id = ?";
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar utilizador.", e);
        }
    }

    public Utilizador buscarPorId(String id) {
        String sql = "SELECT id, nome_completo, contacto FROM utilizador WHERE id = ?";
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilizador(
                            rs.getString("id"),
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

    public List<Utilizador> listarTodos() {
        String sql = "SELECT id, nome_completo, contacto FROM utilizador ORDER BY nome_completo";
        List<Utilizador> utilizadores = new ArrayList<>();
        try (Connection conn = Conexao.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                utilizadores.add(new Utilizador(
                        rs.getString("id"),
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