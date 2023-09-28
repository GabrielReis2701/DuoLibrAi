package com.example.testemenuu.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;

import androidx.lifecycle.ViewModelProvider;

import com.example.testemenuu.Letras;

import com.example.testemenuu.Numeros;
import com.example.testemenuu.Palavras;
import com.example.testemenuu.R;
import com.example.testemenuu.databinding.FragmentHomeBinding;
import com.example.testemenuu.ui.notifications.NotificationService;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageButton ib_letras = binding.ibLetras;
        ImageButton ib_numeros = binding.ibNumeros;
        ImageButton ib_palavras = binding.ibPalavras;

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Recupera a imagem do argumento
        Bundle args = getArguments();
        if (args != null) {
            int image = args.getInt("image", 0);
            // Atualiza a imagem
            updateImage(image);
        }

        //Chama a atividade das Letras
        ib_letras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Letras.class);
                startActivity(i);

            }
        });

        //Chama a atividade dos Numeros
        ib_numeros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Numeros.class);
                startActivity(i);
            }
        });

        //Chama a atividade das Palavras
        ib_palavras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Palavras.class);
                startActivity(i);
            }
        });
        return root;
    }
    public void updateImage(int imageResId) {
        // Atualize o plano de fundo do ConstraintLayout
        binding.fragmentohome.setBackgroundResource(imageResId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}