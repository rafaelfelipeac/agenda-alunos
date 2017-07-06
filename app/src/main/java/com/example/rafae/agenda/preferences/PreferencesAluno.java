package com.example.rafae.agenda.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Rafael Felipe on 05/07/2017.
 */

public class PreferencesAluno {
    private static final String PREFERENCES_ALUNO = "com.example.rafae.agenda.preferences.PreferencesAluno";
    private static final String VERSAO_DADO = "versao_dado";
    private Context context;

    public PreferencesAluno(Context context) {
        this.context = context;
    }

    public void salvarVersao(String versao) {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(VERSAO_DADO, versao);
        editor.commit();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCES_ALUNO, context.MODE_PRIVATE);
    }

    public String getVersao() {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getString(VERSAO_DADO, "");
    }

    public boolean temVersao() {
        return !getVersao().isEmpty();
    }
}
