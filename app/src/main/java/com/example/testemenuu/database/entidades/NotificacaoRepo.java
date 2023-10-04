package com.example.testemenuu.database.entidades;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NotificacaoRepo {

    private SQLiteDatabase conexao;
    private String nome = "";

    public NotificacaoRepo(SQLiteDatabase conexao){
        this.conexao=conexao;
    }
    public void Inserir(Notificacao notificacao){
        ContentValues cv = new ContentValues();
        cv.put("TITULO",notificacao.titulo);
        cv.put("DESCRICAO",notificacao.descricao);
        cv.put("IMAGEM",notificacao.imagem);
        conexao.insertOrThrow("NOTIFICACAO",null,cv);
    }
    public void InserirPapel(String idPapel){
        ContentValues cv = new ContentValues();
        cv.put("IDPAPEL",idPapel);
        conexao.insertOrThrow("PAPEL",null,cv);
    }
    public void InserirMensagem(Notificacao notificacao){
        ContentValues cv = new ContentValues();
        cv.put("TITULO",notificacao.titulo);
        cv.put("DESCRICAO",notificacao.descricao);
        cv.put("IMAGEM",notificacao.imagem);
        conexao.insertOrThrow("MENSAGENS",null,cv);
    }
    public void ExcluirItem(int codigo){
        String[] param = new String[1];
        param[0] = String.valueOf(codigo);
        conexao.delete("NOTIFICACAO","CODIGO = ?",param);
    }
    public void ExcluirPapel(){
        String deleteQuery = "DELETE FROM PAPEL WHERE CODIGO = (SELECT MIN(CODIGO) FROM PAPEL)";
        conexao.execSQL(deleteQuery);
    }

    public void Excluir(){
        String countQuery = "SELECT COUNT(*) FROM NOTIFICACAO";
        Cursor cursor = conexao.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int itemCount = cursor.getInt(0);
        if (itemCount >= 12) {
            String deleteQuery = "DELETE FROM NOTIFICACAO WHERE CODIGO = (SELECT MIN(CODIGO) FROM NOTIFICACAO)";
            conexao.execSQL(deleteQuery);
        }

    }
    //;parei aqui (*.*)
    public List<Notificacao> buscarTodos(){
        List<Notificacao> noticacoes = new ArrayList<Notificacao>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODIGO, TITULO, DESCRICAO, IMAGEM ");
        sql.append("FROM NOTIFICACAO ");
        sql.append("ORDER BY CODIGO DESC");
        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if (resultado.getCount()>0){
            resultado.moveToFirst();
            do{
                Notificacao not = new Notificacao();
                not.codigo = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                not.titulo = resultado.getString(resultado.getColumnIndexOrThrow("TITULO"));
                not.descricao= resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));
                not.imagem = resultado.getString(resultado.getColumnIndexOrThrow("IMAGEM"));

                noticacoes.add(not);
            }while(resultado.moveToNext());
        }
        return noticacoes;
    }
    public String buscarPapel(){

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT IDPAPEL ");
        sql.append("FROM PAPEL ");
        sql.append("WHERE CODIGO = (SELECT MAX(CODIGO) FROM PAPEL)");
        Cursor resultado = conexao.rawQuery(sql.toString(),null);
        resultado.moveToFirst();
        nome = resultado.getString(resultado.getColumnIndexOrThrow("IDPAPEL"));

        return nome;

    }
    public List<Notificacao> buscarMensagens(){
        List<Notificacao> noticacoes = new ArrayList<Notificacao>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODIGO, TITULO, DESCRICAO, IMAGEM ");
        sql.append("FROM MENSAGENS ");
        Cursor resultado = conexao.rawQuery(sql.toString(),null);

        if (resultado.getCount()>0){
            resultado.moveToFirst();
            do{
                Notificacao not = new Notificacao();
                not.codigo = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                not.titulo = resultado.getString(resultado.getColumnIndexOrThrow("TITULO"));
                not.descricao= resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));
                not.imagem = resultado.getString(resultado.getColumnIndexOrThrow("IMAGEM"));

                noticacoes.add(not);
            }while(resultado.moveToNext());
        }
        return noticacoes;
    }

    public boolean buscarNotificacao(String titulo, String descricao){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODIGO, TITULO, DESCRICAO, IMAGEM ");
        sql.append("FROM NOTIFICACAO ");
        sql.append("WHERE TITULO = ? AND DESCRICAO = ? ");

        String[] selectionArgs = { titulo, descricao };
        Cursor cursor = conexao.rawQuery(sql.toString(), selectionArgs);

        boolean notificacaoExiste = (cursor.getCount() > 0);

        cursor.close();
        return notificacaoExiste;
    }
}
