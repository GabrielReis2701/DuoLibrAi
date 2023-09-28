package com.example.testemenuu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.testemenuu.database.CriarConexao;
import com.example.testemenuu.ui.config.ConfigFragment;
import com.example.testemenuu.ui.home.HomeFragment;
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
    Context context = this;

    CriarConexao conexao = new CriarConexao(context);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cl = findViewById(R.id.container_main); // Mova esta linha para depois de setContentView()

        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        setConexao();
    }
    public void setConexao(){
        conexao.conectar();
    }

    public void updateImage(int imageResId) {
        // Atualize o plano de fundo do ConstraintLayout

    }

    @Override
    public void onImageChange(int imageResId) {
        cl.setBackgroundResource(imageResId);    }
}