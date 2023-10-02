package com.example.testemenuu.ui.notifications;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testemenuu.MainActivity;
import com.example.testemenuu.database.CriarConexao;
import com.example.testemenuu.database.DadosOpenHelper;
import com.example.testemenuu.database.entidades.Notificacao;
import com.example.testemenuu.database.entidades.NotificacaoRepo;
import com.example.testemenuu.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private RecyclerView rv_list;
    private NotificationAdapter notificationAdapter;
    private Notificacao notificacao;
    private NotificacaoRepo notificacaoRepo;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        CriarConexao conexao = new CriarConexao(getContext());
        conexao.conectar();

        SQLiteOpenHelper dbHelper = new DadosOpenHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        notificacaoRepo = new NotificacaoRepo(db);


        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rv_list = binding.rvNotificacao;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_list.setLayoutManager(linearLayoutManager);
        notificacao = new Notificacao();
        List<Notificacao> notif = notificacaoRepo.buscarTodos();
        notificationAdapter = new NotificationAdapter((ArrayList<Notificacao>) notif,getContext());

        rv_list.setAdapter(notificationAdapter);




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}