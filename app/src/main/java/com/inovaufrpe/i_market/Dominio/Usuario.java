package com.inovaufrpe.i_market.Dominio;

public class Usuario {
  private String uid;
  private String email;
  private String senha;

  public Usuario(){}

  public Usuario(String uid, String email, String senha, String nick){
    this.uid = uid;
    this.email = email;
    this.senha = senha;

  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

}
