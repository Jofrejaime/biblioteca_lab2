package repository;

import config.Conexao;
import model.Medico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepository{

    public static List<Medico> medicos =  new ArrayList<>();
    private Connection conn;
        private void getConnection()
    {
        this.conn= Conexao.getInstance().getConnection();
       
    }
    public void insert(Medico medico){
        try {
            this.getConnection();
            String sql = "INSERT INTO Medico (nome, especialidade, salario, CodM) VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getEspecialidade());
            stmt.setDouble(3, medico.getSalario());
            stmt.setInt(4, medico.getCodM());
            stmt.execute();
            stmt.close();
                medicos.add(medico);

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void delete(int codM){
        
            this.getConnection();
        String sql =  "DELETE FROM medico where CodM = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, codM);
            stmt.execute();
            medicos.removeIf(medico -> medico.getCodM() == codM);
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public List<Medico> listarMedicos(){
        
            this.getConnection();
        List<Medico> ms = new ArrayList<>();
        String sql = "SELECT * FROM Medico";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet r =  stmt.executeQuery();
          while(r.next()){
              if (r.next())
              {
                  int codM = r.getInt("CodM");
                  String nome = r.getString("Nome");
                  String especialidade = r.getString("Especialidade");
                  double salario = r.getDouble("Salario");
                  ms.add(new Medico(codM, nome, especialidade, salario));
              }
          }
        } catch (SQLException e) {
           e.printStackTrace();
        }
    return ms;
    }
    public  Medico buscar(int codM){
        
            this.getConnection();
        String sql = "SELECT * FROM Medico where CodM = ?";
        Medico m = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, codM);
            ResultSet r =  stmt.executeQuery();
            if(r.next()){
               m =  new Medico(r.getInt("CodM"), r.getString("Nome"),r.getString("Especialidade"),r.getDouble("Salario"));
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return m;
    }

}
