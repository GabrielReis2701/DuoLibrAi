package com.example.testemenuu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Inicie sua Intent aqui
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        }, 4000);  // Atraso de 5 segundos


    }
}