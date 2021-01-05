package com.ugps.paythebill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SegundaActivity extends AppCompatActivity {

    private TextView servidor1;
    private TextView servidor2;

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

    }
}
