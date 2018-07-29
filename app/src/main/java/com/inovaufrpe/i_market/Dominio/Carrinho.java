
package com.inovaufrpe.i_market.Dominio;

import java.util.HashMap;
import java.util.Map;

public class Carrinho{
  private  Produto produto;
  private Map<String, Integer> listaProdutos = new HashMap<>();

  public Map<String, Integer> getListaProdutos(){
    return this.listaProdutos;
  }

  public Carrinho(){

  }

  public void addNovoProduto(Produto produto, int quantidade){
    /*
    * iremos usar o id aleatorio do produto como chave
    * vamos deixar a quantidade dos produtos como valor por enquanto
    * depois vemos se colocamos o protudo e/ou outros dados tambem
    */
    String uid = produto.getUid();
    if (listaProdutos.containsKey(uid)){
      int qtdAntiga = listaProdutos.get(uid);
      listaProdutos.put(uid, qtdAntiga + quantidade);

    }else{
      this.listaProdutos.put(uid, quantidade);
    }
  }

  public void alteraQtdProduto(Produto produto, int quantidade){
    /*
    * o usuario pode remover uma quantidadde ou todas as unidades de um produto
    * vamos comparar a quantidade que sera removida com a existente,
    *caso ambas sejam iguais o produto � removido da lista
    *se o usuario escolher remover todas as unidades do produto passamos a quantidadde total de unidades 
    */
    String uid = produto.getUid();
    int qtdAntiga = listaProdutos.get(uid);
    if (quantidade != 0){
      listaProdutos.put(uid, quantidade);
    }else{
      this.listaProdutos.remove(uid);
    }
  }
}