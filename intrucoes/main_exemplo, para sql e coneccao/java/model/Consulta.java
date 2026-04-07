package model;

import java.time.LocalDate;

public class Consulta {
    private int idConsulta;
    private int codM;
    private String BI;
    private LocalDate data;
    private double preco;
    private static int counter = 0;

    public Consulta(String BI, int CodM, double preco, LocalDate data) {
        counter++;
        this.BI = BI;
        this.codM = CodM;
        this.preco = preco;
        idConsulta = counter;
        this.data = data;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getBI() {
        return BI;
    }

    public void setBI(String BI) {
        this.BI = BI;
    }

    public int getCodM() {
        return codM;
    }

    public void setCodM(int codM) {
        this.codM = codM;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return
                "Consulta: " + idConsulta +
                "\nMedica: " + codM +
                "\nBI: " + BI + '\'' +
                "\nData: " + data +
                "\nPreco: " + preco;
    }
}
