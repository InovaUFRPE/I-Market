package com.inovaufrpe.i_market.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.inovaufrpe.i_market.Dominio.Compra;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.R;
import com.inovaufrpe.i_market.Utilidades.Sessao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProdutosCompraActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_compra);
        listView = findViewById(R.id.listViewProdutosCompra);
        setListView();
    }

    public void backToHistCompra(View view){
        Intent intent = new Intent(this, HistoricoComprasActivity.class);
        startActivity(intent);
        finish();
    }

    public void setListView(){
        Sessao sessao = Sessao.getInstancia();
        Compra compra = sessao.getCompra_atual();
        ArrayList<Produto> listaProdutos = sessao.getProdutos();

        List<HashMap<String,String>> prodCompra = new ArrayList<>();

        String[] produtosCompra = compra.getProdutos().split(",");

        for (String produto: produtosCompra){
            int meio = produto.indexOf(":");

            String uid = produto.substring(0, meio);
            String quantidade = produto.substring(meio+1);

            String nome;
            String preco;

            for(Produto produto1 : listaProdutos){
                if(produto1.getUid().equals(uid)){
                    nome = produto1.getNome();
                    preco = Double.toString(produto1.getPreco());
                    HashMap<String,String> dicProdutos = new HashMap<>();
                    int indexPonto = preco.indexOf(".");
                    if (preco.substring(indexPonto, preco.length()).length()==2){
                        preco = "R$ " + preco + "0";
                    }
                    else{
                        preco = "R$ " + preco;
                    }
                    dicProdutos.put("Nome", nome);
                    dicProdutos.put("Preço", preco);
                    dicProdutos.put("Qtd", quantidade);

                    prodCompra.add(dicProdutos);
                }
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(this, prodCompra, R.layout.item_lista2,
                new String[]{"Nome", "Preço", "Qtd"},
                new int[]{R.id.textViewNome,
                        R.id.textViewPreco,
                        R.id.textViewQtd});
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HistoricoComprasActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
