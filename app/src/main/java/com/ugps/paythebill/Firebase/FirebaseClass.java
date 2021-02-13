package com.ugps.paythebill.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseClass {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference itens = root.child("itens");
    private FirebaseAuth usuario = FirebaseAuth.getInstance();

    //CONSTRUCTOR ==========================
    public FirebaseClass() {
    }

    //GETTERS AND SETTERS ==================
    public DatabaseReference getRoot() {
        return root;
    }

    public DatabaseReference getItens() {
        return itens;
    }

    public FirebaseAuth getUsuario() {
        return usuario;
    }

    //METODOS ==============================
    public void addFireValue(String valor){
        this.root.child("nome").setValue(valor.toString());
    }

}
