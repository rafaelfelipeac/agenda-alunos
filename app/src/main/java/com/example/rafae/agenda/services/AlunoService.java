package com.example.rafae.agenda.services;

import com.example.rafae.agenda.modelo.Aluno;
import com.example.rafae.dto.AlunoSync;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Rafael Felipe on 27/06/2017.
 */

public interface AlunoService {

    @POST("aluno")
    Call <Void> insert(@Body Aluno aluno);

    @GET("aluno")
    Call <AlunoSync> lista();

    @DELETE("aluno/{id}")
    Call <Void> remove(@Path("id") String id);
}
