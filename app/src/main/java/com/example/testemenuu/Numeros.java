package com.example.testemenuu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Numeros extends AppCompatActivity {
    private String numeroEscolhido="", numeroFeito="";
    Identificador identificador = new Identificador(this);
    private Bitmap imageBitmap=null;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button bt_voltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeros);

        bt_voltar = findViewById(R.id.bt_voltarN);

        bt_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }



    public void onclick(View button) {
        numeroEscolhido = button.getTag().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Faça o sinal de acordo com a imagem abaixo")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tirarFoto();
                    }
                })
                .setView(createImageView(numeroEscolhido));
        builder.create().show();

    }
    private View createImageView(String letra) {
        ImageView imageView = new ImageView(this);
        String imageName = "numero_" + letra.toLowerCase();
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
            if (imageBitmap==null){
                AlertDialog.Builder janela = new AlertDialog.Builder(Numeros.this);
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
                numeroFeito = identificador.classificarImagem(imageBitmap,"numeros");
            }
        }
        comparador();
    }
    private void comparador(){
        ImageView imageView= new ImageView(this);
        if (numeroFeito.equals(numeroEscolhido)) {
            AlertDialog.Builder janela1 = new AlertDialog.Builder(Numeros.this);
            imageView.setImageResource(R.drawable.parabens);
            janela1.setView(imageView);
            janela1.setMessage("Numero Feito: " + numeroFeito + " Numero Escolhido: " + numeroEscolhido);
            janela1.setNeutralButton("ok", null);
            janela1.show();
        } else {
            AlertDialog.Builder janela2 = new AlertDialog.Builder(Numeros.this);
            imageView.setImageResource(R.drawable.errou);
            janela2.setView(imageView);
            janela2.setMessage("Numero Feito: " + numeroFeito + " Letra Escolhido: " + numeroEscolhido);
            janela2.setNeutralButton("ok", null);
            janela2.show();
        }
    }
}