package com.example.testemenuu.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testemenuu.R;
import com.example.testemenuu.database.entidades.Notificacao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<Notificacao> notificationList;

    public NotificationAdapter(ArrayList<Notificacao> notificationList) {
        this.notificationList = notificationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if((notificationList!=null) && (notificationList.size()>0)){
            Notificacao item = notificationList.get(position);
            holder.title.setText(item.titulo);
            holder.text.setText(item.descricao);
            Date date = new Date(item.hora);
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String timeString = formatter.format(date);
            holder.time.setText(timeString);
        }

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView text;
        TextView time;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_titulo);
            text = itemView.findViewById(R.id.tv_texto);
            time = itemView.findViewById(R.id.tv_tempo);
        }
    }
}