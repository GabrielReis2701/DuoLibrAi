package com.example.testemenuu.ui.config;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.testemenuu.Letras;
import com.example.testemenuu.MainActivity;
import com.example.testemenuu.R;
import com.example.testemenuu.database.DadosOpenHelper;
import com.example.testemenuu.database.entidades.NotificacaoRepo;
import com.example.testemenuu.databinding.FragmentConfigBinding;
import com.example.testemenuu.databinding.FragmentHomeBinding;
import com.example.testemenuu.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class ConfigFragment extends Fragment {


    private FragmentConfigBinding binding;
    private HomeFragment homeBinding = new HomeFragment();
    OnImageChangeListener mCallback;
    private Spinner spinner;
    private List<SpinnerItem> spinnerItems = new ArrayList<>();
    private NotificacaoRepo notificacaoRepo;

    // Interface para ser implementada pela Activity
    public interface OnImageChangeListener {
        void onImageChange(String imageResId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnImageChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " deve implementar OnImageChangeListener");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConfigViewModel configViewModel =
                new ViewModelProvider(this).get(ConfigViewModel.class);

        binding = FragmentConfigBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SQLiteOpenHelper dbHelper = new DadosOpenHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        notificacaoRepo = new NotificacaoRepo(db);
        Button bt_acessivel = binding.btAcessivel;
        spinner = binding.spinner;
        CarregarItensSpinner();
        ArrayAdapter<SpinnerItem> spinnerAdapter = new ArrayAdapter<SpinnerItem>(getContext(), android.R.layout.simple_spinner_item, spinnerItems) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.BLACK); // Altere para a cor desejada
                return view;
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        bt_acessivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpinnerItem selectedItem = (SpinnerItem) spinner.getSelectedItem();
                String imageResId = selectedItem.getNome();
                notificacaoRepo.ExcluirPapel();
                if (imageResId.equals("Papel de Parede 1")){
                    mCallback.onImageChange("fundo_main");
                    notificacaoRepo.InserirPapel("fundo_main");
                }else if(imageResId.equals("Papel de Parede 2")){
                    mCallback.onImageChange("fundo_2");
                    notificacaoRepo.InserirPapel("fundo_2");
                }else{
                    mCallback.onImageChange("fundo_3");
                    notificacaoRepo.InserirPapel("fundo_3");
                }


            }
        });

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void CarregarItensSpinner(){
        spinnerItems.add(new SpinnerItem("Papel de Parede 1", getResources().getIdentifier("fundo_main", "drawable", getActivity().getPackageName())));
        spinnerItems.add(new SpinnerItem("Papel de Parede 2", getResources().getIdentifier("fundo_2", "drawable", getActivity().getPackageName())));
        spinnerItems.add(new SpinnerItem("Papel de Parede 3", getResources().getIdentifier("fundo_3", "drawable", getActivity().getPackageName())));
    }
}