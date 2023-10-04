package com.example.testemenuu;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.example.testemenuu.database.CriarConexao;
import com.example.testemenuu.database.DadosOpenHelper;
import com.example.testemenuu.database.entidades.NotificacaoRepo;
import com.example.testemenuu.ui.config.ConfigFragment;
import com.example.testemenuu.ui.notifications.NotificationService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.testemenuu.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ConfigFragment.OnImageChangeListener {

    private ActivityMainBinding binding;
    private ConstraintLayout cl;
    private NotificacaoRepo notificacaoRepo;
    Context context = this;

    CriarConexao conexao = new CriarConexao(context);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
        SQLiteOpenHelper dbHelper = new DadosOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        notificacaoRepo = new NotificacaoRepo(db);
        cl = findViewById(R.id.container_main);
        setConexao();
        mudarImagem();



         // Mova esta linha para depois de setContentView()




        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }
    public void setConexao(){
        conexao.conectar();
    }

    public void mudarImagem(){
        String imageResId = notificacaoRepo.buscarPapel();
        int resId = getResources().getIdentifier(imageResId, "drawable", getPackageName());
        cl.setBackgroundResource(resId);
    }
    @Override
    public void onImageChange(String imageResId) {
        int resId = getResources().getIdentifier(imageResId, "drawable", getPackageName());
        cl.setBackgroundResource(resId);

    }
}