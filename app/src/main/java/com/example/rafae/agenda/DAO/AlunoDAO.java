package com.example.rafae.agenda.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.rafae.agenda.modelo.Aluno;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafae on 04/10/2016.
 */
public class AlunoDAO extends SQLiteOpenHelper {

    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table alunos (id integer primary key, nome text not null, endereco text, telefone text, site text, nota real, caminhoFoto text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch(oldVersion)
        {
            case 1:
                String sql = "alter table alunos add column caminhofoto text";
                db.execSQL(sql);
        }
    }

    public void Inserir(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValuesAluno(aluno);

        db.insert("Alunos", null, dados);
    }

    @NonNull
    private ContentValues getContentValuesAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhofoto", aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> Buscar() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "select * from alunos";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c =  db.rawQuery(sql, null);
        c.moveToNext();

        while(c.moveToNext())
        {
            Aluno aluno = new Aluno();

            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhofoto")));

            alunos.add(aluno);
        }
        c.close();

        return alunos;
    }

    public void Remover(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {aluno.getId().toString()};

        db.delete("Alunos", "id = ?", params);
    }

    public void Alterar(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getContentValuesAluno(aluno);
        String[] params = {aluno.getId().toString()};
        db.update("Alunos", dados, "id = ?", params);
    }

    public boolean VerificaAluno(String telefone)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from alunos where telefone = ?", new String[]{telefone});

        boolean results = c.getCount() > 0;
        c.close();

        return results;
    }
}
