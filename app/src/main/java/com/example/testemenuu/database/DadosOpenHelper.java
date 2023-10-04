package com.example.testemenuu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DadosOpenHelper extends SQLiteOpenHelper {
    public DadosOpenHelper(Context context) {
        super(context, "DADOSNotificacao", null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ScriptDDL.getCreateTable());
        db.execSQL(ScriptDDL.getCreateTableMensagens());
        db.execSQL(ScriptDDL.getCreateTablePapel());

        db.execSQL(ScriptDDL.setPapel());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
