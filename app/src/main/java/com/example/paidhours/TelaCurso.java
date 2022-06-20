package com.example.paidhours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.CornerPathEffect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;

import com.example.paidhours.entidade.Coordenador;
import com.example.paidhours.entidade.Curso;

import java.io.Serializable;
import java.util.List;

import DAO.CursoDAO;

public class TelaCurso extends AppCompatActivity {

    Button btnAdicionarCurso;
    EditText txtBuscaTelaCurso;

    RecyclerView recyclerView;
    TelaCursoAdapter telaCursoAdapter;

    Coordenador coordenador;

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
        proInicializaUsuario();
        proConfiguraPesquisa();
        proCarregarLista();
    }

    private void proInicializaUsuario(){
        Intent intent = getIntent();
        coordenador = (Coordenador) intent.getSerializableExtra("COORDENADOR");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        proCarregarLista();
    }

    private void proInicializaComponentes(){
        btnAdicionarCurso = findViewById(R.id.btnAdicionarTelaCurso);
        txtBuscaTelaCurso = findViewById(R.id.txtBuscaTelaCurso);

    }

    private void proConfiguraPesquisa(){
        txtBuscaTelaCurso.addTextChangedListener(new TextWatcher() {
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

    private void proAbrirTelaCadastroCurso(){
        Intent intent = new Intent(TelaCurso.this, TelaCadastroCurso.class);
        intent.putExtra("COORDENADOR", (Serializable) coordenador);
        startActivity(intent);
    }

    private void proCarregarListaFiltrada(){
        recyclerView = findViewById(R.id.listaCurso);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        CursoDAO cursoDAO = new CursoDAO(this);
        final List<Curso> listaCurso = cursoDAO.proListarFiltrado(coordenador.getCodigo(), String.valueOf(txtBuscaTelaCurso.getText()));
        telaCursoAdapter = new TelaCursoAdapter(listaCurso);
        recyclerView.setAdapter(telaCursoAdapter);
    }


    private void proCarregarLista(){
        recyclerView = findViewById(R.id.listaCurso);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        CursoDAO cursoDAO = new CursoDAO(this);
        final List<Curso> listaCurso = cursoDAO.proListar(coordenador.getCodigo());
        telaCursoAdapter = new TelaCursoAdapter(listaCurso);
        recyclerView.setAdapter(telaCursoAdapter);
    }
}