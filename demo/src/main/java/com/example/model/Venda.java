package com.example.model;

import java.time.LocalDateTime;

public class Venda extends EntidadeBase {
    private String descricao;
    private Contato contato;
    private Usuario vendedorResp;
    private Empresa empresa;
    private LocalDateTime dataFechamento;
    private double valor;
    private String status;

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Contato getContato() {
        return contato;
    }
    public void setContato(Contato contato) {
        this.contato = contato;
    }
    public Usuario getVendedorResp() {
        return vendedorResp;
    }
    public void setVendedorResp(Usuario vendedorResp) {
        this.vendedorResp = vendedorResp;
    }
    public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }
    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getResumo() {
        return "Venda #" + getId() + " - " + status + " - R$ " + valor;
    }
}
