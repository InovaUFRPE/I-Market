package com.inovaufrpe.i_market.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.inovaufrpe.i_market.API.Api;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.R;
import com.inovaufrpe.i_market.Utilidades.Sessao;
import com.inovaufrpe.i_market.Negocio.ValidaCadastro;

public class AtualizarActivity extends AppCompatActivity {
    private Sessao sessao = Sessao.getInstancia();
    Api api = new Api();
    private EditText at_nome;
    private EditText at_marca;
    private EditText at_preco;
    private EditText at_categoria;
    private Button atualizar;
    private Button at_cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);

        at_nome = findViewById(R.id.editTextNomeAtualizar);
        at_marca = findViewById(R.id.editTextMarcaAtualizar);
        at_preco = findViewById(R.id.editTextPrecoAtualizar);
        at_categoria = findViewById(R.id.editTextCategoriaAtualizar);

        Produto produtoAtualizar = sessao.getProdutoAtualizar();

        at_nome.setText(produtoAtualizar.getNome());
        at_marca.setText(produtoAtualizar.getMarca());
        at_preco.setText(Double.toString(produtoAtualizar.getPreco()));
        at_categoria.setText(produtoAtualizar.getCategoria());
    }

    public void clickAtualizar(View view){
        String nome = at_nome.getText().toString();
        String marca = at_marca.getText().toString();
        String preco = at_preco.getText().toString();
        String categoria = at_categoria.getText().toString();
        
        ValidaCadastro validacao = new ValidaCadastro();
        boolean vazio = false;
        
        if (validacao.isCampoVazio(nome)){
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
}
