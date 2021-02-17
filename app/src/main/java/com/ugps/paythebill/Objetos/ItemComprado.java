package com.ugps.paythebill.Objetos;

import java.util.Date;

public class ItemComprado {

    private String nome;
    private Double valor;
    private String data;
    private String comprador;

    public ItemComprado() {
    }

    public ItemComprado(String nome, Double valor, String data, String comprador) {
        this.nome = nome;
        this.valor = valor;
        this.data = data;
        this.comprador = comprador;
    }

    public String getNome() {
        return nome;
    }

    public Double getValor() {
        return valor;
    }

    public String getData() {
        return data;
    }

    public String getComprador() {
        return comprador;
    }
}
