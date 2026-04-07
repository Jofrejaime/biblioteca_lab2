package repository;

import config.Conexao;
import model.Paciente;

import static model.Paciente.pacientes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {
    private Connection conexao;
    private void getConnection()
    {
        this.conexao = Conexao.getInstance().getConnection();
    }
    public void insert (Paciente paciente){
        try
        {
            this.getConnection();
            String sql = "INSERT INTO Paciente (BI, Nome, Idade) VALUES (?, ?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, paciente.getBI());
            stmt.setString(2, paciente.getNome());
            stmt.setInt(3, paciente.getIdade());
            stmt.execute();
            stmt.close();
            pacientes.add(paciente);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void update (String BI, String nome, int idade){

        try {
             this.getConnection();
            String sql = "UPDATE paciente set Nome = ?, Idade = ? where BI = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(3, BI);
            stmt.setString(1, nome);
            stmt.setInt(2, idade);
            stmt.execute();
            stmt.close();
        }catch (SQLException e)
            {
            e.printStackTrace();
            }
    }
    public  void delete (String BI){
        String sql = "DELETE FROM paciente where BI = ?";
        try {
             this.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, BI);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Paciente> listarPacientes(){
        List<Paciente> pacientes = new ArrayList<Paciente>();
        try {
             this.getConnection();
            String sql = "SELECT * FROM paciente";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Paciente p = new  Paciente(rs.getString("BI"), rs.getString("Nome"), rs.getInt("Idade") );
                pacientes.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacientes;
    }
    public Paciente buscarPaciente(String BI){
        Paciente paciente = null;
        try {
             this.getConnection();
            String sql = "SELECT * FROM paciente where BI = ?";
            PreparedStatement stmt =  conexao.prepareStatement(sql);
            stmt.setString(1, BI);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
            {
                paciente = new  Paciente(rs.getString("BI"), rs.getString("Nome"), rs.getInt("Idade") );
                if(!pacientes.contains(paciente))
                    pacientes.add(paciente);
            }
        }catch (SQLException e)
            {
            e.printStackTrace();
            }
    return paciente;
    }
}
