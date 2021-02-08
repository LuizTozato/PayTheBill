package com.ugps.paythebill.Principais;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ugps.paythebill.BancoDeDados.Database;
import com.ugps.paythebill.Objetos.ItemComprado;
import com.ugps.paythebill.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private ListView lista;
    private ArrayList<ItemComprado> listaArray = new ArrayList<>();


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
            Cursor cursor = bancoDados.rawQuery("SELECT nome,valor,data,comprador FROM compras", null);

            //INDICES DA TABELA
            int indiceNome = cursor.getColumnIndex("nome");
            int indiceValor = cursor.getColumnIndex("valor");
            int indiceData = cursor.getColumnIndex("data");
            int indiceComprador = cursor.getColumnIndex("comprador");

            //posicionar o cursor o inicio da tabela
            while (cursor.moveToNext()) {

                String item = cursor.getString(indiceNome);
                Double valor = cursor.getDouble(indiceValor);

                String dataString =  cursor.getString(indiceData);
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date data = format.parse(dataString);

                String comprador = cursor.getString(indiceComprador);

                ItemComprado itemComprado = new ItemComprado(item,valor,data,comprador);

                listaArray.add(itemComprado);
            }

            cursor.close();

        } catch (Exception e){
            System.out.println("CAIU NA EXCEÇÃO DO BD NA ACTIVITY ListActivity!!!");
            e.printStackTrace();
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, listaArray){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = view.findViewById(android.R.id.text1);
                TextView text2 = view.findViewById(android.R.id.text2);

                //linha de cima do layout duplo
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                text1.setText(listaArray.get(position).getNome() + " - R$" + listaArray.get(position).getValor().toString() + " em: " + sdf.format(listaArray.get(position).getData()));
                //linha de baixo do layout duplo
                text2.setText(listaArray.get(position).getComprador());

                return view;
            }
        };

        lista.setAdapter(arrayAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }
}









