package com.example.testemenuu.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testemenuu.R;
import com.example.testemenuu.database.DadosOpenHelper;
import com.example.testemenuu.database.entidades.Notificacao;
import com.example.testemenuu.database.entidades.NotificacaoRepo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<Notificacao> notificationList;
    private Context context;
    private NotificacaoRepo notificacaoRepo;

    public NotificationAdapter(ArrayList<Notificacao> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context =context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if((notificationList!=null) && (notificationList.size()>0)){
            Notificacao item = notificationList.get(position);
            holder.title.setText(item.titulo);
            holder.text.setText(item.descricao);
            int resourceId = context.getResources().getIdentifier(item.imagem, "drawable", context.getPackageName());
            Drawable drawable = context.getResources().getDrawable(resourceId);
            holder.imagem.setImageDrawable(drawable);

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && position < notificationList.size()) {
                        SQLiteOpenHelper dbHelper = new DadosOpenHelper(context);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        notificacaoRepo = new NotificacaoRepo(db);

                        int itemId = notificationList.get(position).codigo;
                        notificationList.remove(position);

                        notifyItemRemoved(position);
                        notificacaoRepo.ExcluirItem(itemId);
                    }

                    return true;
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView text;
        ImageView imagem;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_titulo);
            text = itemView.findViewById(R.id.tv_texto);
            imagem= itemView.findViewById(R.id.iv_icone_item);
        }
    }
}