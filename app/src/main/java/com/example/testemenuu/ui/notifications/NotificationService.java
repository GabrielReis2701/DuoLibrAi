package com.example.testemenuu.ui.notifications;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.testemenuu.MainActivity;
import com.example.testemenuu.R;
import com.example.testemenuu.database.CriarConexao;
import com.example.testemenuu.database.DadosOpenHelper;
import com.example.testemenuu.database.entidades.Notificacao;
import com.example.testemenuu.database.entidades.NotificacaoRepo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int Your_X_SECS = 60;
    NotificacaoRepo notificacaoRepo;
    Notificacao notificacao;
    Notificacao notificacao2;
    CriarConexao conexao;
    String titulo;
    String mensagem;
    String imagem;
    String titulo2;
    String mensagem2;
    String imagem2;



    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        startTimer();

        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        SQLiteOpenHelper dbHelper = new DadosOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        notificacaoRepo = new NotificacaoRepo(db);
        notificacao = new Notificacao();
        notificacao2 = new Notificacao();
        conexao = new CriarConexao(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        stoptimertask();
        super.onDestroy();

    }
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 6000, Your_X_SECS * 1000); //
        //timer.schedule(timerTask, 5000,1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {

                        //TODO CALL NOTIFICATION FUNC
                        startForeground(1, Notificacao_cofig());

                    }
                });
            }
        };
    }

    public Notification Notificacao_cofig() {
        conexao.conectar();
        AdicionarMensagens();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        List<Notificacao> mensagens = new ArrayList<Notificacao>();
        mensagens = notificacaoRepo.buscarMensagens();
        Calendar agora = Calendar.getInstance();
        int hora = agora.get(Calendar.HOUR_OF_DAY);
        String mensagemN;
        String tituloN;

        if (hora < 12) {

            notificacaoRepo.Inserir(notificacao);
            notificacaoRepo.Excluir();
            mensagemN = mensagens.get(0).descricao;
            tituloN = mensagens.get(0).titulo;

        } else {

            notificacaoRepo.Inserir(notificacao2);
            notificacaoRepo.Excluir();
            mensagemN = mensagens.get(1).descricao;
            tituloN = mensagens.get(1).titulo;
        }


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        // Crie a notificação
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "volta";
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("2323", name, importance);
            channel.setDescription(description);
            // Registre o canal no sistema
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Bitmap icone = BitmapFactory.decodeResource(getResources(), R.drawable.mascote);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2323")
                .setContentTitle(tituloN)
                .setContentText(mensagemN)
                .setLargeIcon(icone)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.mascote_notificacao)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        // Publique a notificação
        return builder.build();
    }
    public void AdicionarMensagens(){
        conexao.conectar();
            titulo = "Bom Dia!!!  Hora da Lição";
            mensagem ="Vamos Acordar!! está na hora de voltar a aprender Libras";
            imagem = "mascote";
            notificacao.titulo=titulo;
            notificacao.descricao=mensagem;
            notificacao.imagem = imagem;
            if(!notificacaoRepo.buscarNotificacao(titulo,mensagem)){
                notificacaoRepo.InserirMensagem(notificacao);
            }

            titulo2 = "Mais Uma Lição";
            mensagem2 ="Vamos encerrar esse dia com mais uma lição de Libras";
            imagem2 = "mascote";

            notificacao2.titulo=titulo2;
            notificacao2.descricao=mensagem2;
            notificacao2.imagem = imagem2;
        if(!notificacaoRepo.buscarNotificacao(titulo2,mensagem2)){
            notificacaoRepo.InserirMensagem(notificacao2);
        }

    }

}