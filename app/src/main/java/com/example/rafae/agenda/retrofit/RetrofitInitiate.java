package com.example.rafae.agenda.retrofit;

import com.example.rafae.agenda.services.AlunoService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Rafael Felipe on 27/06/2017.
 */

public class RetrofitInitiate {

    private final Retrofit retrofit;

    public RetrofitInitiate() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.2:8080/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public AlunoService getAlunoService() {
        return retrofit.create(AlunoService.class);
    }
}
