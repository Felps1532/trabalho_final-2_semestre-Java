package com.example.model;

import java.time.LocalDateTime;

public abstract class EntidadeBase {
    private int id;
    private LocalDateTime dataEntrada;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }
    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getResumo() {
        return "Entidade ID: " + id;
    }
}
