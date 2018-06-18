package com.inovaufrpe.i_market.Utilidades;

import com.inovaufrpe.i_market.Dominio.Carrinho;
import com.inovaufrpe.i_market.Dominio.Cliente;
import com.inovaufrpe.i_market.Dominio.Usuario;

public class Sessao{

  private static Sessao instance = null;
  private Usuario usuario;
  private Cliente cliente;
  private Carrinho carrinho_atual;

  private Sessao(){

  }

  public static Sessao getInstancia(){
    if (instance == null){
      instance = new Sessao();
    }

    return instance;
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

}