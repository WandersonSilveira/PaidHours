package com.example.paidhours;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TelaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        proTiraSplashScreen();
        setContentView(R.layout.tela_principal);
    }

    private void proTiraSplashScreen(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_PaidHours);
    }
}