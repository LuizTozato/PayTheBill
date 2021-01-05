package com.ugps.paythebill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SegundaActivity extends AppCompatActivity {

    private TextView servidor1;
    private TextView servidor2;

    private EditText editValor;
    private EditText editDescrição;

    private ListView listGastos;
    private ArrayList<String> itens = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        servidor1 = findViewById(R.id.textServidor1);
        servidor2 = findViewById(R.id.textServidor2);

        //recuperando os dados vindo da Intent
        Bundle dados = getIntent().getExtras();
        String nome1 = dados.getString("nome1");
        String nome2 = dados.getString("nome2");

        servidor1.setText(nome1);
        servidor2.setText(nome2);

        //================ COLOCANDO VALOR NA LISTA ====================

        listGastos = findViewById(R.id.listGastos);
        Button buttonAdicionar = findViewById(R.id.buttonAdicionar);
        editValor = findViewById(R.id.editValor);
        editDescrição = findViewById(R.id.editDescrição);

        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editDescrição.getText().toString() + " - " + editValor.getText().toString();
                itens.add(str);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, itens);
                listGastos.setAdapter(arrayAdapter);

            }
        });

    }
}
