package com.example.paidhours;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.paidhours.entidade.Aluno;
import com.example.paidhours.entidade.Coordenador;
import com.example.paidhours.entidade.Curso;

import java.io.Serializable;
import java.util.List;

import DAO.AlunoDAO;
import DAO.CursoDAO;

public class TelaAluno extends AppCompatActivity {

    Button btnAdicionarAluno;

    RecyclerView recyclerView;
    TelaAlunoAdapter telaAlunoAdapter;

    Curso curso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_aluno);
        proInicializaComponentes();

        btnAdicionarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proAbrirTelaCadastroAluno();
            }
        });
        proInicializaUsuario();
        proCarregarLista();
    }

    private void proInicializaUsuario(){
        Intent intent = getIntent();
        curso = (Curso) intent.getSerializableExtra("CURSO");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        proCarregarLista();
    }

    private void proInicializaComponentes(){
        btnAdicionarAluno = findViewById(R.id.btnAdicionarTelaAluno);
    }
    private void proAbrirTelaCadastroAluno(){
        Intent intent = new Intent(TelaAluno.this, TelaCadastroAluno.class);
        intent.putExtra("CURSO", (Serializable) curso);
        startActivity(intent);
    }

    private void proCarregarLista(){
        recyclerView = findViewById(R.id.listaAluno);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        AlunoDAO alunoDAO = new AlunoDAO(this);
        final List<Aluno> listaAluno = alunoDAO.proListar(curso.getCodigo());
        telaAlunoAdapter = new TelaAlunoAdapter(listaAluno);
        recyclerView.setAdapter(telaAlunoAdapter);
    }

}