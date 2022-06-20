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
import android.widget.TextView;

import com.example.paidhours.entidade.Aluno;
import com.example.paidhours.entidade.Coordenador;
import com.example.paidhours.entidade.Curso;

import java.io.Serializable;
import java.util.List;

import DAO.AlunoDAO;
import DAO.CursoDAO;

public class TelaAluno extends AppCompatActivity {

    Button btnAdicionarAluno;
    EditText txtBuscaTelaAluno;

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
        proConfiguraPesquisa();
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
        txtBuscaTelaAluno = findViewById(R.id.txtBuscaTelaAluno);
    }

    private void proAbrirTelaCadastroAluno(){
        Intent intent = new Intent(TelaAluno.this, TelaCadastroAluno.class);
        intent.putExtra("CURSO", (Serializable) curso);
        startActivity(intent);
    }

    private void proConfiguraPesquisa(){
        txtBuscaTelaAluno.addTextChangedListener(new TextWatcher() {
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

    private void proCarregarListaFiltrada(){
        recyclerView = findViewById(R.id.listaAluno);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        AlunoDAO alunoDAO = new AlunoDAO(this);
        final List<Aluno> listaAluno = alunoDAO.proListarFiltrado(curso.getCodigo(), String.valueOf(txtBuscaTelaAluno.getText()));
        telaAlunoAdapter = new TelaAlunoAdapter(listaAluno);
        recyclerView.setAdapter(telaAlunoAdapter);
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