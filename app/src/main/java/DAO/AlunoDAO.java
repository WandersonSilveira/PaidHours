package DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.paidhours.entidade.Aluno;
import com.example.paidhours.entidade.Curso;

import java.util.ArrayList;
import java.util.List;

import DB.DBGateway;

public class AlunoDAO {
    private final String TABLE_ALUNO = "ALUNO";
    private DBGateway gateway;
    List<Aluno> listaAlunos= new ArrayList();

    public AlunoDAO(Context context) {
        gateway = DBGateway.getInstance(context);
    }

    public boolean proCadastrar(String nome, Integer matricula, byte[] imagem, Integer codigoCurso){
        ContentValues content_values = new ContentValues();
        content_values.put("ALUNO_NOME", nome);
        content_values.put("ALUNO_MATRICULA", matricula);
        content_values.put("ALUNO_IMAGEM", imagem);
        content_values.put("ALUNO_STATUS", true);
        content_values.put("ALUNO_CURSO_FK_CODIGO", codigoCurso);

        return gateway.getDatabase().insert(TABLE_ALUNO, null, content_values) > 0;
    }

    public boolean proAlterar(Integer codigo, String nome, Integer matricula, byte[] imagem, Boolean status){
        ContentValues content_values = new ContentValues();
        content_values.put("ALUNO_CODIGO", codigo);
        content_values.put("ALUNO_NOME", nome);
        content_values.put("ALUNO_MATRICULA", matricula);
        content_values.put("ALUNO_IMAGEM", imagem);
        content_values.put("ALUNO_STATUS", status);

        return gateway.getDatabase().update(TABLE_ALUNO, content_values, "ALUNO_CODIGO = ?", new String[]{codigo + ""} ) > 0;
    }

    public boolean proRemover(Integer codigo){
        return gateway.getDatabase().delete(TABLE_ALUNO, "ALUNO_CODIGO = ?", new String[]{codigo + ""} ) > 0;
    }

    public List<Aluno> proListar(Integer codigoCurso){
        try {
            Cursor cursor = gateway.getDatabase().rawQuery("SELECT * FROM ALUNO INNER JOIN CURSO ON (ALUNO_CURSO_FK_CODIGO = CURSO_CODIGO) WHERE CURSO_CODIGO = ? ",new String[]{codigoCurso + ""}, null);

            while (cursor.moveToNext()){
                @SuppressLint("Range") Integer codigo = cursor.getInt(cursor.getColumnIndex("ALUNO_CODIGO"));
                @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("ALUNO_NOME"));
                @SuppressLint("Range") Integer matricula = cursor.getInt(cursor.getColumnIndex("ALUNO_MATRICULA"));
                @SuppressLint("Range") byte[] imagem = cursor.getBlob(cursor.getColumnIndex("ALUNO_IMAGEM"));
                @SuppressLint("Range") Boolean status = cursor.getInt(cursor.getColumnIndex("ALUNO_STATUS")) > 0;

                listaAlunos.add(new Aluno(codigo, nome, matricula, imagem, status));
            }

            cursor.close();
        }
        catch (Exception e){
            Log.d("erro", e.getMessage());
        }
        return  listaAlunos;
    }

}
