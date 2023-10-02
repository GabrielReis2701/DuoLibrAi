package com.example.testemenuu.ui.config;

public class SpinnerItem {
    private String nome;
    private int imageResId;

    public SpinnerItem(String nome, int imageResId) {
        this.nome = nome;
        this.imageResId = imageResId;
    }

    public String getNome() {
        return nome;
    }

    public int getImageResId() {
        return imageResId;
    }

    // O ArrayAdapter usará este método para obter o texto a ser exibido
    @Override
    public String toString() {
        return nome;
    }
}

