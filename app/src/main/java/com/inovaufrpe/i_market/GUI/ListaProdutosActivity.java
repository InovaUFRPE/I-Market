package com.inovaufrpe.i_market.GUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.inovaufrpe.i_market.API.Api;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaProdutosActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Produto> produtos = new ArrayList<>();
    private ProdutoAdapter produtoAdapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private Produto produto;
    private List<HashMap<String, String>> arrayProdutos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        listView = findViewById(R.id.listViewProdutos);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Produto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produtos = new ArrayList<>();
                produto = new Produto();
                produtos.clear();
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot dataSnapshotChild: iterable){
                    produto = dataSnapshotChild.getValue(Produto.class);
                    HashMap<String,String> dicProdutos = new HashMap<>();

                    Produto produto = dataSnapshotChild.getValue(Produto.class);

                    dicProdutos.put("Nome", produto.getNome());
                    dicProdutos.put("Preço",Double.toString(produto.getPreco()));
                    arrayProdutos.add(dicProdutos);
                }
                setListViewProdutos();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setAdapter(){
        ArrayAdapter arrayAdapter = new ArrayAdapter<Produto>(ListaProdutosActivity.this, R.layout.item_lista);
        listView.setAdapter(arrayAdapter);
    }

    private void listarProdutos(){
        Api api = new Api();
        produtos = api.getAllProdutos();
        listView.setAdapter(produtoAdapter);
    }

    public void setListViewProdutos() {
        if (!arrayProdutos.isEmpty()) {
            SimpleAdapter adapter = new SimpleAdapter(this, arrayProdutos, R.layout.item_lista,
                    new String[]{"Nome", "Preco"},
                    new int[]{R.id.textViewNome,
                            R.id.textViewPreco});
            listView.setAdapter(adapter);
            //} else {
            //    Auxiliar.criarToast(getApplicationContext(), getString(R.string.sp_excecao_sem_materiais));
            //}
        }
    }




}
