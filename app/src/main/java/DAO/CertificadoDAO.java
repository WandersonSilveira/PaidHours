package DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.paidhours.entidade.Certificado;

import java.util.ArrayList;
import java.util.List;

import DB.DBGateway;

public class CertificadoDAO {
    private final String TABLE_CERTIFICADO = "CERTIFICADO";
    private DBGateway gateway;
    List<Certificado> listaCertificados= new ArrayList();

    public CertificadoDAO(Context context) {
        gateway = DBGateway.getInstance(context);
    }

    public boolean proCadastrar(String nome, String descricao,Integer cargaHoraria, Integer codigoAluno){
        ContentValues content_values = new ContentValues();
        content_values.put("CERTIFICADO_NOME", nome);
        content_values.put("CERTIFICADO_DESCRICAO", descricao);
        content_values.put("CERTIFICADO_CARGA_HORARIA", cargaHoraria);
        content_values.put("CERTIFICADO_STATUS", true);
        content_values.put("CERTIFICADO_ALUNO_FK_CODIGO", codigoAluno);

        return gateway.getDatabase().insert(TABLE_CERTIFICADO, null, content_values) > 0;
    }

    public boolean proAlterar(Integer codigo, String nome, String descricao, Integer cargaHoraria, Boolean status){
        ContentValues content_values = new ContentValues();
        content_values.put("CERTIFICADO_CODIGO", codigo);
        content_values.put("CERTIFICADO_NOME", nome);
        content_values.put("CERTIFICADO_DESCRICAO", descricao);
        content_values.put("CERTIFICADO_CARGA_HORARIA", cargaHoraria);
        content_values.put("CERTIFICADO_STATUS", status);

        return gateway.getDatabase().update(TABLE_CERTIFICADO, content_values, "CERTIFICADO_CODIGO = ?", new String[]{codigo + ""} ) > 0;
    }

    public boolean proRemover(Integer codigo){
        return gateway.getDatabase().delete(TABLE_CERTIFICADO, "CERTIFICADO_CODIGO = ?", new String[]{codigo + ""} ) > 0;
    }

    public List<Certificado> proListar(Integer codigoAluno){
        try {
            Cursor cursor = gateway.getDatabase().rawQuery("SELECT * FROM CERTIFICADO INNER JOIN ALUNO ON (CERTIFICADO_ALUNO_FK_CODIGO = ALUNO_CODIGO) WHERE ALUNO_CODIGO = ? ",new String[]{codigoAluno + ""}, null);

            while (cursor.moveToNext()){
                @SuppressLint("Range") Integer codigo = cursor.getInt(cursor.getColumnIndex("CERTIFICADO_CODIGO"));
                @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("CERTIFICADO_NOME"));
                @SuppressLint("Range") String descricao = cursor.getString(cursor.getColumnIndex("CERTIFICADO_DESCRICAO"));
                @SuppressLint("Range") Integer cargaHoraria = cursor.getInt(cursor.getColumnIndex("CERTIFICADO_CARGA_HORARIA"));
                @SuppressLint("Range") Boolean status = cursor.getInt(cursor.getColumnIndex("CERTIFICADO_STATUS")) > 0;

                listaCertificados.add(new Certificado(codigo, nome, descricao,cargaHoraria, status));
            }

            cursor.close();
        }
        catch (Exception e){
            Log.d("erro", e.getMessage());
        }
        return  listaCertificados;
    }

}
