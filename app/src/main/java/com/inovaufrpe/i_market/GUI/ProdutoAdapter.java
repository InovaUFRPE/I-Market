package com.inovaufrpe.i_market.GUI;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.R;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

class ProdutoAdapter extends BaseAdapter{
    Activity context;
    ArrayList<Produto> produtos;
    private static LayoutInflater layoutInflater = null;

    public ProdutoAdapter(Activity context, ArrayList<Produto> produtos){
        this.context = context;
        this.produtos = produtos;
        layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View itemView = convertView;
        if (itemView == null) {
                    itemView = LayoutInflater.from(context).
                    inflate(R.layout.item_lista, parent, false);
        }
        TextView textViewNome = itemView.findViewById(R.id.textViewNome);
        TextView textViewPreco = itemView.findViewById(R.id.textViewPreco);
        Produto selectedProduto = produtos.get(position);
        String nome = selectedProduto.getNome() + " " + selectedProduto.getMarca();
        textViewNome.setText(nome);
        String preco = Double.toString(selectedProduto.getPreco());
        textViewPreco.setText(preco);
        return itemView;
    }



}
