package com.example.testemenuu.database;

public class ScriptDDL {

    public static String getCreateTable(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT  EXISTS NOTIFICACAO ( ");
        sql.append("     CODIGO INTEGER PRIMARY KEY AUTOINCREMENT,");
        sql.append("     TITULO VARCHAR (250) NOT NULL DEFAULT (''), " );
        sql.append("     DESCRICAO VARCHAR (250) NOT NULL DEFAULT (''), " );
        sql.append("     HORA INTEGER NOT NULL )" );

        return sql.toString();
    }
}
