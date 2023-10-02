package com.example.testemenuu.ui.config;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.testemenuu.Letras;
import com.example.testemenuu.MainActivity;
import com.example.testemenuu.R;
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

    // Interface para ser implementada pela Activity
    public interface OnImageChangeListener {
        void onImageChange(int imageResId);
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

    // Método para passar a imagem para a Activity
    public void updateImage(int imageResId) {
        mCallback.onImageChange(imageResId);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConfigViewModel configViewModel =
                new ViewModelProvider(this).get(ConfigViewModel.class);

        binding = FragmentConfigBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button bt_acessivel = binding.btAcessivel;
        spinner = binding.spinner;
        CarregarSpinner();
        ArrayAdapter<SpinnerItem> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        bt_acessivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpinnerItem selectedItem = (SpinnerItem) spinner.getSelectedItem();
                int imageResId = selectedItem.getImageResId();
                mCallback.onImageChange(imageResId);
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void CarregarSpinner(){
        spinnerItems.add(new SpinnerItem("Papel de Parede 1", getResources().getIdentifier("fundo_main", "drawable", getActivity().getPackageName())));
        spinnerItems.add(new SpinnerItem("Papel de Parede 2", getResources().getIdentifier("fundo_2", "drawable", getActivity().getPackageName())));
        spinnerItems.add(new SpinnerItem("Papel de Parede 3", getResources().getIdentifier("fundo_3", "drawable", getActivity().getPackageName())));
    }
}