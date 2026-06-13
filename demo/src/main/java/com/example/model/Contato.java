package com.example.model;

public class Contato extends Pessoa {
    private Empresa empresa;
    private String temperatura;

    public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    public String getTemperatura() {
        return temperatura;
    }
    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }
}
