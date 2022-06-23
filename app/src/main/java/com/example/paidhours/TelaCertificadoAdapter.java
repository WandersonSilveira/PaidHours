package com.example.paidhours;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.paidhours.entidade.Certificado;

import java.io.Serializable;
import java.util.List;

public class TelaCertificadoAdapter extends RecyclerView.Adapter<TelaCertificadoAdapter.CertificadoHolder>{

    private final List<Certificado> listaCertificado;

    public TelaCertificadoAdapter(List<Certificado> listaCertificado) {
        this.listaCertificado = listaCertificado;
    }

    @Override
    public TelaCertificadoAdapter.CertificadoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_certificado, parent, false);
        return new TelaCertificadoAdapter.CertificadoHolder(view,parent.getContext());
    }

    @Override
    public void onBindViewHolder(TelaCertificadoAdapter.CertificadoHolder holder, int position) {
        holder.lblNome.setText("Nome:   " + listaCertificado.get(position).getNome());
        holder.lblDescricao.setText("Descrição:   " + (listaCertificado.get(position).getDescricao()));
        holder.lblCargaHoraria.setText("Carga horária:   " + (listaCertificado.get(position).getCargaHoraria()));
        //Imagem
        Bitmap raw;
        byte[] fotoArray;
        fotoArray = (listaCertificado.get(position).getImagem());
        if(fotoArray!=null){
            raw  = BitmapFactory.decodeByteArray(fotoArray,0,fotoArray.length);
            holder.ivImagem.setImageBitmap(raw);
        }
    }



    @Override
    public int getItemCount() {
        return listaCertificado.size();
    }

    public class CertificadoHolder extends RecyclerView.ViewHolder {
        public TextView lblNome;
        public TextView lblDescricao;
        public TextView lblCargaHoraria;
        public ImageView ivImagem;

        public CertificadoHolder(View itemView, final Context context) {
            super(itemView);
            lblNome = itemView.findViewById(R.id.lblNomeTelaItemListaCertificado);
            lblDescricao = itemView.findViewById(R.id.lblDescricaoItemListaCertificado);
            lblCargaHoraria = itemView.findViewById(R.id.lblCargaHorariaItemListaCertificado);
            ivImagem = itemView.findViewById(R.id.ivImagemTelaItemListaCertificado);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(listaCertificado.size() > 0){
                        Certificado certificado = listaCertificado.get(getLayoutPosition());

                        Intent intent = new Intent(context, TelaCadastroCertificado.class);
                        intent.putExtra("CERTIFICADO", (Serializable) certificado);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
