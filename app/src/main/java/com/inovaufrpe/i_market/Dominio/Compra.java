package com.inovaufrpe.i_market.Dominio;

public class Compra{
  String uid_produto;
  String uid_cliente;
  int quantidade;
  double preco_unitario;

  public Compra(){

  }

  public String getUid_produto(){
    return this.uid_produto;
  }

  public void setUid_produto(String uid_produto){
    this.uid_produto = uid_produto;
  }

  public String getUid_cliente(){
    return this.uid_cliente;
  }

  public void setUid_cliente(String uid_cliente){
    this.uid_cliente = uid_cliente;
  }

  public int getQuantidade(){
    return this.quantidade;
  }

  public void setQuantidade(int quantidade){
    this.quantidade = quantidade;
  }

  public double getPreco_unitario(){
    return this.preco_unitario;
  }

  public void setPreco_unitario(double preco_unitario){
    this.preco_unitario = preco_unitario;
  }

  public double getPreco_Total(){
    return (this.preco_unitario * this.quantidade);
  }
}