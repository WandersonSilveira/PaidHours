package com.example.paidhours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.paidhours.entidade.Coordenador;

import java.io.Serializable;

public class TelaCurso extends AppCompatActivity {

    Button btnAdicionarCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_curso);
        proInicializaComponentes();

        btnAdicionarCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proAbrirTelaCadastroCurso();
            }
        });
    }

    private void proInicializaComponentes(){
        btnAdicionarCurso = findViewById(R.id.btnAdicionarTelaCurso);
    }

    private void proAbrirTelaCadastroCurso(){
        Intent intent = new Intent(TelaCurso.this, TelaCadastroCurso.class);
        startActivity(intent);
    }
}