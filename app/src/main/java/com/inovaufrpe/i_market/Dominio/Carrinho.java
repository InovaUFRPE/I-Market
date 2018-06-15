
package com.inovaufrpe.i_market.Dominio;

import java.util.HashMap;
import java.util.Map;

public class Carrinho{
  private Map<String, Integer> listaProdutos = new HashMap<>();

  public Map<String, Integer> getListaProdutos(){
    return this.listaProdutos;
  }

  public Carrinho(){

  }

  public void setNovoProduto(Produto produto, int quantidade){
    /*
    * iremos usar o id aleatorio do produto como chave
    * vamos deixar a quantidade dos produtos como valor por enquanto
    * depois vemos se colocamos o protudo e/ou outros dados tambem
    */
    String id = produto.getUid;
    if (listaProdutos.conteinsKey(id)){
      qtdAntiga = listaProdutos.get(id);
      listaProdutos.put(id, qtdAntiga + quantidade);

    }else{
      this.listaProdutos.put(id, quantidade);
    }
  }

  public void removeProduto(Produto produto, int quantidade){
    /*
    * o usuario pode remover uma quantidadde ou todas as unidades de um produto
    * vamos comparar a quantidade que sera removida com a existente,
    *caso ambas sejam iguais o produto � removido da lista
    *se o usuario escolher remover todas as unidades do produto passamos a quantidadde total de unidades 
    */

    String id = produto.getUid;
    qtdAntiga = listaProdutos.get(id);
    if (qtdAntiga > quantidade){
      listaProdutos.put(id, qtdAntiga - quantidade);

    }else{
      this.listaProdutos.remove(id);
    }
  }
}