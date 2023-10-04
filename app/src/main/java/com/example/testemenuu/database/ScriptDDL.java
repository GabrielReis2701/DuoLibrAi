package com.example.testemenuu.database;

public class ScriptDDL {

    public static String getCreateTable(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT  EXISTS NOTIFICACAO ( ");
        sql.append("     CODIGO INTEGER PRIMARY KEY AUTOINCREMENT,");
        sql.append("     TITULO VARCHAR (250) NOT NULL DEFAULT (''), " );
        sql.append("     DESCRICAO VARCHAR (250) NOT NULL DEFAULT (''), " );
        sql.append("     IMAGEM VARCHAR (250))" );


        return sql.toString();
    }

    public static String getCreateTableMensagens(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT  EXISTS MENSAGENS ( ");
        sql.append("     CODIGO INTEGER PRIMARY KEY AUTOINCREMENT,");
        sql.append("     TITULO VARCHAR (250) NOT NULL DEFAULT (''), " );
        sql.append("     DESCRICAO VARCHAR (250) NOT NULL DEFAULT (''), " );
        sql.append("     IMAGEM VARCHAR (250))" );

        return sql.toString();
    }

    public static String getCreateTablePapel(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT  EXISTS PAPEL ( ");
        sql.append("     CODIGO INTEGER PRIMARY KEY AUTOINCREMENT,");
        sql.append("     IDPAPEL VARCHAR (250) NOT NULL DEFAULT ('') )" );

        return sql.toString();
    }

    public static String setPapel(){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO PAPEL (IDPAPEL) ");
        sql.append("VALUES ('fundo_main');");
        sql.append("WHERE NOT EXISTS (SELECT 1 FROM PAPEL WHERE IDPAPEL = fundo_main);");
        return sql.toString();
    }

}
