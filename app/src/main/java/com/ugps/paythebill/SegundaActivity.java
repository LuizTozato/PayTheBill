package com.ugps.paythebill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SegundaActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private Toolbar toolbar;
    private Switch switch1, switch2;
    private TextInputEditText itemComprado, valorCompra, dataCompra;
    private Button botaoAdicionar;
    private ArrayList<String> listaArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pay the Bill");
        toolbar.inflateMenu(R.menu.menu);

        //CONECTANDO OS NOMES ÀS ENTIDADES
        switch1 =           findViewById(R.id.switch1);
        switch1.setChecked(true);

        switch2 =           findViewById(R.id.switch2);
        itemComprado =      findViewById(R.id.itemComprado);
        valorCompra =       findViewById(R.id.valorCompra);
        dataCompra =        findViewById(R.id.dataCompra);
        botaoAdicionar =    findViewById(R.id.botaoAdicionar);

        //RECUPERANDO CONFIGURAÇÕES / PREFERÊNCIAS
        SharedPreferences sharedPreferences = getSharedPreferences("nomes",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String nome = sharedPreferences.getString("nome1",null);
        
        if (nome == null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        //RECEBENDO A INTENT VINDO DAS SETTINGS E RECUPERANDO DADOS DA SHARED PREFERENCES
        //if (sharedPreferences != null ) {

           //SETTANDO NOME DOS SWITCHES
            switch1.setText(sharedPreferences.getString("nome1",null));
            switch2.setText(sharedPreferences.getString("nome2",null));

        //}

        //GARANTINDO UNICO SWITCH ATIVADO
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switch2.isChecked()){
                    switch2.setChecked(false); //desliga o outro switch
                }
            }
        });
        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switch1.isChecked()){
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

                String textoJuntado =   itemComprado.getText().toString()+
                        " R$"+valorCompra.getText().toString()+
                        " "+dataCompra.getText().toString();

                listaArray.add(textoJuntado);

                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putStringArrayListExtra("Spaceship",listaArray);
                startActivity(intent);

            }
        });


    }
}
