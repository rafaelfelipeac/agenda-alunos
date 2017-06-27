package com.example.rafae.agenda.helper;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.rafae.agenda.R;
import com.example.rafae.agenda.activities.FormularioProvaActivity;
import com.example.rafae.agenda.modelo.Prova;

/**
 * Created by rafae on 12/10/2016.
 */

public class FormularioProvaHelper {
    private final EditText campoMateria;
    private final EditText campoData;
    private Activity activity;
    private String separator = "_,_";

    private Prova prova;

    public FormularioProvaHelper(FormularioProvaActivity actiity) {
        this.activity = actiity;
        campoMateria = (EditText) actiity.findViewById(R.id.formulario_prova_materia);
        campoData = (EditText) actiity.findViewById(R.id.formulario_prova_data);

        prova = new Prova();
    }

    public Prova pegaProva() {
        prova.setMateria(campoMateria.getText().toString());
        prova.setData(campoData.getText().toString());
        String txt = convertArrayToString();
        prova.setConteudos(txt);

        return prova;
    }

    public void preenche(Prova prova) {
        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());

        populaSelecionados(prova);

        this.prova = prova;
    }

    public String[] verificaSelecionados() {

        final LinearLayout ll = (LinearLayout) activity.findViewById(R.id.formulario_prova_ll);
        String[] array = new String[ll.getChildCount()];
        int cont = 0;

        for(int i = 0; i < ll.getChildCount(); i++) {
            CheckBox cb = (CheckBox) ll.getChildAt(i);
            if(cb.isChecked()) {
                array[cont] = (cb.getText().toString());
                cont++;
            }
        }
        return array;
    }

    public String convertArrayToString() {
        String[] array = verificaSelecionados();
        String s = "";

        for(int i = 0; i < array.length; i++)
        {
            if(array[i] != null && array[i] != "null") {
                s += array[i];

                if (i < array.length - 1)
                    s += separator;
            }
        }

        return s;
    }

    public String[] convertStringToArray(String str) {
        String[] array = str.split(separator);
        return array;
    }

    public void populaSelecionados(Prova prova) {
        String s = prova.getConteudos();
        if(s == null)
            return;
        else {
            String[] array = convertStringToArray(s);
            final LinearLayout ll = (LinearLayout) activity.findViewById(R.id.formulario_prova_ll);

            for (int i = 0; i < array.length; i++) {
                if(array[i] != "null") {
                    CheckBox cb = new CheckBox(activity);
                    cb.setChecked(true);
                    cb.setText(array[i]);
                    ll.addView(cb);
                }
            }
        }
    }
}
