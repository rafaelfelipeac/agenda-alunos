package com.example.rafae.agenda.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rafae.agenda.R;
import com.example.rafae.agenda.fragments.DetalhesProvaFragment;
import com.example.rafae.agenda.fragments.ListaProvasFragment;
import com.example.rafae.agenda.modelo.Prova;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();

        tx.replace(R.id.frame_principal, new ListaProvasFragment());

        if(modoPaisagem()) {
            tx.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }


        tx.commit();
    }

    private boolean modoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager manager = getSupportFragmentManager();
        if(!modoPaisagem()) {
            FragmentTransaction tx = manager.beginTransaction();

            Bundle parametros = new Bundle();
            parametros.putSerializable("prova", prova);
            DetalhesProvaFragment detalhesFragment = new DetalhesProvaFragment();
            detalhesFragment.setArguments(parametros);

            tx.replace(R.id.frame_principal,  detalhesFragment);

            tx.addToBackStack(null);

            tx.commit();
        } else {
            DetalhesProvaFragment detalhesFragment = (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_secundario);
            detalhesFragment.populaCamposCom(prova);
        }
    }
}