package com.inovaufrpe.i_market.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.inovaufrpe.i_market.Negocio.ServicosCliente;
import com.inovaufrpe.i_market.Negocio.ValidaCadastro;
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

        ValidaCadastro validacao = new ValidaCadastro();
        boolean vazio = false;
        if (validacao.isCampoVazio(email) && !validacao.isEmail(email)){
            edtEmail.requestFocus();
            edtEmail.setError("Email inválido.");
            vazio = true;
        }
        if (validacao.isCampoVazio(senha) && !validacao.isSenhaValida(senha)){
            edtSenha.requestFocus();
            edtSenha.setError("Senha deve conter no mínimo 6 caractéres.");
            vazio = true;
        }
        if (validacao.isCampoVazio(cpf) && !validacao.isCpfValida(cpf)){
            edtCPF.requestFocus();
            edtCPF.setError("CPF inválido.");
            vazio = true;
        }
        if (validacao.isCampoVazio(telefone)){
            edtTelefone.requestFocus();
            edtTelefone.setError("Telefone Inválido");
            vazio = true;
        }
        if (validacao.isCampoVazio(endereco)){
            edtEndereco.requestFocus();
            edtEndereco.setError("Endereço Inválido");
            vazio = true;
        }
        if (validacao.isCampoVazio(nome)){
            edtNome.requestFocus();
            edtNome.setError("Campo Nome está vazio.");
            vazio = true;
        }
        if (validacao.isCampoVazio(ncartao)){
            edtCartao.requestFocus();
            edtCartao.setError("Número Inválido");
            vazio = true;
        }
        if(!vazio){
            ServicosCliente servicoCliente = new ServicosCliente();
            servicoCliente.cadastrarCliente(email, senha, nome, endereco, cpf, telefone, ncartao);
            Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso.",
                    Toast.LENGTH_LONG).show();
            Intent it = new Intent(CadClienteActivity.this, LoginActivity.class);
            startActivity(it);
            finish();
        }
    }

    public void cancelarCadastro(View view){
        finish();
    }
}

