package com.inovaufrpe.i_market.GUI;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.inovaufrpe.i_market.API.Api;
import com.inovaufrpe.i_market.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadCsv(View view){
        InputStream inputStream = getResources().openRawResource(R.raw.cadastroprodutos);
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream, Charset.forName("ISO-8859-1")));
        Api api = new Api();
        api.lerCSV(bufferedReader);

    }

    public void backGerProd(View view){
        Intent intent = new Intent(this, GerenciarProdutosActivity.class);
        startActivity(intent);
        finish();
    }

}
