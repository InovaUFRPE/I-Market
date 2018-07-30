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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.inovaufrpe.i_market.API.Api;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.inovaufrpe.i_market.Negocio.ValidaCadastro;
import com.inovaufrpe.i_market.API.Api;
import com.inovaufrpe.i_market.Utilidades.Sessao;

public class MainActivity extends AppCompatActivity {
    private Sessao sessao = Sessao.getInstancia();
    private Spinner spinner;
    private String[] listaCategorias = {"Bebidas","Frios","Laticínios","Massas","Mercearia"};
    private EditText edtNome, edtMarca,edtPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNome = findViewById(R.id.editTextNomeProd);
        edtMarca = findViewById(R.id.editTextMarca);
        edtPreco = findViewById(R.id.editTextPreco);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner)findViewById(R.id.spinnerCategoria3);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void loadCsv(View view){
        InputStream inputStream = getResources().openRawResource(R.raw.cadastroprodutos);
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream, Charset.forName("ISO-8859-1")));
        Api api = new Api();
        if(api.lerCSV(bufferedReader)){
            Toast.makeText(getApplicationContext(), "Produtos cadastrados com sucesso.",
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Alguns produtos podem não ter sidos registrados por já se encontrarem cadastrados.",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void cadastraProd(View view){
        String nome = edtNome.getText().toString();
        String marca = edtMarca.getText().toString();
        String preco = edtPreco.getText().toString();
        String categoria = spinner.getSelectedItem().toString();

        Api api = new Api();


        ValidaCadastro validacao = new ValidaCadastro();
        boolean vazio = false;

        if (validacao.isCampoVazio(nome)){
            edtNome.requestFocus();
            edtNome.setError("Campo Nome está vazio.");
            vazio = true;
        }

        if (validacao.isCampoVazio(marca)){
            edtMarca.requestFocus();
            edtMarca.setError("Campo marca está vazio.");
            vazio = true;
        }

        if (validacao.isCampoVazio(preco)){
            edtPreco.requestFocus();
            edtPreco.setError("Campo preço está vazio.");
            vazio = true;
        }
        
        if(!vazio){
            Produto produto = new Produto();
            produto.setUid(UUID.randomUUID().toString());
            api.updateProduto(produto, nome, preco, categoria, marca);
            this.backGerProd(view);
            Toast.makeText(getApplicationContext(), "Produto cadastrado com sucesso.",
                    Toast.LENGTH_LONG).show();


        }else{
            Toast.makeText(getApplicationContext(), "Existem campos invalidos.",
                            Toast.LENGTH_LONG).show();

        }

    }

    public void backGerProd(View view){
        Intent intent = new Intent(this, GerenciarProdutosActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GerenciarProdutosActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

}
