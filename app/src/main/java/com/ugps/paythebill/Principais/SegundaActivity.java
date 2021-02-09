package com.ugps.paythebill.Principais;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.ugps.paythebill.BancoDeDados.Database;
import com.ugps.paythebill.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SegundaActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private Toolbar toolbar;
    private Switch switch1, switch2;
    private TextInputEditText itemComprado, valorCompra, dataCompra;
    private Button botaoAdicionar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pay the Bill");
        toolbar.inflateMenu(R.menu.menu);

        //CONECTANDO OS NOMES ÀS ENTIDADES
        switch1 = findViewById(R.id.switch1);
        switch1.setChecked(true);

        switch2 = findViewById(R.id.switch2);
        itemComprado = findViewById(R.id.itemComprado);
        valorCompra = findViewById(R.id.valorCompra);
        dataCompra = findViewById(R.id.dataCompra);
        botaoAdicionar = findViewById(R.id.botaoAdicionar);

        //RECUPERANDO CONFIGURAÇÕES / PREFERÊNCIAS
        sharedPreferences = getSharedPreferences("nomes", MODE_PRIVATE);

        String nome = sharedPreferences.getString("nome1", null);

        if (nome == null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            //SETTANDO NOME DOS SWITCHES
            switch1.setText(sharedPreferences.getString("nome1", null));
            switch2.setText(sharedPreferences.getString("nome2", null));
        }

        //GARANTINDO UNICO SWITCH ATIVADO
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch2.isChecked()) {
                    switch2.setChecked(false); //desliga o outro switch
                }
            }
        });
        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch1.isChecked()) {
                    switch1.setChecked(false); //desliga o outro switch
                }
            }
        });

        //CONFIGURANDO BOTÃO SETTINGS DO MENU
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        //iniciando outra activity passando origem e destino
                        startActivity(intent);
                        return true;

                    default:
                        // If we got here, the user's action was not recognized.
                        // Invoke the superclass to handle it.
                        return false;
                }
            }
        });

        //============================ COLOCANDO VALORES NA LISTA ============================
        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String item = itemComprado.getText().toString();
                String valor = valorCompra.getText().toString();
                String data = dataCompra.getText().toString();
                if(!inputDateChecker(data)){
                    Toast.makeText(getApplicationContext(),"A data deve ter o formato dd-MM-aaaa.\n Não use barras, use -",Toast.LENGTH_LONG).show();
                    return;
                }

                String comprador;
                if(switch1.isChecked()){
                    comprador = switch1.getText().toString();
                } else {
                    comprador = switch2.getText().toString();
                }

                //acessando o BD
                SQLiteDatabase bancoDados = Database.openDB(getApplicationContext());

                //adicionando valor ao BD
                Database.addValueBD(getApplicationContext(),item,valor,data,comprador);

                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);


            }
        });
    }

    private boolean inputDateChecker(String data){

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date date = format.parse(data);
        } catch (Exception e) {
            return false;
        }

        return true;
    };

}
