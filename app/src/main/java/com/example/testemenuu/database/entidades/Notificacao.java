package com.example.testemenuu.database.entidades;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Notificacao {

    private SQLiteDatabase conexao;

    public Notificacao(SQLiteDatabase conexao){
        this.conexao=conexao;
    }

    public int codigo;
    public long hora;
    public String titulo;
    public String descricao;

    public Notificacao() {

    }

    public void Inserir(Notificacao notificacao){
        ContentValues cv = new ContentValues();
        cv.put("TITULO",notificacao.titulo);
        cv.put("DESCRICAO",notificacao.descricao);
        cv.put("HORA",notificacao.hora);
        conexao.insertOrThrow("NOTIFICACAO",null,cv);
    }

    public void Excluir(int codigo){
        String [] parm = new String[1];
        parm[0] = String.valueOf(codigo);
        conexao.delete("NOTIFICACAO","CODIGO = ?",parm);

    }
//;parei aqui (*.*)
    public List<Notificacao> buscarTodos(){
        List<Notificacao> noticacoes = new ArrayList<Notificacao>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODIGO, TITULO, DESCRICAO, HORA");
        sql.append("FROM NOTIFICACAO");
        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if (resultado.getCount()>0){
            resultado.moveToFirst();
            do{
                Notificacao not = new Notificacao();
                not.codigo = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                not.titulo = resultado.getString(resultado.getColumnIndexOrThrow("TITULO"));
                not.descricao= resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));
                not.hora = resultado.getInt(resultado.getColumnIndexOrThrow("HORA"));

                noticacoes.add(not);
            }while(resultado.moveToNext());
        }
        return noticacoes;
    }


}
