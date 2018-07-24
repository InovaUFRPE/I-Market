
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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inovaufrpe.i_market.Dominio.Carrinho;
import com.inovaufrpe.i_market.Dominio.Compra;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.R;
import com.inovaufrpe.i_market.Utilidades.Sessao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CarrinhoActivity extends AppCompatActivity {
    private TextView textTotal;
    private ListView listView;
    private String precoTotal;
    private Sessao sessao = Sessao.getInstancia();
    private ArrayList<Produto> produtos = new ArrayList<>();
    private List<HashMap<String, String>> arrayProdutos = new ArrayList<>();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        listView = findViewById(R.id.listViewCarrinho);
        textTotal = findViewById(R.id.textViewPrecoTotal);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        setListViewProdutos();


    }

    public void setListViewProdutos() {

        ArrayList todosProdutos = new ArrayList<>();
        todosProdutos = sessao.getProdutos();
        Carrinho carrinho = sessao.getCarrinho();

        if (sessao.getCarrinho() != null) {
            Double acumulado = 0.0;
            for(int i = 0; i < todosProdutos.size(); i++){
                HashMap<String,String> dicProdutos = new HashMap<>();
                Produto produto = (Produto) todosProdutos.get(i);
                String uid = produto.getUid();
                if (carrinho.getListaProdutos().containsKey(uid)){
                    Integer qtd = carrinho.getListaProdutos().get(uid);
                    Double price = produto.getPreco();
                    acumulado = calculaPreco(acumulado, price, qtd);
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
                    dicProdutos.put("Qtd", Integer.toString(qtd));
                    arrayProdutos.add(dicProdutos);
                }
            }
            sessao.setTotalPagar(acumulado);
            precoTotal = getPrecoTotalStr(acumulado);


            SimpleAdapter adapter = new SimpleAdapter(this, arrayProdutos, R.layout.item_lista2,
                    new String[]{"Nome", "Preço", "Qtd"},
                    new int[]{R.id.textViewNome,
                            R.id.textViewPreco,
                            R.id.textViewQtd});
            textTotal.setText(precoTotal);
            listView.setAdapter(adapter);
            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    // When clicked, show a toast with the TextView text
                    final Dialog d = new Dialog(CarrinhoActivity.this);
                    d.setTitle("Escolha a quantidade");
                    d.setContentView(R.layout.dialog_quant_produtos);
                    Button btnCancelar = d.findViewById(R.id.button1);
                    Button btnAdicionar =  d.findViewById(R.id.button2);
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
            });*/
            //} else {
            //    Auxiliar.criarToast(getApplicationContext(), getString(R.string.sp_excecao_sem_materiais));
            //}
        }
    }

    public String getNomeMarcaProduto(Produto produto){
        return produto.getNome() + " - " + produto.getMarca();
    }

    public String getPrecoTotalStr(Double total){
        //sessao.setTotalPagar(dPrecoTotal);
        String sPrecoTotal = Double.toString(total);
        int indexPonto = sPrecoTotal.indexOf(".");
        if (sPrecoTotal.substring(indexPonto, sPrecoTotal.length()).length()==2){
            sPrecoTotal = "R$ " + sPrecoTotal + "0";
        }
        else{
            sPrecoTotal = "R$ " + sPrecoTotal;
        }

        return sPrecoTotal;
    }

    private Double calculaPreco(Double acumulado, Double preco, Integer qtd){
        Double dPrecoTotal = acumulado + (preco * qtd);
        sessao.setTotalPagar(dPrecoTotal);
        return dPrecoTotal;
    }

    public void backToListagem(View view){
        Intent intent = new Intent(this, ListaProdutosActivity.class);
        startActivity(intent);
        finish();
    }

    public void pagamento(View view){
        final Dialog dialogPagamento = new Dialog(CarrinhoActivity.this);
        dialogPagamento.setContentView(R.layout.dialog_comfirmcao_pag);
        dialogPagamento.setTitle("Confirmar Pagamento?");
        Button btnConfirmar = dialogPagamento.findViewById(R.id.buttonConfirmar);
        Button btnCancelar2 =  dialogPagamento.findViewById(R.id.buttonCancelar2);
        btnConfirmar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!sessao.getCarrinho().getListaProdutos().isEmpty()){
                    Compra compra = new Compra();
                    String uid = sessao.getUsuario().getUid();
                    String carrinhoStr = "";
                    Map<String,Integer> carrinho = sessao.getCarrinho().getListaProdutos();
                    for (Map.Entry<String,Integer> entry : carrinho.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue().toString();
                        carrinhoStr = carrinhoStr + key + ":" + value + ",";
                    }
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

                    compra.setUid_cliente(uid);
                    compra.setPrecoTotal(sessao.getTotalPagar());
                    compra.setDate(date);
                    compra.setProdutos(carrinhoStr);
                    compra.setUid_compra(UUID.randomUUID().toString());
                    databaseReference.child("Compra").child(compra.getUid_compra()).setValue(compra);

                    Toast.makeText(getApplicationContext(), "Pagamento efetuado com sucesso!",
                            Toast.LENGTH_LONG).show();
                    dialogPagamento.dismiss();
                    sessao.getCarrinho().getListaProdutos().clear();
                    sessao.setTotalPagar(0.0);
                    Intent intent = new Intent(CarrinhoActivity.this, ListaProdutosActivity.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(getApplicationContext(), "Nenhum produto encontrado.",
                            Toast.LENGTH_LONG).show();

                }

            }

        });

        btnCancelar2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialogPagamento.dismiss();
            }
        });
        dialogPagamento.show();


    }



}
