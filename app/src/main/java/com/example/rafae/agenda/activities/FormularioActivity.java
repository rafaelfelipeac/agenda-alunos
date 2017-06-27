package com.example.rafae.agenda.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rafae.agenda.DAO.AlunoDAO;
import com.example.rafae.agenda.helper.FormularioHelper;
import com.example.rafae.agenda.R;
import com.example.rafae.agenda.modelo.Aluno;
import com.example.rafae.agenda.tasks.InsereAlunoTask;

import java.io.File;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    public static final int CODIGO_SMS = 678;

    private FormularioHelper helper;
    private Aluno aluno;
    private String caminhoFoto;

    @TargetApi(Build.VERSION_CODES.M)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        //if(checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
            //requestPermissions(new String[] {Manifest.permission.RECEIVE_SMS}, CODIGO_SMS);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        aluno = (Aluno) intent.getSerializableExtra("aluno");
        if(aluno != null)
        {
            helper.Preenche(aluno);
        }

        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK)
        {
            helper.carregaImagem(caminhoFoto);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
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

                new InsereAlunoTask(aluno).execute();

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
