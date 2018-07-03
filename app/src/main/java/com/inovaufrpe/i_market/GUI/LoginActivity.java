package com.inovaufrpe.i_market.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.inovaufrpe.i_market.Dominio.Usuario;
import com.inovaufrpe.i_market.Negocio.ServicosUsuario;
import com.inovaufrpe.i_market.R;
import com.inovaufrpe.i_market.Utilidades.Sessao;
import com.inovaufrpe.i_market.Utilidades.Validacao;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void entrar(View view){
        edtEmail = findViewById(R.id.editTextEmail);
        edtSenha = findViewById(R.id.editTextSenha);
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        Validacao validacao = new Validacao(getApplicationContext());

        boolean vazio = false;
        if (validacao.isCampoVazio(email)){
            edtEmail.requestFocus();
            edtEmail.setError("O campo email está vazio.");
            vazio = true;
        }
        if (validacao.isCampoVazio(senha)){
            edtSenha.requestFocus();
            edtSenha.setError("O campo senha está vazio.");
            vazio = true;
        }
        if (!vazio){
            ServicosUsuario servicosUsuario = new ServicosUsuario();
            servicosUsuario.searchUsuarioByEmail(email, senha);
            Sessao sessao = Sessao.getInstancia();
            boolean valido = false;
            if (sessao.getUsuario() != null){
                Usuario usuario = sessao.getUsuario();
                valido = (usuario.getSenha().equals(senha));
            }
            if(valido){
                Intent intent = new Intent(this, ListaProdutosActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                sessao.setUsuario(null);
                Toast.makeText(getApplicationContext(), "Email ou senha incorretos!",
                        Toast.LENGTH_LONG).show();
            }
        }


    }

    public void cadastreSe(View view){
        Intent intent = new Intent(this, CadClienteActivity.class);
        startActivity(intent);
        finish();
    }

}
