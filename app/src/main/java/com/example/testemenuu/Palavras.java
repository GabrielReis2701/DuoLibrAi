package com.example.testemenuu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.testemenuu.database.CriarConexao;
import com.example.testemenuu.database.DadosOpenHelper;
import com.example.testemenuu.database.entidades.NotificacaoRepo;

public class Palavras extends AppCompatActivity {
    private String palavraEscolhida = "", palavraFeita = "";
    Identificador identificador = new Identificador(this);
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap=null;
    private Button bt_voltar;
    private ConstraintLayout cl;
    private NotificacaoRepo notificacaoRepo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palavras);
        cl = findViewById(R.id.layout_palavra);

        CriarConexao conexao = new CriarConexao(this);
        conexao.conectar();
        SQLiteOpenHelper dbHelper = new DadosOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        notificacaoRepo = new NotificacaoRepo(db);
        updateImage();

        bt_voltar = findViewById(R.id.bt_voltarP);

        bt_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //volta para a activity anterior
            }
        });
    }

    //A função onclik está sendo chamada em todos os botões da activity_palavras
    //Exibi um AlertDialog Com o sinal que o usuario deve fazer
    public void onclick(View button) {
        palavraEscolhida = button.getTag().toString(); //Essa variavel recebe uma Tag que foi definida em cada botão com sua respctiva letra, que é carrega aqui como uma string
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setNeutralButton("Voltar",null)
                .setMessage("Faça o sinal de acordo com a imagem abaixo\nObs: tire a foto em um ambiente com claridade e sem objetos no fundo")
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tirarFoto();
                    }
                })
                .setView(createImageView(palavraEscolhida)); //chama a função createImageView passando a palavra que foi escolhida
        builder.create().show();

    }
    //Essa função é responsavel por procurar a imagem de acordo com a palavra escolhida, para ser exibida no AlertDialog
    private View createImageView(String letra) {
        ImageView imageView = new ImageView(this);
        String imageName = "palavra_" + letra.toLowerCase();
        int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        imageView.setImageResource(resId);
        return imageView;
    }

    //Função para carregar a camera do dispositivo e tirar uma foto
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
                AlertDialog.Builder janela = new AlertDialog.Builder(Palavras.this);
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
                palavraFeita = identificador.classificarImagem(imageBitmap,3);
                System.out.println(imageBitmap);
            }
        }
        comparador();
    }
    //Função para comparar a Palavra feita com a Palavra Escolhida
    private void comparador(){
        if (palavraFeita.equals(palavraEscolhida)) {
            mensagem(true);
        }else{
            mensagem(false);
        }
    }
    //Função para exibir uma Mensagem em Formato AlertDialog para informar se o usuario errou ou acertou, é chamada na função comparador()
    private void mensagem(boolean r){

        ImageView imageView= new ImageView(this);
        AlertDialog.Builder janela = new AlertDialog.Builder(Palavras.this);
        if(r==true){
            imageView.setImageResource(R.drawable.parabens);
        }else{
            imageView.setImageResource(R.drawable.errou);
        }
        janela.setView(imageView);
        janela.setMessage("Palavra Feita: " + palavraFeita + " Palavra Escolhida: " + palavraEscolhida);
        janela.setPositiveButton("Ok", null);
        janela.show();
    }
    public void updateImage(){
        String imageResId = notificacaoRepo.buscarPapel();
        int resId = getResources().getIdentifier(imageResId, "drawable", getPackageName());
        cl.setBackgroundResource(resId);
    }
}