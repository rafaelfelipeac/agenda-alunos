package com.example.rafae.agenda;

import android.widget.EditText;

import com.example.rafae.agenda.modelo.Prova;

/**
 * Created by rafae on 12/10/2016.
 */

public class FormularioProvaHelper {
    private final EditText campoMateria;
    private final EditText campoData;

    private Prova prova;

    public FormularioProvaHelper(FormularioProvaActivity actiity) {
        campoMateria = (EditText) actiity.findViewById(R.id.formulario_prova_materia);
        campoData = (EditText) actiity.findViewById(R.id.formulario_prova_data);

        prova = new Prova();
    }

    public Prova pegaProva() {
        prova.setMateria(campoMateria.getText().toString());
        prova.setData(campoData.getText().toString());

        return prova;
    }

    public void preenche(Prova prova) {
        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());

        this.prova = prova;
    }
}
