package com.example.rafae.agenda.services;

import com.example.rafae.agenda.modelo.Aluno;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Rafael Felipe on 27/06/2017.
 */

public interface AlunoService {

    @POST("aluno")
    Call <Void> insert(@Body Aluno aluno);
}
