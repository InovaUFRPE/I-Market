package com.inovaufrpe.i_market.Negocio;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inovaufrpe.i_market.Dominio.Cliente;

import java.util.UUID;

public class ServicosCliente {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public ServicosCliente(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void cadastrarCliente(String id_usuario, String nome, String endereco, long cpf, int num_telefone,  int num_cartao){
        Cliente cliente = new Cliente();

        cliente.setId(UUID.randomUUID().toString());
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
