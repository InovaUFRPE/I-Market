package com.inovaufrpe.i_market.API;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.GUI.ListaProdutosActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Api {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ArrayList<Produto> listaProdutos;

    public Api() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void lerCSV(BufferedReader bufferedReader) {
        BufferedReader csvConteudo = bufferedReader;
        String linha;
        String sep = ",";
        int cont = 0;

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
                    insertProduto(produto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertProduto(Produto produto){
        databaseReference.child("Produto").child(produto.getUid()).setValue(produto);
    }
    
    private void updateProduto(Produto produto, String nome, String preco, String categoria, String marca){
        Produto produtoAt = new Produto();
        produtoAt.setUid(produto.getUid());
        
        produtoAt.setNome(nome);
        produtoAt.setPreco(Double.parseDouble(preco));
        produtoAt.setCategoria(categoria);
        produtoAt.setMarca(marca);
        
        insertProduto(produtoAt);
    }

    public ArrayList<Produto> getAllProdutos(){
        listaProdutos = new ArrayList<Produto>();
        Query query = databaseReference.child("Produto").child("uid");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaProdutos.clear();
                for (DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Produto produto = objSnapshot.getValue(Produto.class);
                    listaProdutos.add(produto);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return listaProdutos;


    }


}
