package com.ugps.paythebill.BancoDeDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.Date;

public class MySQLiteDatabase {

    //CRIANDO O AJUDANTE
    private static SQLiteOpenHelper helper = null;

    //ABRIR / CRIAR O BD
    public static android.database.sqlite.SQLiteDatabase openDB(Context context) {

        //AQUI ESTOU CRIANDO O BANCO SE ELE AINDA NÃO EXISTE
        if (helper == null) {
            //ISSO É UM SINGLETON (a variável helper)
            helper = new SQLiteOpenHelper(context, "app", null, 1) {
                @Override
                public void onCreate(android.database.sqlite.SQLiteDatabase bancoDados) {
                    //CRIANDO E INSERINDO O DADO
                    bancoDados.execSQL("CREATE TABLE IF NOT EXISTS compras ( nome VARCHAR, valor DOUBLE(6), data DATE, comprador VARCHAR ) ");
                }

                @Override
                public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {

                }
            };
        }

        //AQUI ESTOU ABRINDO O BANCO
        return helper.getWritableDatabase();
    }

    //========================= ADICIONAR VALOR =========================
    public static void addValueBD (Context context, String item, String valor, String data, String comprador) {

        try {
            SQLiteStatement stmt = openDB(context).compileStatement("INSERT INTO compras(nome,valor,data,comprador) VALUES (?,?,?,?)");
            stmt.bindAllArgsAsStrings(new String[]{item, valor, data, comprador});
            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //========================= REMOVER VALOR =========================
    public static void removeValueBD (Context context, String item, String valor, String data, String comprador) {

        try {
            SQLiteStatement stmt = openDB(context).compileStatement("DELETE FROM compras WHERE (nome = ? AND valor = ? AND data = ? AND comprador = ?)");
            stmt.bindAllArgsAsStrings(new String[]{item, valor, data, comprador});
            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
