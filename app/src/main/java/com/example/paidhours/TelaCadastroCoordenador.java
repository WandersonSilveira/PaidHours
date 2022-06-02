package com.example.paidhours;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paidhours.entidade.Coordenador;

import DAO.CoordenadorDAO;
import DAO.CursoDAO;

public class TelaCadastroCoordenador extends AppCompatActivity {

    EditText txtNome;
    EditText txtEmail;
    EditText txtLogin;
    EditText txtSenha;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_coordenador);
        proInicializaComponentes();
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proAdicionarCoordenador();
            }
        });
    }

    private void proInicializaComponentes(){
        txtNome = findViewById(R.id.txtLNomeTelaCadastroCoordenador);
        txtEmail = findViewById(R.id.txtEmailTelaCadastroCoordenador);
        txtLogin = findViewById(R.id.txtLoginTelaCadastroCoordenador);
        txtSenha = findViewById(R.id.txtSenhaTelaCadastroCoordenador);
        btnSalvar = findViewById(R.id.btnSalvarTelaCadastroCoordenador);
    }

    private Boolean proConsisteDados(){
        Boolean resposta = true;

        if(txtNome.getText().toString().isEmpty()){
            resposta = false;
        }
        else if(txtEmail.getText().toString().isEmpty()){
            resposta = false;
        }
        else if(txtLogin.getText().toString().isEmpty()){
            resposta = false;
        }
        else if(txtSenha.getText().toString().isEmpty()){
            resposta = false;
        }

        return resposta;
    }

    private void proAdicionarCoordenador(){
        if(proConsisteDados()){

            String nome = txtNome.getText().toString();
            String email = txtEmail.getText().toString();
            String login = txtLogin.getText().toString();
            String senha = txtSenha.getText().toString();

            CoordenadorDAO coordenadorDao = new CoordenadorDAO(getBaseContext());

            if(coordenadorDao.proCadastrar(nome, email, login, senha)){
                Toast.makeText(getBaseContext(), "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                Toast.makeText(getBaseContext(), "Erro ao cadastrar", Toast.LENGTH_LONG).show();
            }

        }
        else {
            Toast.makeText(getBaseContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
        }
    }
}