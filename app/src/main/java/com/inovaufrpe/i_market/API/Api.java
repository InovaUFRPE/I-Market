package com.inovaufrpe.i_market.API;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.GUI.ListaProdutosActivity;
import com.inovaufrpe.i_market.Utilidades.Sessao;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Api {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ArrayList<Produto> listaProdutos;
    private Sessao sessao = Sessao.getInstancia();

    public Api() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public boolean lerCSV(BufferedReader bufferedReader) {
        BufferedReader csvConteudo = bufferedReader;
        Boolean tudoOk = true;
        String linha;
        String sep = ",";
        int cont = 0;
        ArrayList<Produto> todosProdutos = sessao.getProdutos();

        try {
            Produto produto = new Produto();
            while((linha = csvConteudo.readLine()) != null){
                cont ++;
                String[] atributos = linha.split(sep);
                if (cont >=  3){
                    produto.setUid(UUID.randomUUID().toString());
                    produto.setNome(atributos[3]);
                    produto.setPreco(Double.parseDouble(atributos[8].substring(3)));
                    produto.setCategoria(atributos[6]);
                    produto.setMarca(atributos[10]);
                    if (!isProdutoCadastrado(produto, todosProdutos)){
                        insertProduto(produto);
                    }
                    else{
                        tudoOk = false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tudoOk;
    }

    private void insertProduto(Produto produto){
        databaseReference.child("Produto").child(produto.getUid()).setValue(produto);
    }
    
    public void updateProduto(Produto produtoAt, String nome, String preco, String categoria, String marca){
        produtoAt.setNome(nome);
        produtoAt.setPreco(Double.parseDouble(preco));
        produtoAt.setCategoria(categoria);
        produtoAt.setMarca(marca);
        
        insertProduto(produtoAt);
    }
    
    public void deletarProduto(Produto produto){
        databaseReference.child("Produto").child(produto.getUid()).removeValue();
    }

    private boolean isProdutoCadastrado(Produto produto, ArrayList<Produto> todosProdutos){
        Boolean isIt = false;
        if (todosProdutos != null){
            for(Produto produto1: todosProdutos){
                if(produto.getNome().equals(produto1.getNome()) && produto.getMarca().equals(produto1.getMarca())){
                    isIt = true;
                }
            }
        }
        return isIt;
    }

}
