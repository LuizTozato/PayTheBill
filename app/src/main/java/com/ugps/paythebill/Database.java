package com.ugps.paythebill;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

    //CRIANDO O AJUDANTE
    private static SQLiteOpenHelper helper = null;

    public static SQLiteDatabase openDB(Context context) {

        //AQUI ESTOU CRIANDO O BANCO SE ELE AINDA NÃO EXISTE
        if (helper == null) {
            //ISSO É UM SINGLETON (a variável helper)
            helper = new SQLiteOpenHelper(context, "app", null, 1) {
                @Override
                public void onCreate(SQLiteDatabase bancoDados) {
                    //CRIANDO E INSERINDO O DADO
                    bancoDados.execSQL("CREATE TABLE IF NOT EXISTS compras ( nome VARCHAR, valor DOUBLE(6), data DATE ) ");
                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

                }
            };
        }

        //AQUI ESTOU ABRINDO O BANCO
        return helper.getWritableDatabase();
    }
}
