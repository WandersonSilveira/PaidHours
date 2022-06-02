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

import com.example.paidhours.entidade.Aluno;
import com.example.paidhours.entidade.Coordenador;
import com.example.paidhours.entidade.Curso;

import DAO.AlunoDAO;
import DAO.CursoDAO;

public class TelaCadastroAluno extends AppCompatActivity {

    EditText txtNome;
    EditText txtMatricula;
    Button btnSalvar;
    Button btnExcluir;

    Integer codigoRecebido = null;
    Curso curso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_aluno);

        proInicializaComponentes();
        proCarregaInformacao();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proAdicionarAluno();
            }
        });
    }

    private void proCarregaInformacao(){
        Intent intent = getIntent();
        curso = (Curso) intent.getSerializableExtra("CURSO");
        Aluno aluno = (Aluno) intent.getSerializableExtra("ALUNO");

        if(aluno != null){
            txtNome.setText(aluno.getNome());
            txtMatricula.setText(aluno.getMatricula().toString());
            codigoRecebido = aluno.getCodigo();

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
        txtNome = findViewById(R.id.txtLNomeTelaCadastroAluno);
        txtMatricula = findViewById(R.id.txtMatriculaTelaCadastroAluno);
        btnSalvar = findViewById(R.id.btnSalvarTelaCadastroAluno);
        btnExcluir = findViewById(R.id.btnExcluirTelaCadastroAluno);
    }

    private Boolean proConsisteDados(){
        Boolean resposta = true;

        if(txtNome.getText().toString().isEmpty()){
            resposta = false;
        }
        else if(txtMatricula.getText().toString().isEmpty()){
            resposta = false;
        }

        return resposta;

    }

    private void proAdicionarAluno(){
        if(proConsisteDados()){

            String nome = txtNome.getText().toString();
            Integer matricula = Integer.parseInt(txtMatricula.getText().toString());

            AlunoDAO alunoDao = new AlunoDAO(getBaseContext());

            //SE TIVER CÓDIGO FAZ UPDATE, SENÃO, FAZ INSERT
            if(codigoRecebido == null){
                if(alunoDao.proCadastrar(nome, matricula, curso.getCodigo())){
                    Toast.makeText(getBaseContext(), "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getBaseContext(), "Erro ao cadastrar", Toast.LENGTH_LONG).show();
                }
            }
            else{
                if(alunoDao.proAlterar(codigoRecebido, nome, matricula, true)){
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
        builder.setMessage("Realmente deseja excluir este aluno?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(proExcluirAluno()){
                    Toast.makeText(getApplicationContext(), "Aluno excluido", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Aluno não excluido", Toast.LENGTH_LONG).show();
            }
        });

        builder.create().show();
    }

    private Boolean proExcluirAluno(){
        AlunoDAO alunoDao = new AlunoDAO(getBaseContext());
        return alunoDao.proRemover(codigoRecebido);
    }
}