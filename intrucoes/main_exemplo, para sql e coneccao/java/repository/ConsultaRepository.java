package repository;

import config.Conexao;
import model.Consulta;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {
    public  static List<Consulta> consultas =  new ArrayList<>();
    private Connection conexao;

    private void getConnection()
    {
        this.conexao = Conexao.getInstance().getConnection();
       
    }
    public List<Consulta> listarConsultas() {

       String sql = "SELECT * FROM Consulta";
       List<Consulta> cs = new ArrayList<>();
       try {
        this.getConnection();
           PreparedStatement stmt = conexao.prepareStatement(sql);
           ResultSet rs = stmt.executeQuery();
           while (rs.next()) {
               Consulta c = new Consulta(rs.getString("BI"), rs.getInt("CodM"), rs.getDouble("preco"), LocalDate.parse((CharSequence) rs.getDate("data").toString()));
               c.setIdConsulta(rs.getInt("idConsulta"));
               cs.add(c);
           }

       } catch (SQLException e) {
           e.printStackTrace();
       }
       return cs;
    }
    public  void insert(Consulta c) {
        String sql = "INSERT INTO consulta (BI, CodM, preco, data) VALUES (?, ?, ?, ?)";
        try {
            this.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, c.getBI());
            stmt.setInt(2, c.getCodM());
            stmt.setDouble(3, c.getPreco());
            stmt.setDate(4, Date.valueOf(c.getData()));
            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int idConsulta){
        String sql = "DELETE FROM Consulta WHERE idConsulta = ?";
        try {
            this.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idConsulta);
            stmt.execute();
            stmt.close();
            consultas.removeIf(c -> c.getIdConsulta() == idConsulta);
        }catch (SQLException e)
            {
            e.printStackTrace();
            }
    }
    public Consulta buscaConsulta(int idConsulta){
        this.getConnection();
        String sql = "SELECT * FROM Consulta WHERE idConsulta = ?";
        Consulta c = null;
        try{
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idConsulta);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                c = new Consulta(rs.getString("BI"), rs.getInt("CodM"), rs.getDouble(("preco")), LocalDate.parse((CharSequence) rs.getDate("data").toString()) );
                c.setIdConsulta(rs.getInt("idConsulta"));
            }
        }catch (SQLException e)
            {
            e.printStackTrace();
            }
        return c;
    }
}
