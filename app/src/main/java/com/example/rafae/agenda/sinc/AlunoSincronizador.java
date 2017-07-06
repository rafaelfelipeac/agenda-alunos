package com.example.rafae.agenda.sinc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.rafae.agenda.DAO.AlunoDAO;
import com.example.rafae.agenda.activities.ListaAlunosActivity;
import com.example.rafae.agenda.events.AtualizarListaAlunoEvent;
import com.example.rafae.agenda.modelo.Aluno;
import com.example.rafae.agenda.preferences.PreferencesAluno;
import com.example.rafae.agenda.retrofit.RetrofitInitiate;
import com.example.rafae.dto.AlunoSync;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoSincronizador {
    private final Context context;
    private EventBus bus = EventBus.getDefault();
    private PreferencesAluno preferences;

    public AlunoSincronizador(Context context) {
        this.context = context;

        preferences = new PreferencesAluno(context);
    }

    public void buscaTodos() {
        if(preferences.temVersao()) {
            buscaNovos();
        }
        else {
            buscaAlunos();
        }
    }

    private void buscaNovos() {
        String versao = preferences.getVersao();
        Call<AlunoSync> call = new RetrofitInitiate().getAlunoService().novos(versao);
        call.enqueue(buscaAlunosCallback());
    }

    private void buscaAlunos() {
        Call<AlunoSync> call = new RetrofitInitiate().getAlunoService().lista();
        call.enqueue(buscaAlunosCallback());
    }

    @NonNull
    private Callback<AlunoSync> buscaAlunosCallback() {
        return new Callback<AlunoSync>() {
            @Override
            public void onResponse(Call<AlunoSync> call, Response<AlunoSync> response) {
                AlunoSync alunoSync = response.body();
                String versao = alunoSync.getmomentoDaUltimaModificacao();


                preferences.salvarVersao(versao);


                List<Aluno> alunos = alunoSync.getAlunos();
                AlunoDAO dao = new AlunoDAO(context);

                dao.Sync(alunos);

                dao.close();

                Log.i("versao", preferences.getVersao());

                bus.post(new AtualizarListaAlunoEvent());
            }

            @Override
            public void onFailure(Call<AlunoSync> call, Throwable t) {
                Log.e("onFailure", t.getMessage());

                bus.post(new AtualizarListaAlunoEvent());
            }
        };
    }

    public void sincronizaAlunosInternos() {
        final AlunoDAO dao = new AlunoDAO(context);

        final List<Aluno> alunos = dao.listaNaoSincronizados();

        Call<AlunoSync> call = new RetrofitInitiate().getAlunoService().atualiza(alunos);
        call.enqueue(new Callback<AlunoSync>() {
            @Override
            public void onResponse(Call<AlunoSync> call, Response<AlunoSync> response) {
                AlunoSync alunoSync = response.body();
                dao.Sync(alunoSync.getAlunos());
                dao.close();
            }

            @Override
            public void onFailure(Call<AlunoSync> call, Throwable t) {

            }
        });
    }

    public void deleta(final Aluno aluno) {
        Call<Void> call = new RetrofitInitiate().getAlunoService().remove(aluno.getId());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                AlunoDAO dao = new AlunoDAO(context);
                dao.Remover(aluno);
                dao.close();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}