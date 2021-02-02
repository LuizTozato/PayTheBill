package com.ugps.paythebill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    
    private Button buttonEnter;
    private EditText servidor1,servidor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEnter = findViewById(R.id.buttonEnter);
        servidor1   = findViewById(R.id.editServidor1);
        servidor2   = findViewById(R.id.editServidor2);

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Montando pacote de comando
                Intent intent = new Intent(getApplicationContext(), SegundaActivity.class);

                //Adicionando informações extras
                intent.putExtra("nome1",servidor1.getText().toString());
                intent.putExtra("nome2",servidor2.getText().toString());

                //iniciando outra activity passando origem e destino
                startActivity(intent);
            }
        });


    }
}
