package com.example.rafae.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafae.agenda.modelo.Prova;

import org.w3c.dom.Text;

public class DetalhesProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);

        Intent intent = getIntent();
        Prova prova = (Prova) intent.getSerializableExtra("prova");

        TextView campoMateria = (TextView) findViewById(R.id.detalhes_prova_materia);
        campoMateria.setText(prova.getMateria());

        TextView campoData = (TextView) findViewById(R.id.detalhes_prova_data);
        campoData.setText(prova.getData());

        ListView listaTopicos = (ListView) findViewById(R.id.detalhes_prova_topico);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prova.getTopicos());
        //listaTopicos.setAdapter(adapter);

        Button novoConteudo = (Button) findViewById(R.id.novo_conteudo);
        novoConteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetalhesProvaActivity.this, "Novo conteudo", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
