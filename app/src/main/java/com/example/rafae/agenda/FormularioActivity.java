package com.example.rafae.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rafae.agenda.DAO.AlunoDAO;
import com.example.rafae.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;
    private Aluno aluno;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        aluno = (Aluno) intent.getSerializableExtra("aluno");
        if(aluno != null)
        {
            helper.Preenche(aluno);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:

                Aluno aluno = helper.pegaAluno();
                AlunoDAO dao = new AlunoDAO(this);

                if(aluno.getId() != null) {
                    dao.Alterar(aluno);
                    Toast.makeText(FormularioActivity.this, "Aluno editado.", Toast.LENGTH_SHORT).show();
                }
                else {
                    dao.Inserir(aluno);
                    Toast.makeText(FormularioActivity.this, "Aluno criado.", Toast.LENGTH_SHORT).show();
                }
                dao.close();


                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
