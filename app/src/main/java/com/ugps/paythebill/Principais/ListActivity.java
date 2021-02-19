package com.ugps.paythebill.Principais;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ugps.paythebill.BancoDeDados.MySQLiteDatabase;
import com.ugps.paythebill.Firebase.FirebaseClass;
import com.ugps.paythebill.Objetos.ItemComprado;
import com.ugps.paythebill.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListActivity extends AppCompatActivity {

    private TextView textNome1,textNome2,textValor1,textValor2,textFinal;
    private FloatingActionButton floatingActionButton;
    private ListView lista;
    private ArrayList<ItemComprado> listaArray = new ArrayList<>();
    SharedPreferences sharedPreferences;
    private final FirebaseClass firebaseClass = new FirebaseClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        //CONECTANDO COM AS ENTIDADES
        lista = findViewById(R.id.lista);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        textNome1  = findViewById(R.id.textNome1);
        textNome2  = findViewById(R.id.textNome2);
        textValor1 = findViewById(R.id.textValor1);
        textValor2 = findViewById(R.id.textValor2);
        textFinal  = findViewById(R.id.textFinal);

        //RECUPERANDO CONFIGURAÇÕES / PREFERÊNCIAS =================================================
        sharedPreferences = getSharedPreferences("nomes", MODE_PRIVATE);

        //FAB ======================================================================================
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Montando pacote de comando
                Intent intent = new Intent(getApplicationContext(), AdicionarItemActivity.class);

                //iniciando outra activity passando origem e destino
                startActivity(intent);

            }
        });

        //CHAMAR FUNÇÃO DE CARREGAMENTO DE DADOS DO BD =============================================
        carregarDadosNaListView();

        //EVENTOS DE CLIQUE ========================================================================
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //chando função externa para confirmar exclusão de item
                dialogCall(parent,view,position,id);

            }
        });

        //CARREGAR SHAREDPREFERENCES ===============================================================
        carregarSharedPreferences();

        //LER O BD PARA SABER QUANTO CADA UM GASTOU ================================================
        lerBDeSettarValoresTotais();

    }




    // FUNÇÕES EXTERNAS AO ONCREATE

    public void carregarDadosNaListView(){

        //MÉTODO USANDO FIREBASE
        firebaseClass.getItens().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ItemComprado ic = ds.getValue(ItemComprado.class);
                    listaArray.add(ic);
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, listaArray) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text1 = view.findViewById(android.R.id.text1);
                        TextView text2 = view.findViewById(android.R.id.text2);

                        //linha de cima do layout duplo
                        text1.setText(listaArray.get(position).getNome() + " - R$" + listaArray.get(position).getValor().toString() + " em: " + listaArray.get(position).getData());
                        text1.setTextColor(getResources().getColor(R.color.white));
                        //linha de baixo do layout duplo
                        text2.setText(listaArray.get(position).getComprador());
                        text2.setTextColor(getResources().getColor(R.color.white));

                        return view;
                    }
                };

                lista.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("ERRO meuuu: " + error.getDetails());
            }
        });

    }

    public void dialogCall(AdapterView<?> parent, View view, final int position, long id){

        //Instanciando o AlertDialog - usar o contexto dessa activity (this) e não o global (getAplicationContext)
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        //Configurando o titlo e mensagem
        dialog.setTitle("Exclusão de compra");
        dialog.setMessage("Deseja excluir esta compra realizada?");

        //Configura as ações para "sim" e "não"
        //SIM
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //PRECISO PROCURAR VIA QUERY ONDE ESTÁ O QUE EU QUERO DELETAR
                    String nomeDaCompra = listaArray.get(position).getNome();
                    Query busca = firebaseClass.getItens().orderByChild("nome").equalTo(nomeDaCompra);

                    busca.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dadoEncontrado: snapshot.getChildren()){
                                dadoEncontrado.getRef().removeValue();
                            }

                            //Retira da ArrayList o item que cliquei
                            listaArray.remove(position);

                            finish();
                            startActivity(getIntent());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });


                }
        });

        //NÃO
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //Criar e exibir a AlertDialog
        dialog.create();
        dialog.show();

    }

    private void carregarSharedPreferences() {

        textNome1.setText(sharedPreferences.getString("nome1", null));
        textNome2.setText(sharedPreferences.getString("nome2", null));

    }

    private void lerBDeSettarValoresTotais() {

        String nome1 = sharedPreferences.getString("nome1", null);
        String nome2 = sharedPreferences.getString("nome2", null);

        try {
            android.database.sqlite.SQLiteDatabase bancoDados = MySQLiteDatabase.openDB(getApplicationContext());
            Cursor cursor = bancoDados.rawQuery("SELECT valor FROM compras WHERE comprador = '"+nome1+"' ", null);

            //INDICES DA TABELA
            int indiceValor = cursor.getColumnIndex("valor");

            Double valor1 = 0.0;

            //posicionar o cursor o inicio da tabela
            while (cursor.moveToNext()) {
                valor1 += cursor.getDouble(indiceValor);
            }
            cursor.close();

            textValor1.setText(String.valueOf(valor1));

            //===============================================
            cursor = bancoDados.rawQuery("SELECT valor FROM compras WHERE comprador = '"+nome2+"' ", null);

            Double valor2 = 0.0;

            //posicionar o cursor o inicio da tabela
            while (cursor.moveToNext()) {
                valor2 += cursor.getDouble(indiceValor);
            }
            cursor.close();

            textValor2.setText(String.valueOf(valor2));

            // FINAL ===============================================
            if( valor1 > valor2 ){
                textFinal.setText(nome2);
            } else {
                textFinal.setText(nome1);
            }


        } catch (Exception e){
            System.out.println("CAIU NA EXCEÇÃO da leitura do quanto cada um gastou!!!");
            e.printStackTrace();
        }


    }

}