package com.example.paidhours;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.paidhours.entidade.Curso;

import java.io.Serializable;
import java.util.List;

public class TelaCursoAdapter extends RecyclerView.Adapter<TelaCursoAdapter.CursoHolder>{

    private final List<Curso> listaCurso;

    public TelaCursoAdapter(List<Curso> listaCurso) {
        this.listaCurso = listaCurso;
    }

    @Override
    public CursoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_curso, parent, false);
        return new CursoHolder(view,parent.getContext());
    }

    @Override
    public void onBindViewHolder(CursoHolder holder, int position) {
        holder.lblNome.setText("Nome:   " + listaCurso.get(position).getNome());
        holder.lblCargaHoraria.setText("Carga horária:   " + (listaCurso.get(position).getCargaHoraria()));
    }

    @Override
    public int getItemCount() {
        return listaCurso.size();
    }

    public class CursoHolder extends RecyclerView.ViewHolder {
        public TextView lblNome;
        public TextView lblCargaHoraria;

        public CursoHolder(View itemView, final Context context) {
            super(itemView);
            lblNome = itemView.findViewById(R.id.lblNomeTelaItemListaCurso);
            lblCargaHoraria = itemView.findViewById(R.id.lblCargaHorariaItemListaCurso);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(listaCurso.size() > 0){
                        Curso curso = listaCurso.get(getLayoutPosition());

                        Intent intent = new Intent(context, TelaCadastroCurso.class);
                        intent.putExtra("CURSO", (Serializable) curso);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }


}
