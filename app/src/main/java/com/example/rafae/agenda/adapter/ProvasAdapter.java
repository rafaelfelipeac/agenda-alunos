package com.example.rafae.agenda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rafae.agenda.R;
import com.example.rafae.agenda.modelo.Prova;

import java.util.List;

/**
 * Created by rafae on 12/10/2016.
 */

public class ProvasAdapter extends BaseAdapter {
    private final List<Prova> provas;
    private final Context context;

    public ProvasAdapter(Context context, List<Prova> provas) {
        this.provas = provas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return provas.size();
    }

    @Override
    public Object getItem(int position) {
        return provas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return provas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Prova prova = provas.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if(convertView == null) {
            view = inflater.inflate(R.layout.item_list_provas, parent, false);
        }

        TextView campoMateria = (TextView) view.findViewById(R.id.item_list_nome);
        campoMateria.setText(prova.getMateria());

        TextView campoData = (TextView) view.findViewById(R.id.item_list_data);
        campoData.setText(prova.getData());

        // campoConteudos

        return view;

    }
}
