package model;

import java.util.Objects;

public class Utilizador {
    private int id;
    private final String nomeCompleto;
    private final String contacto;

    // Construtor para criação (sem ID - será gerado pela BD)
    public Utilizador(String nomeCompleto, String contacto) {
        if (nomeCompleto == null || nomeCompleto.isBlank()) {
            throw new IllegalArgumentException("Nome do utilizador nao pode ser vazio.");
        }
        if (contacto == null || contacto.isBlank()) {
            throw new IllegalArgumentException("Contacto do utilizador nao pode ser vazio.");
        }

        this.nomeCompleto = nomeCompleto;
        this.contacto = contacto;
    }

    // Construtor para hidratar da BD (com ID)
    public Utilizador(int id, String nomeCompleto, String contacto) {
        this(nomeCompleto, contacto);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getContacto() {
        return contacto;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Utilizador other)) {
            return false;
        }
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Utilizador{id=" + id + ", nome='" + nomeCompleto + "', contacto='" + contacto + "'}";
    }
}