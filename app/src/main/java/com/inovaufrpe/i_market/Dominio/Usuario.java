package com.inovaufrpe.i_market.Dominio;

public class Usuario {
  private String uid;
  private String email;
  private String senha;//podemos mudar para int se a senha for apenas numerica
  private String nick;

  public Usuario(){}

  public Usuario(String uid, String email, String senha, String nick){
    this.uid = uid;
    this.email = email;
    this.senha = senha;
    this.nick = nick;
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

  public String getNick() {
    return nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }
}
