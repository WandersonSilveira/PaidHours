package com.example.paidhours;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paidhours.entidade.Curso;

import DAO.CursoDAO;

public class TelaCadastroCurso extends AppCompatActivity {

    EditText txtNome;
    EditText txtCargaHoraria;
    Button btnSalvar;
    Button btnExcluir;

    Integer codigoRecebido = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_curso);
        proInicializaComponentes();
        proCarregaInformacaoEdicao();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proAdicionarCurso();
            }
        });
    }

    private void proCarregaInformacaoEdicao(){
        Intent intent = getIntent();
        Curso curso = (Curso) intent.getSerializableExtra("CURSO");

        if(curso != null){
            txtNome.setText(curso.getNome());
            txtCargaHoraria.setText(curso.getCargaHoraria().toString());
            codigoRecebido = curso.getCodigo();

            btnExcluir.setVisibility(View.VISIBLE);
            btnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    proCarregaAlertDialogExclusao();
                }
            });
        }
    }

    private void proInicializaComponentes(){
        txtNome = findViewById(R.id.txtLNomeTelaCadastroCurso);
        txtCargaHoraria = findViewById(R.id.txtCargaHorariaTelaCadastroCurso);
        btnSalvar = findViewById(R.id.btnSalvarTelaCadastroCurso);
        btnExcluir = findViewById(R.id.btnExcluirTelaCadastroCurso);
    }

    private Boolean proConsisteDados(){
        Boolean resposta = true;

        if(txtNome.getText().toString().isEmpty()){
            resposta = false;
        }
        else if(txtCargaHoraria.getText().toString().isEmpty()){
            resposta = false;
        }

        return resposta;

    }

    private void proAdicionarCurso(){
        if(proConsisteDados()){

            String nome = txtNome.getText().toString();
            Integer cargaHoraria = Integer.parseInt(txtCargaHoraria.getText().toString());

            CursoDAO cursoDao = new CursoDAO(getBaseContext());

            //SE TIVER CÓDIGO FAZ UPDATE, SENÃO, FAZ INSERT
            if(codigoRecebido == null){
                if(cursoDao.proCadastrar(nome, cargaHoraria)){
                    Toast.makeText(getBaseContext(), "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getBaseContext(), "Erro ao cadastrar", Toast.LENGTH_LONG).show();
                }
            }
            else{
                if(cursoDao.proAlterar(codigoRecebido, nome, cargaHoraria, true)){
                    Toast.makeText(getBaseContext(), "Alterado com sucesso", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getBaseContext(), "Erro ao cadastrar", Toast.LENGTH_LONG).show();
                }
            }

            finish();
        }
        else {
            Toast.makeText(getBaseContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
        }
    }

    private void proCarregaAlertDialogExclusao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("EXCLUSÃO");
        builder.setMessage("Realmente deseja excluir este curso?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(proExcluirCurso()){
                    Toast.makeText(getApplicationContext(), "Curso excluido", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Curso não excluido", Toast.LENGTH_LONG).show();
            }
        });

        builder.create().show();
    }

    private Boolean proExcluirCurso(){
        CursoDAO cursoDao = new CursoDAO(getBaseContext());
        return cursoDao.proRemover(codigoRecebido);
    }

}