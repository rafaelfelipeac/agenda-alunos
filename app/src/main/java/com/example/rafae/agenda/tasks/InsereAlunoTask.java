package com.example.rafae.agenda.tasks;

import android.os.AsyncTask;

import com.example.rafae.agenda.WebClient;
import com.example.rafae.agenda.converter.AlunoConverter;
import com.example.rafae.agenda.modelo.Aluno;

/**
 * Created by Rafael Felipe on 26/06/2017.
 */

public class InsereAlunoTask extends AsyncTask {
    private final Aluno aluno;

    public InsereAlunoTask(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String json = new AlunoConverter().convertToJSON(aluno);
        new WebClient().Insert(json);
        return null;
    }
}
