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
    private List<HashMap<String, String>> arrayProdutosPesquisa = new ArrayList<>();
    private ArrayList<Produto> produtosPesquisa = new ArrayList<>();


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
                    dicProdutos.put("Pre?o", preco);
                    arrayProdutos.add(dicProdutos);
                    produtos.add(produto);
                }
                sessao.setProdutos(produtos);
                setListViewProdutos(arrayProdutos, produtos);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void setListViewProdutos(List<HashMap<String, String>> arrayProdutos, final ArrayList<Produto> listaProdutos) {
        if (!arrayProdutos.isEmpty()) {
            SimpleAdapter adapter = new SimpleAdapter(this, arrayProdutos, R.layout.item_lista,
                    new String[]{"Nome", "Pre?o"},
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
                            Produto produto = listaProdutos.get(position);
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
        }else{
            listView.setAdapter(null);
            Toast.makeText(getApplicationContext(), "Nenhum produto encontrado.",
                    Toast.LENGTH_LONG).show();
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
        produtosPesquisa.clear();
        arrayProdutosPesquisa.clear();
        String texto = edtPesquisa.getText().toString();

        ArrayList todosProdutos = sessao.getProdutos();

        for(Iterator<Produto> i = todosProdutos.iterator(); i.hasNext();){
            Produto produto = i.next();
            String nomeProduto = getNomeMarcaProduto(produto);
            if (nomeProduto.toLowerCase().contains(texto)){
                HashMap<String,String> dicProdutos = new HashMap<>();

                String preco = Double.toString(produto.getPreco());
                int indexPonto = preco.indexOf(".");
                if (preco.substring(indexPonto, preco.length()).length()==2){
                    preco = "R$ " + preco + "0";
                }
                else{
                    preco = "R$ " + preco;
                }
                dicProdutos.put("Nome", nomeProduto);
                dicProdutos.put("Pre?o", preco);
                arrayProdutosPesquisa.add(dicProdutos);
                produtosPesquisa.add(produto);
            }
        }
        setListViewProdutos(arrayProdutosPesquisa, produtosPesquisa);
    }

    public void voltarListaCompleta(View view){
        edtPesquisa.setText("");
        produtosPesquisa.clear();
        setListViewProdutos(arrayProdutos, produtos);
    }

    public void goToHistorico(View view){
        Intent intent = new Intent(this, HistoricoComprasActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToLogin(View view){
        sessao.getCarrinho().getListaProdutos().clear();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        sessao.getCarrinho().getListaProdutos().clear();
        Intent intent = new Intent(ListaProdutosActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

}
