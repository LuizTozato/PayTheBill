package com.ugps.paythebill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private ListView lista;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        //CONECTANDO COM AS ENTIDADES
        lista = findViewById(R.id.lista);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        sharedPreferences = getSharedPreferences("nomes",MODE_PRIVATE);

        //FAB
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Montando pacote de comando
                Intent intent = new Intent(getApplicationContext(), SegundaActivity.class);

                //iniciando outra activity passando origem e destino
                startActivity(intent);

            }
        });

        //RECUPERANDO OS DADOS DA INTENT
        ArrayList<String> listaArray = getIntent().getStringArrayListExtra("Spaceship");
        if(listaArray != null) {

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    listaArray);

            lista.setAdapter(arrayAdapter);
        }
    }
}