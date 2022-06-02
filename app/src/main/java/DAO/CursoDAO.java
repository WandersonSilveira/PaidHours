package DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.paidhours.entidade.Curso;

import java.util.ArrayList;
import java.util.List;

import DB.DBGateway;

public class CursoDAO {
    private final String TABLE_CURSO = "CURSO";
    private DBGateway gateway;
    List<Curso> listaCursos= new ArrayList();

    public CursoDAO(Context context) {
        gateway = DBGateway.getInstance(context);
    }

    public boolean proCadastrar(String nome, Integer cargaHoraria, Integer codigoCoordenador){
        ContentValues content_values = new ContentValues();
        content_values.put("CURSO_NOME", nome);
        content_values.put("CURSO_CARGA_HORARIA", cargaHoraria);
        content_values.put("CURSO_STATUS", true);
        content_values.put("CURSO_COORDENADOR_FK_CODIGO", codigoCoordenador);

        return gateway.getDatabase().insert(TABLE_CURSO, null, content_values) > 0;
    }

    public boolean proAlterar(Integer codigo, String nome, Integer cargaHoraria, Boolean status){
        ContentValues content_values = new ContentValues();
        content_values.put("CURSO_CODIGO", codigo);
        content_values.put("CURSO_NOME", nome);
        content_values.put("CURSO_CARGA_HORARIA", cargaHoraria);
        content_values.put("CURSO_STATUS", status);

        return gateway.getDatabase().update(TABLE_CURSO, content_values, "CURSO_CODIGO = ?", new String[]{codigo + ""} ) > 0;
    }

    public boolean proRemover(Integer codigo){
        return gateway.getDatabase().delete(TABLE_CURSO, "CURSO_CODIGO = ?", new String[]{codigo + ""} ) > 0;
    }

    public List<Curso> proListar(Integer codigoCoordenador){
        try {
            Cursor cursor = gateway.getDatabase().rawQuery("SELECT * FROM CURSO INNER JOIN COORDENADOR ON (CURSO_COORDENADOR_FK_CODIGO = COORDENADOR_CODIGO) WHERE COORDENADOR_CODIGO = ? ",new String[]{codigoCoordenador + ""}, null);

            while (cursor.moveToNext()){
                @SuppressLint("Range") Integer codigo = cursor.getInt(cursor.getColumnIndex("CURSO_CODIGO"));
                @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("CURSO_NOME"));
                @SuppressLint("Range") Integer cargaHoraria = cursor.getInt(cursor.getColumnIndex("CURSO_CARGA_HORARIA"));
                @SuppressLint("Range") Boolean status = cursor.getInt(cursor.getColumnIndex("CURSO_STATUS")) > 0;

                listaCursos.add(new Curso(codigo, nome, cargaHoraria, status));
            }

            cursor.close();
        }
        catch (Exception e){
            Log.d("erro", e.getMessage());
        }
        return  listaCursos;
    }
}
