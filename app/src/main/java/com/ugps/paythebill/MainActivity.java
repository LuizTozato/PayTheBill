package com.ugps.paythebill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonEnter;
    private EditText servidor1,servidor2;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEnter = findViewById(R.id.buttonEnter);
        servidor1   = findViewById(R.id.editServidor1);
        servidor2   = findViewById(R.id.editServidor2);

        sharedPreferences = getSharedPreferences("nomes",MODE_PRIVATE);

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nome1",servidor1.getText().toString());
                editor.putString("nome2",servidor2.getText().toString());
                editor.apply(); //não esquecer esse comando!!!

                //Montando pacote de comando
                Intent intent = new Intent(getApplicationContext(), SegundaActivity.class);
                startActivity(intent);

                Toast.makeText(getApplicationContext(),"Usuários atualizados!",Toast.LENGTH_LONG).show();

            }
        });
    }
}
