package com.ugps.paythebill.Principais;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.ugps.paythebill.BancoDeDados.MySQLiteDatabase;
import com.ugps.paythebill.Firebase.FirebaseClass;
import com.ugps.paythebill.Objetos.ItemComprado;
import com.ugps.paythebill.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdicionarItemActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private Toolbar toolbar;
    private Switch switch1, switch2;
    private TextInputEditText itemComprado, valorCompra, dataCompra;
    private Button botaoAdicionar,botaoVoltar;
    SharedPreferences sharedPreferences;
    private final FirebaseClass firebaseClass = new FirebaseClass();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_item);
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu);

        //CONECTANDO OS NOMES ÀS ENTIDADES
        switch1 = findViewById(R.id.switch1);
        switch1.setChecked(true);

        switch2 = findViewById(R.id.switch2);
        itemComprado = findViewById(R.id.itemComprado);
        valorCompra = findViewById(R.id.valorCompra);
        dataCompra = findViewById(R.id.dataCompra);
        botaoAdicionar = findViewById(R.id.botaoAdicionar);
        botaoVoltar = findViewById(R.id.botaoVoltar);

        //RECUPERANDO CONFIGURAÇÕES / PREFERÊNCIAS
        sharedPreferences = getSharedPreferences("nomes", MODE_PRIVATE);

        String nome = sharedPreferences.getString("nome1", null);

        if (nome == null) {
            Intent intent = new Intent(getApplicationContext(), ConfiguracoesActivity.class);
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
                        Intent intent = new Intent(getApplicationContext(), ConfiguracoesActivity.class);
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

        //MASCARA DE DATA - github.com/rtoshiro
        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("NN-NN-NNNN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(dataCompra,simpleMaskFormatter);
        dataCompra.addTextChangedListener(maskTextWatcher);

        //============================ COLOCANDO VALORES NA LISTA ============================
        botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = itemComprado.getText().toString();
                Double valor = Double.parseDouble(valorCompra.getText().toString());
                String data = dataCompra.getText().toString();

                if(!inputDateChecker(data)){
                    Toast.makeText(getApplicationContext(),"Data inválida.",Toast.LENGTH_LONG).show();
                    return;
                }

                String comprador;
                if(switch1.isChecked()){
                    comprador = switch1.getText().toString();
                } else {
                    comprador = switch2.getText().toString();
                }

                //Acessando o firebase para guardar as informações do item
                ItemComprado itemComprado = new ItemComprado(nome,valor,data,comprador);
                firebaseClass.getItens().push().setValue(itemComprado);

                /*acessando o BD - SQLite
                SQLiteDatabase bancoDados = MySQLiteDatabase.openDB(getApplicationContext());
                //adicionando valor ao BD
                MySQLiteDatabase.addValueBD(getApplicationContext(),item,valor,data,comprador);
                */

                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);


            }
        });
        
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Montando pacote de comando
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);

                //iniciando outra activity passando origem e destino
                startActivity(intent);
            }
        });

    }

    private boolean inputDateChecker(String data){

        Date date;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date = simpleDateFormat.parse(data);
        } catch (Exception e) {
            return false;
        }

        Date today = new Date();

        String[] dataSplit = data.split("-");
        Integer dia = Integer.valueOf(dataSplit[0]);
        Integer mes = Integer.valueOf(dataSplit[1]);
        Integer ano = Integer.valueOf(dataSplit[2]);

        if ( dia > 31 || mes > 12 || date.compareTo(today) > 0 ){
            return false;
        }

        return true;
    };

}
