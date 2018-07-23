package com.inovaufrpe.i_market.Dominio;

public class Cliente {
  private String id;
  private String uid_usuario;
  private String nome;
  private String endereco;
  private String cpf;
  private String num_telefone;
  private String num_cartao;

  public Cliente(){}

  public Cliente(String id, String id_usuario, String nome, String endereco, String cpf, String num_telefone, String num_cartao){
    this.id = id;
    this.uid_usuario = id_usuario;
    this.nome = nome;
    this.endereco = endereco;
    this.cpf = cpf;
    this.num_telefone = num_telefone;
    this.num_cartao = num_cartao;
  }

  public String getId(){return id;}

  public void setId(String id){this.id = id;}

  public String geUtid_usuario(){return uid_usuario;}

  public void setUid_usuario(String id_usuario){this.uid_usuario = id_usuario;}
  
  public String getNome() {return nome;}

  public void setNome(String nome) {this.nome = nome;}

  public String getEndereco() {return endereco;}

  public void setEndereco(String endereco) {this.endereco = endereco;}

  public String getCpf() {return cpf;}

  public void setCpf(String cpf) {this.cpf = cpf;}

  public String getNum_telefone() {return num_telefone;}

  public void setNum_telefone(String num_telefone) {this.num_telefone = num_telefone;}

  public String getNum_cartao() {return num_cartao;}

  public void setNum_cartao(String num_cartao) {this.num_cartao = num_cartao;}

}