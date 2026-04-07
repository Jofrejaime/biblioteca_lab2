package model;

import java.util.Objects;

public class Utilizador {
    private final String id;
    private final String nomeCompleto;
    private final String contacto;

    public Utilizador(String id, String nomeCompleto, String contacto) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID do utilizador nao pode ser vazio.");
        }
        if (nomeCompleto == null || nomeCompleto.isBlank()) {
            throw new IllegalArgumentException("Nome do utilizador nao pode ser vazio.");
        }
        if (contacto == null || contacto.isBlank()) {
            throw new IllegalArgumentException("Contacto do utilizador nao pode ser vazio.");
        }

        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.contacto = contacto;
    }

    public String getId() {
        return id;
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
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Utilizador{id='" + id + "', nome='" + nomeCompleto + "', contacto='" + contacto + "'}";
    }
}