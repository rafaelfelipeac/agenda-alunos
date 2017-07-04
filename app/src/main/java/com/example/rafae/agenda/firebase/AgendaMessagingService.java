package com.example.rafae.agenda.firebase;

import android.util.Log;

import com.example.rafae.agenda.DAO.AlunoDAO;
import com.example.rafae.agenda.events.AtualizarListaAlunoEvent;
import com.example.rafae.agenda.modelo.Aluno;
import com.example.rafae.dto.AlunoSync;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Rafael Felipe on 03/07/2017.
 */

public class AgendaMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> mensagem = remoteMessage.getData();
        Log.i("mensagem recebida", String.valueOf(mensagem));

        converteToAluno(mensagem);
    }

    private void converteToAluno(Map<String, String> mensagem) {
        String chaveDeAcesso = "alunoSync";

        if(mensagem.containsKey(chaveDeAcesso)) {
            String json = mensagem.get(chaveDeAcesso);
            ObjectMapper mapper = new ObjectMapper();

            try {
                AlunoSync alunoSync = mapper.readValue(json, AlunoSync.class);

                AlunoDAO alunoDAO = new AlunoDAO(this);
                alunoDAO.Sync(alunoSync.getAlunos());
                alunoDAO.close();

                EventBus eventBus = EventBus.getDefault();
                eventBus.post(new AtualizarListaAlunoEvent());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
