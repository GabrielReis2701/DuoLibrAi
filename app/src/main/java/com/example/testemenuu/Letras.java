package com.example.testemenuu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class Letras extends AppCompatActivity {
    String letraEscolhida = "", letraFeita = "";
    Identificador identificador = new Identificador(this);
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letras);
    }

    public void onclick(View button) {
        letraEscolhida = button.getTag().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Faça o sinal de acordo com a imagem abaixo")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
            letraFeita = identificador.classificarImagem(imageBitmap);
        }
        comparador();
    }
    private void comparador(){
        ImageView imageView= new ImageView(this);
        if (letraFeita.equals(letraEscolhida)) {
            AlertDialog.Builder janela1 = new AlertDialog.Builder(Letras.this);
            imageView.setImageResource(R.drawable.parabens);
            janela1.setView(imageView);
            janela1.setMessage("Letra Feita: " + letraFeita + " Letra Escolhida: " + letraEscolhida);
            janela1.setNeutralButton("ok", null);
            janela1.show();
        } else {
            AlertDialog.Builder janela2 = new AlertDialog.Builder(Letras.this);
            imageView.setImageResource(R.drawable.errou);
            janela2.setView(imageView);
            janela2.setMessage("Letra Feita: " + letraFeita + " Letra Escolhida: " + letraEscolhida);
            janela2.setNeutralButton("ok", null);
            janela2.show();
        }
    }

}
