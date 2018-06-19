
package com.inovaufrpe.i_market.GUI;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.inovaufrpe.i_market.Dominio.Carrinho;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.R;
import com.inovaufrpe.i_market.Utilidades.Sessao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CarrinhoActivity extends AppCompatActivity {
    private TextView textTotal;
    private ListView listView;
    private String precoTotal;
    private Sessao sessao = Sessao.getInstancia();
    private ArrayList<Produto> produtos = new ArrayList<>();
    private List<HashMap<String, String>> arrayProdutos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        listView = findViewById(R.id.listViewCarrinho);
        textTotal = findViewById(R.id.textViewPrecoTotal);

        setListViewProdutos();


    }

    public void setListViewProdutos() {

        ArrayList todosProdutos = new ArrayList<>();
        todosProdutos = sessao.getProdutos();
        Carrinho carrinho = sessao.getCarrinho();

        if (sessao.getCarrinho() != null) {
            for(int i = 0; i < todosProdutos.size(); i++){
                HashMap<String,String> dicProdutos = new HashMap<>();
                Produto produto = (Produto) todosProdutos.get(i);
                String uid = produto.getUid();
                if (carrinho.getListaProdutos().containsKey(uid)){
                    Integer qtd = carrinho.getListaProdutos().get(uid);
                    Double price = produto.getPreco();
                    precoTotal = getPrecoTotal(sessao.getTotalPagar(), price, qtd);
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

    public String getPrecoTotal(Double total, Double preco, Integer qtd){
        Double dPrecoTotal = total + (preco * qtd);
        sessao.setTotalPagar(dPrecoTotal);
        String sPrecoTotal = Double.toString(dPrecoTotal);
        int indexPonto = sPrecoTotal.indexOf(".");
        if (sPrecoTotal.substring(indexPonto, sPrecoTotal.length()).length()==2){
            sPrecoTotal = "R$ " + sPrecoTotal + "0";
        }
        else{
            sPrecoTotal = "R$ " + sPrecoTotal;
        }

        return sPrecoTotal;
    }



}