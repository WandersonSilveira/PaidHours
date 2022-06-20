package com.example.paidhours;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.paidhours.entidade.Aluno;
import com.example.paidhours.entidade.Certificado;
import com.example.paidhours.entidade.Curso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import DAO.AlunoDAO;
import DAO.CertificadoDAO;

public class TelaCadastroCertificado extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;

    EditText txtNome;
    EditText txtDescricao;
    EditText txtCargaHoraria;
    Button btnSalvar;
    Button btnExcluir;
    ImageView ivImagem;

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

        ivImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proSelecionarImagem();
            }
        });
    }

    private void proSelecionarImagem(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {
                Uri imagemUri = data.getData();
                Bitmap fotoBuscada = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagemUri);
                Bitmap fotoRedimensionada = Bitmap.createScaledBitmap(fotoBuscada, 200, 200, false);
                ivImagem.setImageBitmap(fotoRedimensionada);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void proCarregaInformacao(){
        Intent intent = getIntent();
        aluno = (Aluno) intent.getSerializableExtra("ALUNO");
        Certificado certificado = (Certificado) intent.getSerializableExtra("CERTIFICADO");

        if(certificado != null){
            txtNome.setText(certificado.getNome());
            txtDescricao.setText(certificado.getDescricao());
            txtCargaHoraria.setText(certificado.getCargaHoraria().toString());
            //Imagem
            Bitmap raw;
            byte[] fotoArray;
            fotoArray = (certificado.getImagem());
            if(fotoArray!=null){
                raw  = BitmapFactory.decodeByteArray(fotoArray,0,fotoArray.length);
                ivImagem.setImageBitmap(raw);
            }
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
        ivImagem = findViewById(R.id.ivImagemTelaCadastroCertificado);
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
            //Imagem
            Bitmap bitmap = ((BitmapDrawable) ivImagem.getDrawable()).getBitmap();
            ByteArrayOutputStream saida = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,saida);
            byte[] imagem = saida.toByteArray();

            CertificadoDAO certificadoDao = new CertificadoDAO(getBaseContext());

            //SE TIVER CÓDIGO FAZ UPDATE, SENÃO, FAZ INSERT
            if(codigoRecebido == null){
                if(certificadoDao.proCadastrar(nome, descricao, cargaHoraria, imagem, aluno.getCodigo())){
                    Toast.makeText(getBaseContext(), "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getBaseContext(), "Erro ao cadastrar", Toast.LENGTH_LONG).show();
                }
            }
            else{
                if(certificadoDao.proAlterar(codigoRecebido, nome, descricao, cargaHoraria , imagem,true)){
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