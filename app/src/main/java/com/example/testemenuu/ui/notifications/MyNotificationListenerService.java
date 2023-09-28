package com.example.testemenuu.ui.notifications;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.example.testemenuu.database.CriarConexao;
import com.example.testemenuu.database.entidades.Notificacao;

import java.sql.SQLOutput;

public class MyNotificationListenerService extends NotificationListenerService {
    String title="";
    String text="";

    long time=0;

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn.getPackageName().equals("com.example.testemenuu")) {
            // Aqui você pode acessar as informações da notificação
            Notification notification = sbn.getNotification();
            Bundle extras = notification.extras;
            title = extras.getString(Notification.EXTRA_TITLE);
            text = extras.getString(Notification.EXTRA_TEXT);
            time = sbn.getPostTime();
            Log.i("notificacao",title + " " + text + " " + time);
            // Crie um novo objeto NotificationItem e adicione-o à sua lista
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Implemente este método se você quiser fazer algo quando uma notificação for removida
    }
}
