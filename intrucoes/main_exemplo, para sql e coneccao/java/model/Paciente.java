package model;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private String BI;
    private String nome;
    private int idade;
    public static List<Paciente> pacientes = new ArrayList<Paciente>();
    public Paciente(String BI, String nome, int idade) {
        this.BI = BI;
        this.nome = nome;
        this.idade = idade;
    }

    public String getBI() {
        return BI;
    }

    public void setBI(String BI) {
        this.BI = BI;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Bilhete: "+this.BI+
                "\nNome: "+this.nome+
                "\nIdade: "+this.idade;
    }
}
