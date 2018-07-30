package com.inovaufrpe.i_market.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.inovaufrpe.i_market.Dominio.Compra;
import com.inovaufrpe.i_market.Dominio.Usuario;
import com.inovaufrpe.i_market.R;
import com.inovaufrpe.i_market.Utilidades.Sessao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReceitaActivity extends AppCompatActivity {
    private TextView textPreco;
    private String preco = "R$ 0.00";
    private Sessao sessao = Sessao.getInstancia();
    private ArrayList<Compra> compras = new ArrayList<>();
    private List<HashMap<String, String>> arrayCompras = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        textPreco = findViewById(R.id.textViewPrecoRec);
        listView = findViewById(R.id.listViewComprasRec);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("Compra").orderByChild("date");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double dpreco = 0.0;
                String precoCompra;
                int indexPonto;
                ArrayList<Usuario> usuarios = sessao.getUsuarios();

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot dataSnapshotChild : iterable) {
                    Compra compra = dataSnapshotChild.getValue(Compra.class);

                    HashMap<String, String> dicCompra = new HashMap<>();
                    dpreco += compra.getPrecoTotal();

                    precoCompra = Double.toString(compra.getPrecoTotal());
                    indexPonto = precoCompra.indexOf(".");
                    if (precoCompra.substring(indexPonto, precoCompra.length()).length() > 3){
                        precoCompra = "R$ " + precoCompra.substring(0,indexPonto+3);
                    }
                    else if (precoCompra.substring(indexPonto, precoCompra.length()).length() == 2) {
                        precoCompra = "R$ " + precoCompra + "0";
                    } else {
                        precoCompra = "R$ " + precoCompra;
                    }


                    dicCompra.put("Data", "Data da Compra: " + compra.getDate());
                    dicCompra.put("Preço", "Valor: " + precoCompra);
                    arrayCompras.add(dicCompra);
                    compras.add(compra);

                }
                sessao.setCompras(compras);

                preco = Double.toString(dpreco);

                indexPonto = preco.indexOf(".");
                if (preco.substring(indexPonto, preco.length()).length() > 3){
                    preco = "R$ " + preco.substring(0,indexPonto+3);
                }
                else if (preco.substring(indexPonto, preco.length()).length() == 2) {
                    preco = "R$ " + preco + "0";
                } else {
                    preco = "R$ " + preco;
                }
                textPreco.setText(preco);
                setListViewCompras();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setListViewCompras(){
        if(!arrayCompras.isEmpty()){
            SimpleAdapter adapter = new SimpleAdapter(this, arrayCompras, R.layout.item_lista,
                    new String[]{"Data", "Preço"},
                    new int[]{R.id.textViewNome,
                            R.id.textViewPreco});
            listView.setAdapter(adapter);
        }else{
            listView.setAdapter(null);
            Toast.makeText(getApplicationContext(), "Nenhuma compra encontrada.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void backToGerProd2(View view){
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
