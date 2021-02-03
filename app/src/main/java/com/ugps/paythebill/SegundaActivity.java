package com.ugps.paythebill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SegundaActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private Switch switch1, switch2;

    private ListView listGastos;
    private ArrayList<String> lista = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        //CONECTANDO OS NOMES ÀS ENTIDADES
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);

        //RECUPERANDO OS DADOS DA INTENT
        Bundle dados = getIntent().getExtras();
        final String nome1 = dados.getString("nome1");
        final String nome2 = dados.getString("nome2");

        //SETTANDO NOME DOS SWITCHES
        switch1.setText(nome1);
        switch2.setText(nome2);


        /*
        servidor1 = findViewById(R.id.textServidor1);
        servidor2 = findViewById(R.id.textServidor2);

        total1    = findViewById(R.id.textTotal1);
        String zero = "0";
        total1.setText(zero);

        total2    = findViewById(R.id.textTotal2);
        total2.setText(zero);

        resultado = findViewById(R.id.textResultado);

        //recuperando os dados vindo da Intent
        Bundle dados = getIntent().getExtras();
        final String nome1 = dados.getString("nome1");
        final String nome2 = dados.getString("nome2");

        servidor1.setText(nome1);
        servidor2.setText(nome2);

        //================ COLOCANDO VALOR NA LISTA ====================

        listGastos1 = findViewById(R.id.listGastos1);
        listGastos2 = findViewById(R.id.listGastos2);
        Button buttonAdicionar1 = findViewById(R.id.buttonAdicionar1);
        Button buttonAdicionar2 = findViewById(R.id.buttonAdicionar2);
        editValor1 = findViewById(R.id.editValor1);
        editValor2 = findViewById(R.id.editValor2);
        editDescrição1 = findViewById(R.id.editDescrição1);
        editDescrição2 = findViewById(R.id.editDescrição2);

        //============== BOTÃO SERVIDOR 1 ========================
        buttonAdicionar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String timeStamp = sdf.format(new Date());

                String str = editDescrição1.getText().toString() + " - R$"
                               + editValor1.getText().toString() + "\n"
                               + timeStamp;
                itens1.add(str);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                                                            getApplicationContext(),
                                                                            android.R.layout.simple_list_item_1,
                                                                            itens1);
                listGastos1.setAdapter(arrayAdapter);

                double aux = Double.parseDouble(total1.getText().toString());
                aux += Double.parseDouble(editValor1.getText().toString());
                total1.setText(String.valueOf(aux));

                double totalPago1 = Double.parseDouble(total1.getText().toString());
                double totalPago2 = Double.parseDouble(total2.getText().toString());

                if(totalPago1 > totalPago2){
                    resultado.setText(nome2);
                } else {
                    resultado.setText(nome1);
                }

            }
        });


        //============== BOTÃO SERVIDOR 2 ========================
        buttonAdicionar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String timeStamp = sdf.format(new Date());

                String str = editDescrição2.getText().toString() + " - R$"
                        + editValor2.getText().toString() + "\n"
                        + timeStamp;
                itens2.add(str);

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        itens2);
                listGastos2.setAdapter(arrayAdapter);

                double aux = Double.parseDouble(total2.getText().toString());
                aux += Double.parseDouble(editValor2.getText().toString());
                total2.setText(String.valueOf(aux));

                double totalPago1 = Double.parseDouble(total1.getText().toString());
                double totalPago2 = Double.parseDouble(total2.getText().toString());

                if(totalPago1 > totalPago2){
                    resultado.setText(nome2);
                } else {
                    resultado.setText(nome1);
                }

            }
        });*/
    }
}
