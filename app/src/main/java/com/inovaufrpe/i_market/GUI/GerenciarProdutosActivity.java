package com.inovaufrpe.i_market.GUI;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inovaufrpe.i_market.Dominio.Compra;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.R;
import com.inovaufrpe.i_market.Utilidades.Sessao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GerenciarProdutosActivity extends AppCompatActivity {
    private ListView listView;
    private Sessao sessao = Sessao.getInstancia();
    private ArrayList<Produto> produtos = new ArrayList<>();
    private ProdutoAdapter produtoAdapter;
    private DatabaseReference databaseReference;
    private Produto produto;
    private List<HashMap<String, String>> arrayProdutos = new ArrayList<>();
    private String preco = "R$ 0.00";
    private TextView txtPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_produtos);

        txtPreco = findViewById(R.id.textViewPrecoRec);
        listView = findViewById(R.id.listViewProdutos2);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Produto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produtos = new ArrayList<>();
                produto = new Produto();
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot dataSnapshotChild: iterable){
                    produto = dataSnapshotChild.getValue(Produto.class);
                    HashMap<String,String> dicProdutos = new HashMap<>();

                    Produto produto = dataSnapshotChild.getValue(Produto.class);

                    String preco = Double.toString(produto.getPreco());
                    int indexPonto = preco.indexOf(".");
                    if (preco.substring(indexPonto, preco.length()).length()==2){
                        preco = "R$ " + preco + "0";
                    }
                    else{
                        preco = "R$ " + preco;
                    }

                    dicProdutos.put("Nome", getNomeMarcaProduto(produto));
                    dicProdutos.put("Preço", preco);
                    arrayProdutos.add(dicProdutos);
                    produtos.add(produto);
                }
                sessao.setProdutos(produtos);
                setListViewProdutos();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void setListViewProdutos() {
        if (!arrayProdutos.isEmpty()) {
            SimpleAdapter adapter = new SimpleAdapter(this, arrayProdutos, R.layout.item_lista,
                    new String[]{"Nome", "Preço"},
                    new int[]{R.id.textViewNome,
                            R.id.textViewPreco});
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    // When clicked, show a toast with the TextView text
                    final Dialog d = new Dialog(GerenciarProdutosActivity.this);
                    d.setContentView(R.layout.dialog_ger_produtos);

                    Button btnCancelar = d.findViewById(R.id.buttonCancelar);
                    Button btnExcluir =  d.findViewById(R.id.buttonExcluir);
                    Button btnAtualizar = d.findViewById(R.id.buttonAtualizar);

                    btnAtualizar.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            Produto produto = produtos.get(position);
                            sessao.setProdutoAtualizar(produto);
                            Intent intent = new Intent(GerenciarProdutosActivity.this, AtualizarActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    btnExcluir.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    btnCancelar.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            d.dismiss(); // dismiss the dialog
                        }
                    });
                    d.show();
                }
            });
            //} else {
            //    Auxiliar.criarToast(getApplicationContext(), getString(R.string.sp_excecao_sem_materiais));
            //}*/
        }
    }

    public String getNomeMarcaProduto(Produto produto){
        return produto.getNome() + " - " + produto.getMarca();
    }

    public void lerCompras(){
        databaseReference.child("Compra").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double dpreco = 0.0;

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot dataSnapshotChild: iterable){
                    Compra compra = dataSnapshotChild.getValue(Compra.class);

                    //Compra compra = dataSnapshotChild.getValue(Compra.class);

                    dpreco += compra.getPrecoTotal();

                }
                preco = Double.toString(dpreco);

                int indexPonto = preco.indexOf(".");
                if (preco.substring(indexPonto, preco.length()).length()==2){
                    preco = "R$ " + preco + "0";
                }
                else{
                    preco = "R$ " + preco;
                }
                txtPreco.setText(preco);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void verReceita(View view){
        Intent intent = new Intent(this, ReceitaActivity.class);
        startActivity(intent);
        finish();
    }


    public void backToLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToCadastroProd(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
