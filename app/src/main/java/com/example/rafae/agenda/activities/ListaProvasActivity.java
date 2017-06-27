package com.example.rafae.agenda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rafae.agenda.DAO.ProvaDAO;
import com.example.rafae.agenda.R;
import com.example.rafae.agenda.adapter.ProvasAdapter;
import com.example.rafae.agenda.modelo.Prova;

import java.util.List;

public class ListaProvasActivity extends AppCompatActivity {

    private ListView listaProvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_provas);

        listaProvas = (ListView) findViewById(R.id.lista_provas);

        listaProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) listaProvas.getItemAtPosition(position);

                Intent formProva = new Intent(ListaProvasActivity.this, FormularioProvaActivity.class);
                formProva.putExtra("prova", prova);
                startActivity(formProva);
            }
        });

        Button novaProva = (Button) findViewById(R.id.nova_prova);
        novaProva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent formProva = new Intent(ListaProvasActivity.this, FormularioProvaActivity.class);
                startActivity(formProva);
            }
        });
        registerForContextMenu(listaProvas);
    }

    private void carregaLista() {
        ProvaDAO dao = new ProvaDAO(this);
        List<Prova> provas = dao.Buscar();
        dao.close();

        ProvasAdapter adapter = new ProvasAdapter(this, provas);
        listaProvas.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Prova prova = (Prova) listaProvas.getItemAtPosition(info.position);

        MenuItem deletar = menu.add("Remover");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ProvaDAO dao = new ProvaDAO(ListaProvasActivity.this);
                dao.Remover(prova);
                dao.close();
                carregaLista();

                Toast.makeText(ListaProvasActivity.this, "Prova removida", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_lista_provas, container, false);
//
//
//        List<String> topicosPortugues = Arrays.asList("A", "B", "C");
//        Prova provaPortugues = new Prova("Portugues", "25/05/2016", topicosPortugues);
//
//        List<String> topicosMatematica = Arrays.asList("1", "2", "3");
//        Prova provaMatematica = new Prova("Matematica", "26/05/2016", topicosMatematica);
//
//        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);
//
//        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, provas);
//
//        ListView lista = (ListView) view.findViewById(R.id.provas_lista);
//        lista.setAdapter(adapter);
//
//        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Prova prova = (Prova) parent.getItemAtPosition(position);
//                Toast.makeText(getContext(), "Clicou na prova de " + prova, Toast.LENGTH_SHORT).show();
//
//                ProvasActivity provasActivity = (ProvasActivity) getActivity();
//                provasActivity.selecionaProva(prova);
//            }
//        });
//
//        return view;
//    }
}
