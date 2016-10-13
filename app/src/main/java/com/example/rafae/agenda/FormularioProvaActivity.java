package com.example.rafae.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafae.agenda.DAO.ProvaDAO;
import com.example.rafae.agenda.modelo.Prova;

import java.util.ArrayList;
import java.util.List;

public class FormularioProvaActivity extends AppCompatActivity {

    private FormularioProvaHelper helper;
    private Prova prova;
    private int chkId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_prova);

        helper = new FormularioProvaHelper(this);

        Intent intent = getIntent();
        prova = (Prova) intent.getSerializableExtra("prova");
        if(prova != null) {
            helper.preenche(prova);
        }

        final String idS = "R.id.formulario_prova_btn";

        final LinearLayout ll = (LinearLayout) findViewById(R.id.formulario_prova_ll);
        Button b = (Button) findViewById(R.id.formulario_prova_btnCheckBox);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText campoConteudo = (EditText) findViewById(R.id.formulario_prova_conteudo);
                CheckBox cb = new CheckBox(FormularioProvaActivity.this);
                cb.setId(++chkId);
                cb.setChecked(true);
                cb.setText(campoConteudo.getText());
                campoConteudo.setText("");
                ll.addView(cb);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario_prova, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menu_formulario_prova_ok:
                Prova prova = helper.pegaProva();
                ProvaDAO dao = new ProvaDAO(this);

                if(prova.getId() > 0) {
                    dao.Alterar(prova);
                    Toast.makeText(FormularioProvaActivity.this, "Prova editada", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dao.Inserir(prova);
                    Toast.makeText(FormularioProvaActivity.this, "Prova criada", Toast.LENGTH_SHORT).show();
                }

                dao.close();

                verificaSelecionados();

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<String> verificaSelecionados() {

        List<String> lst = new ArrayList<>();

        final LinearLayout ll = (LinearLayout) findViewById(R.id.formulario_prova_ll);

        for(int i = 0; i < ll.getChildCount(); i++) {
            CheckBox cb = (CheckBox) ll.getChildAt(i);
            if(cb.isChecked())
                lst.add(cb.getText().toString());
        }

        return lst;
    }
}
