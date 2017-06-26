package com.example.rafae.agenda;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.example.rafae.agenda.DAO.AlunoDAO;
import com.example.rafae.agenda.modelo.Aluno;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by rafae on 11/10/2016.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng posicaoEscola = pegaCoordenadaDoEndereco("Rua Juvenal Alves Senne 178, Paineiras, Sorocaba");

        if (posicaoEscola != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoEscola, 17);
            googleMap.moveCamera(update);

//            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                googleMap.setMyLocationEnabled(true);
//            }
//            else {
//                ActivityCompat.requestPermissions(getActivity(), new String[] {
//                                android.Manifest.permission.ACCESS_FINE_LOCATION,
//                                android.Manifest.permission.ACCESS_COARSE_LOCATION }, 321);
//
//                googleMap.setMyLocationEnabled(true);
//            }
        }

        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        for(Aluno aluno : alunoDAO.Buscar())         {
            LatLng coordenada = pegaCoordenadaDoEndereco(aluno.getEndereco());
            if(coordenada != null) {
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());;
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }
        alunoDAO.close();

        new Localizador(getContext(), googleMap, getActivity());
    }

    private LatLng pegaCoordenadaDoEndereco (String endereco) {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> resultados = geocoder.getFromLocationName(endereco, 1);

            if(!resultados.isEmpty()) {
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return  posicao;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}