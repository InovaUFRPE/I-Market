package com.inovaufrpe.i_market.GUI;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.inovaufrpe.i_market.API.Api;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.R;
import com.inovaufrpe.i_market.Utilidades.Sessao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ListaProdutosActivity extends AppCompatActivity {
    private ListView listView;
    private Sessao sessao = Sessao.getInstancia();
    private ArrayList<Produto> produtos = new ArrayList<>();
    private ProdutoAdapter produtoAdapter;
    private Produto produto;
    private EditText edtPesquisa;
    private List<HashMap<String, String>> arrayProdutos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        listView = findViewById(R.id.listViewProdutos);
        edtPesquisa = findViewById(R.id.editTextPesquisa);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

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
                setListViewProdutos(arrayProdutos);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void setListViewProdutos(List<HashMap<String, String>> arrayProdutos) {
        if (!arrayProdutos.isEmpty()) {
            SimpleAdapter adapter = new SimpleAdapter(this, arrayProdutos, R.layout.item_lista,
                    new String[]{"Nome", "Preço"},
                    new int[]{R.id.textViewNome,
                            R.id.textViewPreco});
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    // When clicked, show a toast with the TextView text
                    final Dialog d = new Dialog(ListaProdutosActivity.this);
                    d.setTitle("Escolha a quantidade");
                    d.setContentView(R.layout.dialog_quant_produtos);
                    Button btnCancelar = d.findViewById(R.id.button2);
                    Button btnAdicionar =  d.findViewById(R.id.button1);
                    final NumberPicker np = d.findViewById(R.id.numberPicker1);
                    np.setMaxValue(100); // max value 100
                    np.setMinValue(1);   // min value 0
                    np.setWrapSelectorWheel(false);
                    np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                        }
                    });
                    btnAdicionar.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            int quantidade = np.getValue();
                            Produto produto = produtos.get(position);
                            sessao.getCarrinho().addNovoProduto(produto, quantidade);
                            d.dismiss();

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
            //}
        }
    }

    public String getNomeMarcaProduto(Produto produto){
        return produto.getNome() + " - " + produto.getMarca();
    }

    public void goToCarrinho(View view){
        Intent intent = new Intent(this, CarrinhoActivity.class);
        startActivity(intent);
        finish();
    }

    public void pesquisarByNome(View view){
        String texto = edtPesquisa.getText().toString();

        List<HashMap<String, String>> arrayDicProdutos = new ArrayList<>();
        HashMap<String,String> dicProdutos = new HashMap<>();
        ArrayList todosProdutos = sessao.getProdutos();

        for(Iterator<Produto> i = todosProdutos.iterator(); i.hasNext();){
            String nomeProduto = i.next().getNome();
            if (nomeProduto.toLowerCase().contains(texto)){

                String preco = Double.toString(i.next().getPreco());
                int indexPonto = preco.indexOf(".");
                if (preco.substring(indexPonto, preco.length()).length()==2){
                    preco = "R$ " + preco + "0";
                }
                else{
                    preco = "R$ " + preco;
                }
                dicProdutos.put("Nome", getNomeMarcaProduto(i.next()));
                dicProdutos.put("Preço", preco);
                arrayDicProdutos.add(dicProdutos);
                setListViewProdutos(arrayDicProdutos);
            }
        }
    }

    public void voltarListaCompleta(View view){
        setListViewProdutos(arrayProdutos);
    }

}
