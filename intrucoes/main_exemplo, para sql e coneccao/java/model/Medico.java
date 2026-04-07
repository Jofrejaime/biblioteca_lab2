package model;

public class Medico {
    private int codM;
    private String nome;
    private String especialidade;
    private double salario;
    private static int counter = 0;
    public Medico(int codM,String nome, String especialidade, double salario) {
        this.codM = codM;
        this.nome = nome;
        this.especialidade = especialidade;
        this.salario = salario;
    }

    public int getCodM() {
        return codM;
    }

    public void setCodM(int codM) {
        this.codM = codM;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Medico.counter = counter;
    }

    @Override
    public String toString() {
        return  "Medico: " + codM +
                "\nNome: " + nome +
                "\nEspecialidade: " + especialidade +
                "\nSalario: " + salario;
    }
}
