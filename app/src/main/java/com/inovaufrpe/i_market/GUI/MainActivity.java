package com.inovaufrpe.i_market.GUI;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.inovaufrpe.i_market.API.Api;
import com.inovaufrpe.i_market.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import com.inovaufrpe.i_market.Negocio.ValidaCadastro;
import com.inovaufrpe.i_market.API.Api;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadCsv(View view){
        InputStream inputStream = getResources().openRawResource(R.raw.cadastroprodutos);
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream, Charset.forName("ISO-8859-1")));
        Api api = new Api();
        if(api.lerCSV(bufferedReader)){
            Toast.makeText(getApplicationContext(), "Produtos cadastrados com sucesso.",
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Alguns produtos já se encontram cadastrados.",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void cadastraProd(View view){
        Api api = new Api();
        
        
        ValidaCadastro validacao = new ValidaCadastro();
        boolean vazio = false;
        
        /*if (validacao.isCampoVazio(nome)){
            edtNome.requestFocus();
            edtNome.setError("Campo Nome está vazio.");
            vazio = true;
        }
        
        if (validacao.isCampoVazio(marca)){
            edtNome.requestFocus();
            edtNome.setError("Campo Nome está vazio.");
            vazio = true;
        }
        
        if (validacao.isCampoVazio(preco)){
            edtNome.requestFocus();
            edtNome.setError("Campo Nome está vazio.");
            vazio = true;
        }
        
        if (validacao.isCampoVazio(categoria)){
            edtNome.requestFocus();
            edtNome.setError("Campo Nome está vazio.");
            vazio = true;
        }
        
        if(!vazio){
            Produto produto = new Produto();
            produto.setUid(UUID.randomUUID().toString());
            api.updateProduto(produto, nome, preco, categoria, marca);
            this.backGerProd(view);
            
        }else{
            Toast.makeText(getApplicationContext(), "Existem campos invalidos.",
                            Toast.LENGTH_LONG).show();

        }*/

    }

    public void backGerProd(View view){
        Intent intent = new Intent(this, GerenciarProdutosActivity.class);
        startActivity(intent);
        finish();
    }

}
