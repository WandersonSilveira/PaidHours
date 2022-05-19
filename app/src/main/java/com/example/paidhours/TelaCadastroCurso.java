package com.example.paidhours;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import DAO.CursoDAO;

public class TelaCadastroCurso extends AppCompatActivity {

    EditText txtNome;
    EditText txtCargaHoraria;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_curso);
        proInicializaComponentes();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proAdicionarCurso();
            }
        });
    }

    private void proInicializaComponentes(){
        txtNome = findViewById(R.id.txtLNomeTelaCadastroCurso);
        txtCargaHoraria = findViewById(R.id.txtCargaHorariaTelaCadastroCurso);
        btnSalvar = findViewById(R.id.btnSalvarTelaCadastroCurso);
    }

    private void proAdicionarCurso(){
        String nome = txtNome.getText().toString();
        Integer cargaHoraria = Integer.parseInt(txtCargaHoraria.getText().toString());

        CursoDAO cursoDao = new CursoDAO(getBaseContext());

        if(cursoDao.proCadastrar(nome, cargaHoraria)){
            Toast.makeText(getBaseContext(), "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getBaseContext(), "Erro ao cadastrar", Toast.LENGTH_LONG).show();
        }

        finish();
    }

}