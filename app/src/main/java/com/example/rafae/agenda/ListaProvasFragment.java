package com.example.rafae.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rafae.agenda.modelo.Prova;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rafae on 10/10/2016.
 */

public class ListaProvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);


        List<String> topicosPortugues = Arrays.asList("A", "B", "C");
        Prova provaPortugues = new Prova("Portugues", "25/05/2016", topicosPortugues);

        List<String> topicosMatematica = Arrays.asList("1", "2", "3");
        Prova provaMatematica = new Prova("Matematica", "26/05/2016", topicosMatematica);

        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, provas);

        ListView lista = (ListView) view.findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Clicou na prova de " + prova, Toast.LENGTH_SHORT).show();

                Intent vaiParaDetalhes = new Intent(getContext(), DetalhesProvaActivity.class);
                vaiParaDetalhes.putExtra("prova", prova);
                startActivity(vaiParaDetalhes);
            }
        });

        return view;
    }
}
