package com.example.rafae.agenda.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.rafae.agenda.modelo.Aluno;
import com.google.android.gms.nearby.messages.internal.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by rafae on 04/10/2016.
 */
public class AlunoDAO extends SQLiteOpenHelper {

    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 3);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (id CHAR(36) PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL, " +
                "caminhoFoto TEXT);";
        db.execSQL(sql);

        sql = "create table provas (id integer primary key, materia text not null, data text, conteudos text);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch(oldVersion)
        {
            case 1:
                String newTablesql = "CREATE TABLE Alunos_novo " +
                        "(id CHAR(36) PRIMARY KEY, " +
                        "nome TEXT NOT NULL, " +
                        "endereco TEXT, " +
                        "telefone TEXT, " +
                        "site TEXT, " +
                        "nota REAL, " +
                        "caminhoFoto TEXT);";
                db.execSQL(newTablesql);

                String insertAlunos_new = "INSERT INTO Alunos_novo " +
                        "(id, nome, endereco, telefone, site, nota, caminhoFoto) " +
                        "SELECT id, nome, endereco, telefone, site, nota, caminhoFoto " +
                        "FROM Alunos";
                db.execSQL(insertAlunos_new);

                String dropOldTable = "DROP TABLE Alunos";
                db.execSQL(dropOldTable);

                String changeName = "ALTER TABLE Alunos_novo " +
                        "RENAME TO Alunos";
                db.execSQL(changeName);
            case 2:
                String buscaAlunos = "SELECT * FROM Alunos";
                Cursor c = db.rawQuery(buscaAlunos, null);

                List<Aluno> alunos = populaAlunos(c);

                String atualizaIdAluno = "UPDATE Alunos SET Id = ? WHERE id = ?";

                for (Aluno aluno: alunos) {
                    db.execSQL(atualizaIdAluno, new String[]{generateUUID(), aluno.getId()});
                }
        }
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public void Inserir(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        if(aluno.getId() == null)
            aluno.setId(generateUUID());

        ContentValues dados = getContentValuesAluno(aluno);

        db.insert("Alunos", null, dados);
    }

    @NonNull
    private ContentValues getContentValuesAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("id", aluno.getId());
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> Buscar() {

        String sql = "select * from Alunos";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c =  db.rawQuery(sql, null);

        List<Aluno> alunos = populaAlunos(c);
        c.close();

        return alunos;
    }

    @NonNull
    private List<Aluno> populaAlunos(Cursor c) {
        List<Aluno> alunos = new ArrayList<>();
        while(c.moveToNext())
        {
            Aluno aluno = new Aluno();

            aluno.setId(c.getString(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }
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

    public void Sync(List<Aluno> alunos) {
        for (Aluno aluno: alunos) {
            if(existe(aluno)) {
                if(!aluno.isActive()) {
                    Remover(aluno);
                }
                else {
                    Alterar(aluno);
                }
            }
            else if(aluno.isActive()) {
                Inserir(aluno);
            }
        }
    }

    private boolean existe(Aluno aluno) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id FROM Alunos WHERE id = ? LIMIT 1";
        Cursor cursor = db.rawQuery(sql, new String[]{aluno.getId()});

        return cursor.getCount() > 0;
    }
}
