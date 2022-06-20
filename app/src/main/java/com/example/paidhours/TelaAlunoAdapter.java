package com.example.paidhours;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.paidhours.entidade.Aluno;
import com.example.paidhours.entidade.Curso;

import java.io.Serializable;
import java.util.List;

public class TelaAlunoAdapter extends RecyclerView.Adapter<TelaAlunoAdapter.AlunoHolder>{

    private final List<Aluno> listaAluno;

    public TelaAlunoAdapter(List<Aluno> listaAluno) {
        this.listaAluno = listaAluno;
    }

    @Override
    public TelaAlunoAdapter.AlunoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_aluno, parent, false);
        return new TelaAlunoAdapter.AlunoHolder(view,parent.getContext());
    }

    @Override
    public void onBindViewHolder(TelaAlunoAdapter.AlunoHolder holder, int position) {
        holder.lblNome.setText("Nome:   " + listaAluno.get(position).getNome());
        holder.lblMatricula.setText("Mastricula:   " + (listaAluno.get(position).getMatricula()));
        //Imagem
        Bitmap raw;
        byte[] fotoArray;
        fotoArray = (listaAluno.get(position).getImagem());
        if(fotoArray!=null){
            raw  = BitmapFactory.decodeByteArray(fotoArray,0,fotoArray.length);
            holder.ivImagem.setImageBitmap(raw);
        }
    }

    @Override
    public int getItemCount() {
        return listaAluno.size();
    }

    public class AlunoHolder extends RecyclerView.ViewHolder {
        public TextView lblNome;
        public TextView lblMatricula;
        public Button btnGerenciarCertificados;
        public ImageView ivImagem;


        public AlunoHolder(View itemView, final Context context) {
            super(itemView);
            lblNome = itemView.findViewById(R.id.lblNomeTelaItemListaAluno);
            lblMatricula = itemView.findViewById(R.id.lblMatriculaItemListaAluno);
            btnGerenciarCertificados = itemView.findViewById(R.id.btnGerenciarCertificadosItemListaAluno);
            ivImagem = itemView.findViewById(R.id.ivImagemTelaItemListaAluno);

            btnGerenciarCertificados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listaAluno.size() > 0){
                        Aluno aluno = listaAluno.get(getLayoutPosition());

                        Intent intent = new Intent(context, TelaCertificado.class);
                        intent.putExtra("ALUNO", (Serializable) aluno);
                        context.startActivity(intent);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(listaAluno.size() > 0){
                        Aluno aluno = listaAluno.get(getLayoutPosition());

                        Intent intent = new Intent(context, TelaCadastroAluno.class);
                        intent.putExtra("ALUNO", (Serializable) aluno);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

}
