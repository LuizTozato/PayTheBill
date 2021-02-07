package com.ugps.paythebill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private ListView lista;
    private ArrayList<String> listaArray = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        //CONECTANDO COM AS ENTIDADES
        lista = findViewById(R.id.lista);
        floatingActionButton = findViewById(R.id.floatingActionButton);

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

        //RECUPERANDO OS DADOS DO SQLite
        try {
            SQLiteDatabase bancoDados = Database.openDB(getApplicationContext());
            Cursor cursor = bancoDados.rawQuery("SELECT nome,valor,data FROM compras", null);

            //INDICES DA TABELA
            int indiceNome = cursor.getColumnIndex("nome");
            System.out.println("Indice nome: " + indiceNome);
            int indiceValor = cursor.getColumnIndex("valor");
            int indiceData = cursor.getColumnIndex("data");

            //posicionar o cursor o inicio da tabela
            while (cursor.moveToNext()) {

                String item = cursor.getString(indiceNome);
                String valor = cursor.getString(indiceValor);
                String data = cursor.getString(indiceData);

                String textoJuntado = item + " - R$" + valor + " - " + data;
                System.out.println("Texto juntado: " + textoJuntado);
                listaArray.add(textoJuntado);

            }

            cursor.close();

        } catch (Exception e){
            System.out.println("CAIU NA EXCEÇÃO DO BD NA ACTIVITY ListActivity!!!");
            e.printStackTrace();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                listaArray);

        lista.setAdapter(arrayAdapter);


    }
}