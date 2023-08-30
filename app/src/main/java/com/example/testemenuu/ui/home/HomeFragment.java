package com.example.testemenuu.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.testemenuu.Letras;
import com.example.testemenuu.R;
import com.example.testemenuu.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageButton ib_letras = binding.ibLetras;
        ImageButton ib_numeros = binding.ibNumeros;
        ImageButton ib_palavras = binding.ibPalavras;

        ib_letras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Letras.class);
                startActivity(i);
            }
        });

        ib_numeros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder janela = new AlertDialog.Builder(getContext());
                janela.setTitle("Esta ffuncionando");
                janela.setMessage("Botão funcionando");
                janela.setNeutralButton("ok",null);
                janela.create();
                janela.show();
            }
        });

        ib_palavras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder janela = new AlertDialog.Builder(getContext());
                janela.setTitle("Esta fffuncionando");
                janela.setMessage("Botão funcionando");
                janela.setNeutralButton("ok",null);
                janela.create();
                janela.show();
            }
        });


       // final TextView textView = binding.textHome;
       // homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}