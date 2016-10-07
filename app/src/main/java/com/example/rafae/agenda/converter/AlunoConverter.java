package com.example.rafae.agenda.converter;

import com.example.rafae.agenda.modelo.Aluno;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

/**
 * Created by rafae on 07/10/2016.
 */
public class AlunoConverter  {


    public String converteParaJSON(List<Aluno> alunos) {
        JSONStringer jsons = new JSONStringer();

        try {
            jsons.object().key("list").array().object().key("aluno").array();
            for(Aluno aluno : alunos)
            {
                jsons.object();
                jsons.key("nome").value(aluno.getNome());
                jsons.key("nota").value(aluno.getNota());
                jsons.endObject();
            }
            jsons.endArray().endObject().endArray().endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsons.toString();
    }
}
