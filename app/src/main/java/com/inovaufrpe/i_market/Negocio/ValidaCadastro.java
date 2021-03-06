package com.inovaufrpe.i_market.Negocio;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Classe responsável pelos serviços de validações de campo e datas
 */

@SuppressWarnings({"FieldCanBeLocal", "BooleanMethodIsAlwaysInverted"})
public class ValidaCadastro {
  private final int TAMANHO_SENHA = 6;
  private final int TAMANHO_CPF = 11;

  public boolean isCampoVazio(String texto){
    return (texto.trim().isEmpty() || TextUtils.isEmpty(texto));
  }

  public boolean isEmail(String texto){
    return (Patterns.EMAIL_ADDRESS.matcher(texto).matches());
  }

  public boolean isSenhaValida(String texto) {
    return !isCampoVazio(texto) && texto.length() >= TAMANHO_SENHA;
  }

  public boolean isCpfValida(String texto) {
    return !isCampoVazio(texto) && texto.length() == TAMANHO_CPF;
  }

  public boolean isTelefone(String texto){
    return texto.length() == 11;
  }

  public boolean isCartao(String texto){
    return texto.length() == 16;
  }

}
