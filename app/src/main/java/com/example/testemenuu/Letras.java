package com.example.testemenuu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.testemenuu.database.CriarConexao;
import com.example.testemenuu.database.DadosOpenHelper;
import com.example.testemenuu.database.entidades.NotificacaoRepo;
import com.example.testemenuu.ui.config.ConfigFragment;


public class Letras extends AppCompatActivity {
    String letraEscolhida = "", letraFeita = "";
    Identificador identificador = new Identificador(this);
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap=null;
    private Button bt_voltar;
    private ConstraintLayout cl;
    private NotificacaoRepo notificacaoRepo;

    public Letras(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letras);
        bt_voltar = findViewById(R.id.bt_voltar);
        cl = findViewById(R.id.layout_letra);
        CriarConexao conexao = new CriarConexao(this);
        conexao.conectar();
        SQLiteOpenHelper dbHelper = new DadosOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        notificacaoRepo = new NotificacaoRepo(db);
        updateImage();


        bt_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onclick(View button) {
        letraEscolhida = button.getTag().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setNeutralButton("Voltar",null)
                .setMessage("Faça o sinal de acordo com a imagem abaixo\nObs: tire a foto em um ambiente com claridade e sem objetos no fundo")
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tirarFoto();
                    }
                })
                .setView(createImageView(letraEscolhida));
        builder.create().show();

    }
    private View createImageView(String letra) {
        ImageView imageView = new ImageView(this);
        String imageName = "letra_" + letra.toLowerCase();
        int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        imageView.setImageResource(resId);
        return imageView;
    }

    private void tirarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            if (imageBitmap == null){
                AlertDialog.Builder janela = new AlertDialog.Builder(Letras.this);
                janela.setTitle("ERRO!!!");
                janela.setMessage("Você deve tirar a foto e salvar ela antes de retornar");
                janela.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tirarFoto();
                    }
                });
                janela.show();
            }else{
                letraFeita = identificador.classificarImagem(imageBitmap,1);
                System.out.println(imageBitmap);
            }
        }
        comparador();
    }
    private void comparador(){
        if (letraFeita.equals(letraEscolhida)) {
            mensagem(true);
        }else{
            mensagem(false);
        }
    }
    private void mensagem(boolean r){

        ImageView imageView= new ImageView(this);
        AlertDialog.Builder janela = new AlertDialog.Builder(Letras.this);
        if(r==true){
            imageView.setImageResource(R.drawable.parabens);
        }else{
            imageView.setImageResource(R.drawable.errou);
        }
        janela.setView(imageView);
        janela.setMessage("Letra Feita: " + letraFeita + " Letra Escolhida: " + letraEscolhida);
        janela.setPositiveButton("Ok", null);
        janela.show();
    }
    public void updateImage(){
        String imageResId = notificacaoRepo.buscarPapel();
        int resId = getResources().getIdentifier(imageResId, "drawable", getPackageName());
        cl.setBackgroundResource(resId);
    }

}
