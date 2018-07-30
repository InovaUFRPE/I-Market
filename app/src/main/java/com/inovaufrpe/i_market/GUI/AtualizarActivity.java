package com.inovaufrpe.i_market.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.inovaufrpe.i_market.API.Api;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.R;
import com.inovaufrpe.i_market.Utilidades.Sessao;
import com.inovaufrpe.i_market.Negocio.ValidaCadastro;

public class AtualizarActivity extends AppCompatActivity {
    private Sessao sessao = Sessao.getInstancia();
    private Api api = new Api();
    private EditText at_nome;
    private EditText at_marca;
    private EditText at_preco;
    private Spinner at_categoria;
    private String[] listaCategorias = {"Bebidas","Frios","Laticínios","Massas","Mercearia"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);

        at_nome = findViewById(R.id.editTextNomeAtualizar);
        at_marca = findViewById(R.id.editTextMarcaAtualizar);
        at_preco = findViewById(R.id.editTextPrecoAtualizar);
        at_categoria = findViewById(R.id.spinnerCategoriaAt);

        Produto produtoAtualizar = sessao.getProdutoAtualizar();

        at_nome.setText(produtoAtualizar.getNome());
        at_marca.setText(produtoAtualizar.getMarca());

        String preco = Double.toString(produtoAtualizar.getPreco());
        int indexPonto = preco.indexOf(".");

        if (preco.substring(indexPonto, preco.length()).length() > 3){
            preco = preco.substring(0,indexPonto+3);
        }
        else if (preco.substring(indexPonto, preco.length()).length()==2){
            preco = preco + "0";
        }
        at_preco.setText(preco);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        at_categoria.setAdapter(adapter);
        int categoriaAtualIndex = adapter.getPosition(produtoAtualizar.getCategoria());
        at_categoria.setSelection(categoriaAtualIndex);

        at_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void clickAtualizar(View view){
        String nome = at_nome.getText().toString();
        String marca = at_marca.getText().toString();
        String preco = at_preco.getText().toString();
        String categoria = at_categoria.getSelectedItem().toString();
        
        ValidaCadastro validacao = new ValidaCadastro();
        boolean vazio = false;
        
        if (validacao.isCampoVazio(nome)){
            at_nome.requestFocus();
            at_nome.setError("Campo Nome está vazio.");
            vazio = true;
        }
        
        if (validacao.isCampoVazio(marca)){
            at_marca.requestFocus();
            at_marca.setError("Campo marca está vazio.");
            vazio = true;
        }
        
        if (validacao.isCampoVazio(preco)){
            at_preco.requestFocus();
            at_preco.setError("Campo preço está vazio.");
            vazio = true;
        }

        
        if(!vazio){
            api.updateProduto(sessao.getProdutoAtualizar(), nome, preco, categoria, marca);
            this.cancelarAtualizar(view);
            
        }else{
            Toast.makeText(getApplicationContext(), "Existem campos invalidos.",
                            Toast.LENGTH_LONG).show();

        }

        
    }

    public void cancelarAtualizar(View view){
        Intent intent = new Intent(AtualizarActivity.this, GerenciarProdutosActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AtualizarActivity.this, GerenciarProdutosActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
