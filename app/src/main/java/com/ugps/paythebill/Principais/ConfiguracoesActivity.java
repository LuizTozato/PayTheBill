package com.ugps.paythebill.Principais;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.ugps.paythebill.Firebase.FirebaseClass;
import com.ugps.paythebill.R;

public class ConfiguracoesActivity extends AppCompatActivity {

    private Button buttonEnter,buttonLogout;
    private EditText servidor1,servidor2,email,senha;
    SharedPreferences sharedPreferences;
    private final FirebaseClass firebaseClass = new FirebaseClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        buttonEnter     = findViewById(R.id.buttonEnter);
        buttonLogout    = findViewById(R.id.buttonLogout);
        servidor1       = findViewById(R.id.editServidor1);
        servidor2       = findViewById(R.id.editServidor2);
        email           = findViewById(R.id.editTextEmailAddress);
        senha           = findViewById(R.id.editTextPassword);

        sharedPreferences = getSharedPreferences("nomes",MODE_PRIVATE);

        if( firebaseClass.getUsuario().getCurrentUser() != null) {
            email.setText(firebaseClass.getUsuario().getCurrentUser().getEmail());
            Toast.makeText(getApplicationContext(), "Usuário já está logado!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Usuário não logado. Faça login ou cadastre-se.", Toast.LENGTH_LONG).show();
        }

        //BOTÃO ENTER ==============================
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( isEmpty(email) || isEmpty(senha) || isEmpty(servidor1) || isEmpty(servidor2)){
                    Toast.makeText(getApplicationContext(), "Não pode haver espaços vazios", Toast.LENGTH_LONG).show();
                    return;
                }

                //GERENCIAMENTO DE USUARIOS =======================================
                //aqui, os inputs já estão saneados
                if(firebaseClass.getUsuario().getCurrentUser() != null){
                    //USUÁRIO JÁ LOGADO, SÓ QUER MUDAR O NOME DOS ENVOLVIDOS
                    logarOuCadastrar();
                } else {
                    //USUÁRIO NÃO LOGADO. 1º TENTAR LOGAR. 2º TENTAR CADASTRAR
                    logarOuCadastrar();
                }
            }
        });

        //BOTÃO LOGOUT ==============================
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseClass.getUsuario().signOut();
                Toast.makeText(getApplicationContext(),"Logout realizado com sucesso.",Toast.LENGTH_LONG).show();
            }
        });

    }


    // ====================================================================
    // METODOS EXTERNOS ===================================================
    public boolean isEmpty (EditText campo){
        String texto = campo.getText().toString();
        if (texto.matches("")){
            return true;
        } else {
            return false;
        }
    };

    public void carregarPreferenciasMudarActivity(){
        //CARREGANDO O NOME DOS ENVOLVIDOS
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nome1",servidor1.getText().toString());
        editor.putString("nome2",servidor2.getText().toString());
        editor.apply(); //não esquecer esse comando!!!

        //Montando pacote de comando
        Intent intent = new Intent(getApplicationContext(), AdicionarItemActivity.class);
        startActivity(intent);

        Toast.makeText(getApplicationContext(),"Integrantes atualizados!",Toast.LENGTH_LONG).show();
    }

    public void logarOuCadastrar(){
        firebaseClass.getUsuario().signInWithEmailAndPassword(
                email.getText().toString(),
                senha.getText().toString())
                .addOnCompleteListener(ConfiguracoesActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Sucesso ao logar usuário!", Toast.LENGTH_LONG).show();
                            carregarPreferenciasMudarActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao logar usuário...", Toast.LENGTH_LONG).show();
                            cadastrar();
                        }
                    }
                });
    }

    public void cadastrar(){
        firebaseClass.getUsuario().createUserWithEmailAndPassword(
                email.getText().toString(),
                senha.getText().toString())
                .addOnCompleteListener(ConfiguracoesActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Sucesso ao cadastrar usuário!", Toast.LENGTH_LONG).show();
                            carregarPreferenciasMudarActivity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao cadastrar usuário", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
    }
}
