package com.inovaufrpe.i_market.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.inovaufrpe.i_market.Negocio.ServicosCliente;
import com.inovaufrpe.i_market.R;

public class CadClienteActivity extends AppCompatActivity {
    private EditText edtNick, edtEmail, edtSenha, edtNome, edtTelefone, edtCPF, edtEndereco, edtCartao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_cliente);

        edtSenha = (EditText)findViewById(R.id.edtSenha);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtCPF = (EditText)findViewById(R.id.edtCpf);
        edtTelefone = (EditText)findViewById(R.id.edtTelefone);
        edtEndereco = (EditText)findViewById(R.id.edtEndereco);
        edtNome = (EditText)findViewById(R.id.edtNome);
        edtCartao = (EditText)findViewById(R.id.edtCartao);

    }

    public void cadastrarCliente(View view) {
        String senha = edtSenha.getText().toString();
        String email = edtEmail.getText().toString();

        String cpf = edtCPF.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String endereco = edtEndereco.getText().toString();
        String nome = edtNome.getText().toString();
        String ncartao = edtCartao.getText().toString();



        ServicosCliente servicoCliente = new ServicosCliente();
        servicoCliente.cadastrarCliente(email, senha, nome, endereco, cpf, telefone, ncartao);
        Intent it = new Intent(CadClienteActivity.this, LoginActivity.class);
        startActivity(it);
        finish();
    }

    public void cancelarCadastro(View view){
        finish();
    }
}

