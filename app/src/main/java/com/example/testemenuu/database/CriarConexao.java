package com.example.testemenuu.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;


public class CriarConexao{
    private SQLiteDatabase conexao;
    private Context context;

    public CriarConexao(Context context) {
        this.context = context;
    }
    private DadosOpenHelper dadosOpenHelper;
    public void conectar(){

        try{

            dadosOpenHelper = new DadosOpenHelper(context);
            conexao = dadosOpenHelper.getWritableDatabase();

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

    }
}
