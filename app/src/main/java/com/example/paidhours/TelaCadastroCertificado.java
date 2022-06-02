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
import com.example.paidhours.entidade.Certificado;
import com.example.paidhours.entidade.Curso;

import DAO.AlunoDAO;
import DAO.CertificadoDAO;

public class TelaCadastroCertificado extends AppCompatActivity {

    EditText txtNome;
    EditText txtDescricao;
    EditText txtCargaHoraria;
    Button btnSalvar;
    Button btnExcluir;

    Integer codigoRecebido = null;
    Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_certificado);

        proInicializaComponentes();
        proCarregaInformacao();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proAdicionarCertificado();
            }
        });
    }

    private void proCarregaInformacao(){
        Intent intent = getIntent();
        aluno = (Aluno) intent.getSerializableExtra("ALUNO");
        Certificado certificado = (Certificado) intent.getSerializableExtra("CERTIFICADO");

        if(certificado != null){
            txtNome.setText(certificado.getNome());
            txtDescricao.setText(certificado.getDescricao());
            txtCargaHoraria.setText(certificado.getCargaHoraria().toString());
            codigoRecebido = certificado.getCodigo();

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
        txtNome = findViewById(R.id.txtLNomeTelaCadastroCertificado);
        txtDescricao = findViewById(R.id.txtDescricaoTelaCadastroCertificado);
        txtCargaHoraria = findViewById(R.id.txtCargaHorariaTelaCadastroCertificado);
        btnSalvar = findViewById(R.id.btnSalvarTelaCadastroCertificado);
        btnExcluir = findViewById(R.id.btnExcluirTelaCadastroCertificado);
    }

    private Boolean proConsisteDados(){
        Boolean resposta = true;

        if(txtNome.getText().toString().isEmpty()){
            resposta = false;
        }
        else if(txtDescricao.getText().toString().isEmpty()){
            resposta = false;
        }
        else if(txtCargaHoraria.getText().toString().isEmpty()){
            resposta = false;
        }

        return resposta;

    }

    private void proAdicionarCertificado(){
        if(proConsisteDados()){

            String nome = txtNome.getText().toString();
            String descricao = txtDescricao.getText().toString();
            Integer cargaHoraria = Integer.parseInt(txtCargaHoraria.getText().toString());

            CertificadoDAO certificadoDao = new CertificadoDAO(getBaseContext());

            //SE TIVER CÓDIGO FAZ UPDATE, SENÃO, FAZ INSERT
            if(codigoRecebido == null){
                if(certificadoDao.proCadastrar(nome, descricao, cargaHoraria, aluno.getCodigo())){
                    Toast.makeText(getBaseContext(), "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getBaseContext(), "Erro ao cadastrar", Toast.LENGTH_LONG).show();
                }
            }
            else{
                if(certificadoDao.proAlterar(codigoRecebido, nome, descricao, cargaHoraria ,true)){
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
        builder.setMessage("Realmente deseja excluir este certificado?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(proExcluirCertificado()){
                    Toast.makeText(getApplicationContext(), "Certificado excluido", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Certificado não excluido", Toast.LENGTH_LONG).show();
            }
        });

        builder.create().show();
    }

    private Boolean proExcluirCertificado(){
        CertificadoDAO certificadoDao = new CertificadoDAO(getBaseContext());
        return certificadoDao.proRemover(codigoRecebido);
    }
}