package com.example.paidhours;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paidhours.entidade.Coordenador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import DAO.CoordenadorDAO;

public class TelaLogin extends AppCompatActivity {

    EditText txtLogin;
    EditText txtSenha;
    Button btnEntrar;
    Button btnCriarConta;

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
                proValidaLogin();
            }
        });
        //Event botao criar conta
        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proCriarConta();
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
        btnCriarConta= findViewById(R.id.btnCriarConta);
    }

    private void proCriarConta(){
        Intent intent = new Intent(TelaLogin.this, TelaCadastroCoordenador.class);
        startActivity(intent);
    }

    private void proValidaLogin(){
        Boolean mensagem = true;
        CoordenadorDAO coordenadorDAO = new CoordenadorDAO(getBaseContext());

        List<Coordenador> listaCoordenadores = new ArrayList<>();
        listaCoordenadores = coordenadorDAO.proListar();

        //Verifica login
        for(Coordenador coordenador : listaCoordenadores){
            if(txtLogin.getText().toString().equals(coordenador.getLogin()) && txtSenha.getText().toString().equals(coordenador.getSenha())){
                mensagem = false;
                proEntrar(coordenador);
                break;
            }
        }

        //Informa caso não ache
        if(mensagem){
            Toast.makeText(getBaseContext(), "Login incorreto!", Toast.LENGTH_LONG).show();
        }
    }

    private void proEntrar(Coordenador coordenador){
        Intent intent = new Intent(TelaLogin.this, TelaCurso.class);
        intent.putExtra("COORDENADOR", (Serializable) coordenador);
        startActivity(intent);
    }
}