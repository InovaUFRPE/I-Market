package com.inovaufrpe.i_market.Dominio;

public class Produto {
    private String uid;
    private String nome;
    private double preco;
    private String categoria;
    private String marca;

    public Produto(){

    }

    public Produto(String nome, double preco, String categoria, String marca) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.marca = marca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String id) {
        this.uid = id;
    }
}
