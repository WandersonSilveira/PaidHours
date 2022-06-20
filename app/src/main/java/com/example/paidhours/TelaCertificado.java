package com.example.paidhours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.paidhours.entidade.Aluno;
import com.example.paidhours.entidade.Certificado;
import com.example.paidhours.entidade.Curso;

import java.io.Serializable;
import java.util.List;

import DAO.AlunoDAO;
import DAO.CertificadoDAO;

public class TelaCertificado extends AppCompatActivity {

    Button btnAdicionarCertificado;
    EditText txtBuscaTelaCertificado;


    RecyclerView recyclerView;
    TelaCertificadoAdapter telaCertificadoAdapter;

    Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_certificado);
        proInicializaComponentes();

        btnAdicionarCertificado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proAbrirTelaCadastroCertificado();
            }
        });
        proInicializaUsuario();
        proConfiguraPesquisa();
        proCarregarLista();
    }

    private void proInicializaUsuario(){
        Intent intent = getIntent();
        aluno = (Aluno) intent.getSerializableExtra("ALUNO");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        proCarregarLista();
    }

    private void proInicializaComponentes(){
        btnAdicionarCertificado = findViewById(R.id.btnAdicionarTelaCertificado);
        txtBuscaTelaCertificado = findViewById(R.id.txtBuscaTelaCertificado);
    }

    private void proConfiguraPesquisa(){
        txtBuscaTelaCertificado.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    proCarregarListaFiltrada();
                }
                else if(s.length() == 0){
                    proCarregarLista();
                }
            }
        });
    }

    private void proAbrirTelaCadastroCertificado(){
        Intent intent = new Intent(TelaCertificado.this, TelaCadastroCertificado.class);
        intent.putExtra("ALUNO", (Serializable) aluno);
        startActivity(intent);
    }

    private void proCarregarListaFiltrada(){
        recyclerView = findViewById(R.id.listaCertificado);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        CertificadoDAO certificadoDAO = new CertificadoDAO(this);
        final List<Certificado> listaCertificado = certificadoDAO.proListarFiltrado(aluno.getCodigo(), String.valueOf(txtBuscaTelaCertificado.getText()));
        telaCertificadoAdapter = new TelaCertificadoAdapter(listaCertificado);
        recyclerView.setAdapter(telaCertificadoAdapter);
    }

    private void proCarregarLista(){
        recyclerView = findViewById(R.id.listaCertificado);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        CertificadoDAO certificadoDAO = new CertificadoDAO(this);
        final List<Certificado> listaCertificado = certificadoDAO.proListar(aluno.getCodigo());
        telaCertificadoAdapter = new TelaCertificadoAdapter(listaCertificado);
        recyclerView.setAdapter(telaCertificadoAdapter);
    }


}