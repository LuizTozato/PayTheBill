package com.ugps.paythebill.Objetos;

import java.util.Date;

public class ItemComprado {

    private String nome;
    private Double valor;
    private Date data;
    private String comprador;

    public ItemComprado(String nome, Double valor, Date data, String comprador) {
        this.nome = nome;
        this.valor = valor;
        this.data = data;
        this.comprador = comprador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }
}
