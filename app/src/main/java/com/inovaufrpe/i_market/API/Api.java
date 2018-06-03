package com.inovaufrpe.i_market.API;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inovaufrpe.i_market.Dominio.Produto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class Api {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Api() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void lerCSV(String path) {
        BufferedReader csvContent = null;
        String linha;
        String sep = ",";
        int cont = 0;

        try {
            csvContent = new BufferedReader(new FileReader(path));
            Produto produto = new Produto();
            while((linha = csvContent.readLine()) != null){
                cont ++;
                String[] atributos = linha.split(sep);
                if (cont >=  3){
                    produto.setUid(UUID.randomUUID().toString());
                    produto.setNome(atributos[1]);
                    produto.setPreco(Double.parseDouble(atributos[6].substring(3,7)));
                    produto.setCategoria(atributos[4]);
                    produto.setMarca(atributos[8]);
                    insertProduto(produto);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertProduto(Produto produto){
        databaseReference.child("Produto").child(produto.getUid()).setValue(produto);
    }
    
    private void updateProduto(Produto produto, String nome, String preco, String categoria, String marca){
        Produto produtoAt = new Produto;
        produtoAt.setUid(produto.getUid());
        
        produtoAt.setNome(nome);
        produtoAt.setPreco(Double.parseDouble(preco));
        produtoAt.setCategoria(categoria);
        produtoAt.setMarca(marca);
        
        insertProduto(produtoAt);
    }


}
