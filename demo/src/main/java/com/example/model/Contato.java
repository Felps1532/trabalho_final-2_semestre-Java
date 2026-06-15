package com.example.model;

public class Contato extends Pessoa {
    private Empresa empresa;
    private String telefone;
    private String temperatura;

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
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

    @Override
    public String getResumo() {
        return "Contato #" + getId() + " - " + getNome();
    }
}
