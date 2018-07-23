package com.inovaufrpe.i_market.Dominio;

public class Compra{
  String uid_compra;
  String produtos;
  String uid_cliente;
  String date;
  double precoTotal = 0.0;

  public Compra(){

  }

  public String getProdutos(){
    return this.produtos;
  }

  public void setProdutos(String produtos){
    this.produtos = produtos;
  }

  public String getUid_cliente(){
    return this.uid_cliente;
  }

  public void setUid_cliente(String uid_cliente){
    this.uid_cliente = uid_cliente;
  }

  public String getUid_compra() {
    return uid_compra;
  }

  public void setUid_compra(String uid_compra) {
    this.uid_compra = uid_compra;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public double getPrecoTotal() {
    return precoTotal;
  }

  public void setPrecoTotal(double precoTotal) {
    this.precoTotal = precoTotal;
  }
}