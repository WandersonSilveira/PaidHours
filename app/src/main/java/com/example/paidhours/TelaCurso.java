package com.example.paidhours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.CornerPathEffect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;

import com.example.paidhours.entidade.Coordenador;
import com.example.paidhours.entidade.Curso;

import java.io.Serializable;
import java.util.List;

import DAO.CursoDAO;

public class TelaCurso extends AppCompatActivity {

    Button btnAdicionarCurso;

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
    }

    private void proAbrirTelaCadastroCurso(){
        Intent intent = new Intent(TelaCurso.this, TelaCadastroCurso.class);
        intent.putExtra("COORDENADOR", (Serializable) coordenador);
        startActivity(intent);
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