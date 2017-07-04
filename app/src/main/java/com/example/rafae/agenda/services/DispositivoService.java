package com.example.rafae.agenda.services;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Rafael Felipe on 03/07/2017.
 */

public interface DispositivoService {
    @POST("firebase/dispositivo")
    Call<Void> SendToken(@Header("token") String token);
}
