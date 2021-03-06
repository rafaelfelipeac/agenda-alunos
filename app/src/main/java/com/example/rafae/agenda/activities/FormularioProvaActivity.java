package com.example.rafae.agenda.activities;

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
import android.widget.Toast;

import com.example.rafae.agenda.DAO.ProvaDAO;
import com.example.rafae.agenda.helper.FormularioProvaHelper;
import com.example.rafae.agenda.R;
import com.example.rafae.agenda.modelo.Prova;

public class FormularioProvaActivity extends AppCompatActivity {

    private FormularioProvaHelper helper;
    private Prova prova;

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

        final LinearLayout ll = (LinearLayout) findViewById(R.id.formulario_prova_ll);
        Button b = (Button) findViewById(R.id.formulario_prova_btnCheckBox);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText campoConteudo = (EditText) findViewById(R.id.formulario_prova_conteudo);
                CheckBox cb = new CheckBox(FormularioProvaActivity.this);
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

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
