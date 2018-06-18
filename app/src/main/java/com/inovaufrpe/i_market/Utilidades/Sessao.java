package com.inovaufrpe.i_market.Utilidades;

import com.inovaufrpe.i_market.Dominio.Carrinho;
import com.inovaufrpe.i_market.Dominio.Cliente;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.Dominio.Usuario;

import java.util.ArrayList;

public class Sessao{

  private static Sessao instance = null;
  private Usuario usuario;
  private Cliente cliente;
  private Carrinho carrinho_atual = new Carrinho();
  private ArrayList<Produto> produtos = new ArrayList<>();
  private Double totalPagar = 0.0;

  private Sessao(){

  }

  public static Sessao getInstancia(){
    if (instance == null){
      instance = new Sessao();
    }

    return instance;
  }

  public void setProdutos(ArrayList<Produto> produtos){
    this.produtos = produtos;
  }

    public ArrayList getProdutos() {
        return produtos;
    }

    public void setUsuario(Usuario usuario){
    this.usuario = usuario;
  }

  public Usuario getUsuario(){
    return this.usuario;
  }

  public void setCliente(Cliente cliente){
    this.cliente = cliente;
  }

  public Cliente getCliente(){
    return this.cliente;
  }

  public void setCarrinho(Carrinho carrinho_atual){
    this.carrinho_atual = carrinho_atual;
  }

  public Carrinho getCarrinho(){
    return this.carrinho_atual;
  }

  public Double getTotalPagar() {
    return totalPagar;
  }

  public void setTotalPagar(Double totalPagar) {
    this.totalPagar = totalPagar;
  }
}