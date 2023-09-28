package com.example.testemenuu.ui.config;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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

public class ConfigFragment extends Fragment {


    private FragmentConfigBinding binding;
    private HomeFragment homeBinding = new HomeFragment();
    OnImageChangeListener mCallback;

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
        ImageButton bt_acessivel = binding.btAcessivel;
        ConstraintLayout conf = binding.layoutConfig;


        bt_acessivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onImageChange(R.drawable.fundo_3);
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}