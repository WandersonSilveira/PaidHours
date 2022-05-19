package com.example.paidhours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.paidhours.entidade.Coordenador;

import java.io.Serializable;

public class TelaLogin extends AppCompatActivity {

    EditText txtLogin;
    EditText txtSenha;
    Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        proTiraSplashScreen();
        setContentView(R.layout.tela_login);
        proInicializaComponentes();

        //Event botao entrar
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proEntrar();
            }
        });
    }

    private void proTiraSplashScreen(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_PaidHours);
    }

    private void proInicializaComponentes(){
        txtLogin = findViewById(R.id.txtLogin);
        txtSenha= findViewById(R.id.txtSenha);
        btnEntrar= findViewById(R.id.btnEntrar);
    }

    private void proEntrar(){
        Coordenador coordenador = new Coordenador(null, txtLogin.getText().toString(), txtSenha.getText().toString(),0,"Carlos","carlos@unifacear.edu.br");

        Intent intent = new Intent(TelaLogin.this, TelaCurso.class);
        intent.putExtra("coordenador", (Serializable) coordenador);
        startActivity(intent);
    }
}