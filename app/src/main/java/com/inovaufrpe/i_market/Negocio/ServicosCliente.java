package com.inovaufrpe.i_market.Negocio;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inovaufrpe.i_market.Dominio.Cliente;
import com.inovaufrpe.i_market.Dominio.Usuario;

import java.util.UUID;

public class ServicosCliente {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public ServicosCliente(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void cadastrarCliente(String email, String senha, String nome, String endereco, String cpf, String num_telefone,  String num_cartao){
        ServicosUsuario servicosUsuario = new ServicosUsuario();
        Usuario usuario = new Usuario();
        usuario.setUid(UUID.randomUUID().toString());
        servicosUsuario.cadastrarUsuario(usuario,email, senha);

        Cliente cliente = new Cliente();

        cliente.setId(usuario.getUid());
        cliente.setUid_usuario(usuario.getUid());
        cliente.setNome(nome);
        cliente.setEndereco(endereco);
        cliente.setCpf(cpf);
        cliente.setNum_telefone(num_telefone);
        cliente.setNum_cartao(num_cartao);
        
        this.insertCliente(cliente);
    }

    private void insertCliente(Cliente cliente){
        databaseReference.child("Cliente").child(cliente.getId()).setValue(cliente);
    }
}
