package com.inovaufrpe.i_market.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inovaufrpe.i_market.Dominio.Produto;
import com.inovaufrpe.i_market.Dominio.Usuario;
import com.inovaufrpe.i_market.Negocio.ServicosUsuario;
import com.inovaufrpe.i_market.R;
import com.inovaufrpe.i_market.Utilidades.Sessao;
import com.inovaufrpe.i_market.Utilidades.Validacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtSenha;
    private Sessao sessao;
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtEmail = findViewById(R.id.editTextEmail);
        edtSenha = findViewById(R.id.editTextSenha);
        sessao = Sessao.getInstancia();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot dataSnapshotChild : iterable) {

                    Usuario Usuario = dataSnapshotChild.getValue(Usuario.class);
                    usuarios.add(Usuario);
                }
                sessao.setUsuarios(usuarios);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }

    public void entrar(View view){
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
            boolean valido = false;
            if (sessao.getUsuarios() != null){
                ArrayList todosUsuarios = sessao.getUsuarios();

                for(Iterator<Usuario> i = todosUsuarios.iterator(); i.hasNext();){
                    Usuario usuario = i.next();
                    if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)){
                        valido = true;
                        sessao.setUsuario(usuario);
                        break;
                    }
                }
                if (valido){
                    Intent intent = new Intent(this, ListaProdutosActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Email ou senha incorretos!",
                            Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Email ou senha incorretos!", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void cadastreSe(View view){
        Intent intent = new Intent(this, CadClienteActivity.class);
        startActivity(intent);
        finish();
    }

}
