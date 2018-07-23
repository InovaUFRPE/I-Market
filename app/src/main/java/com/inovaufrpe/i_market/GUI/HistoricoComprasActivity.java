package com.inovaufrpe.i_market.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.inovaufrpe.i_market.Dominio.Compra;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.R;
import com.inovaufrpe.i_market.Utilidades.Sessao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoricoComprasActivity extends AppCompatActivity {
    private Sessao sessao = Sessao.getInstancia();
    private ArrayList<Compra> compras = new ArrayList<>();
    private List<HashMap<String, String>> arrayCompras = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_compras);
        listView = findViewById(R.id.listViewCompras);

        String uidCliente = sessao.getUsuario().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("Compra").orderByChild("uid_cliente").equalTo(uidCliente);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot dataSnapshotChild: iterable) {
                    Compra compra = dataSnapshotChild.getValue(Compra.class);
                    HashMap<String, String> dicCompra = new HashMap<>();

                    String preco = Double.toString(compra.getPrecoTotal());
                    int indexPonto = preco.indexOf(".");
                    if (preco.substring(indexPonto, preco.length()).length() > 3){
                        preco = "R$ " + preco.substring(0,indexPonto+3);
                    }
                    else if (preco.substring(indexPonto, preco.length()).length()==2){
                        preco = "R$ " + preco + "0";
                    }
                    else{
                        preco = "R$ " + preco;
                    }

                    String data = dataFormatada(compra.getDate());

                    dicCompra.put("Data", data);
                    dicCompra.put("Preço", preco);
                    arrayCompras.add(dicCompra);
                    compras.add(compra);
                }
                sessao.setCompras(compras);
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
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Compra compra_atual = compras.get(position);
                    sessao.setCompra_atual(compra_atual);
                    Intent intent = new Intent(HistoricoComprasActivity.this, ProdutosCompraActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else{
            listView.setAdapter(null);
            Toast.makeText(getApplicationContext(), "Nenhuma compra encontrada.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void backToListagem2(View view){
        Intent intent = new Intent(this, ListaProdutosActivity.class);
        startActivity(intent);
        finish();
    }

    private String  dataFormatada(String data){
        String data2 = data.substring(0, 10);
        String data3 = data2.substring(8) + "/" + data2.substring(5,7) + "/" +
                data2.substring(0,4);
        String horario = data.substring(11);
        String newData  = "Data da Compra: " + data3 + " " + horario;
        return newData;
    }
}
