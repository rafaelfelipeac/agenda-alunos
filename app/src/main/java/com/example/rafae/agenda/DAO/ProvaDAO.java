package com.example.rafae.agenda.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.rafae.agenda.modelo.Aluno;
import com.example.rafae.agenda.modelo.Prova;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafae on 12/10/2016.
 */

public class ProvaDAO extends SQLiteOpenHelper{

    public ProvaDAO(Context context) {
        super(context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table provas (id integer primary key, materia text not null, data text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @NonNull
    private ContentValues getContentValuesProva(Prova prova) {
        ContentValues dados = new ContentValues();
        dados.put("materia", prova.getMateria());
        dados.put("data", prova.getData());

        return dados;
    }

    public void Remover(Prova prova) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {prova.getId().toString()};

        db.delete("Provas", "id = ?", params);
    }

    public void Inserir(Prova prova) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValuesProva(prova);

        db.insert("provas", null, dados);
    }

    public List<Prova> Buscar() {
        List<Prova> provas = new ArrayList<>();
        String sql = "select * from provas";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c =  db.rawQuery(sql, null);
        c.moveToNext();

        while(c.moveToNext())
        {
            Prova prova = new Prova();

            prova.setId(c.getLong(c.getColumnIndex("id")));
            prova.setMateria(c.getString(c.getColumnIndex("materia")));
            prova.setData(c.getString(c.getColumnIndex("data")));

            provas.add(prova);
        }
        c.close();

        return provas;
    }

    public void Alterar(Prova prova) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getContentValuesProva(prova);
        String[] params = {prova.getId().toString()};
        db.update("provas", dados, "id = ?", params);
    }
}
