package com.example.model;

public class Produto extends EntidadeBase {
    private String nome;
    private String descricao;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String getResumo() {
        return "Produto #" + getId() + " - " + nome;
    }
}
