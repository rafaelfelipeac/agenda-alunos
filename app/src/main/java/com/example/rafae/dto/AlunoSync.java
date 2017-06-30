package com.example.rafae.dto;

import com.example.rafae.agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by Rafael Felipe on 30/06/2017.
 */

public class AlunoSync {
    private List<Aluno> alunos;
    private String momentoDaUltimaModificacao;

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public String getmomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }
}
