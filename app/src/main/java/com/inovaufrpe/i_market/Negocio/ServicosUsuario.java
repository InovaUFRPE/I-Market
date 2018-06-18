package com.inovaufrpe.i_market.Negocio;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.inovaufrpe.i_market.Dominio.Usuario;

import java.util.UUID;

public class ServicosUsuario {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private Usuario usuario;

    public ServicosUsuario(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void cadastrarUsuario(String email, String senha, String nick){
        Usuario usuario = new Usuario();

        usuario.setUid(UUID.randomUUID().toString());
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setNick(nick);

        this.insertUsuario(usuario);
    }

    private void insertUsuario(Usuario usuario) {
        databaseReference.child("Usuario").child(usuario.getUid()).setValue(usuario);
    }

    public void searchUsuarioByUid(String uid){
        Query query = databaseReference.child("Usuario").orderByChild("uid").equalTo(uid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    usuario = dataSnapshot1.getValue(Usuario.class);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //return usuario = new Usuario();
    }
}
