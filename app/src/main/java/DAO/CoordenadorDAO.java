package DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.paidhours.entidade.Coordenador;
import com.example.paidhours.entidade.Curso;

import java.util.ArrayList;
import java.util.List;

import DB.DBGateway;

public class CoordenadorDAO {

    private final String TABLE_COORDENADOR = "COORDENADOR";
    private final String TABLE_USUARIO = "USUARIO";
    private DBGateway gateway;
    List<Coordenador> listaCoordenador = new ArrayList();

    public CoordenadorDAO(Context context) {
        gateway = DBGateway.getInstance(context);
    }

    public boolean proCadastrar(String nome, String email, String usuario, String senha){
        ContentValues content_values_usuario = new ContentValues();
        content_values_usuario.put("USUARIO_LOGIN", usuario);
        content_values_usuario.put("USUARIO_SENHA", senha);
        content_values_usuario.put("USUARIO_COORDENADOR_FK_EMAIL", email);

        gateway.getDatabase().insert(TABLE_USUARIO, null, content_values_usuario);

        ContentValues content_values_coordenador = new ContentValues();
        content_values_coordenador.put("COORDENADOR_NOME", nome);
        content_values_coordenador.put("COORDENADOR_EMAIL", email);

        return gateway.getDatabase().insert(TABLE_COORDENADOR, null, content_values_coordenador) > 0;
    }

    public List<Coordenador> proListar(){
        try {
            Cursor cursor = gateway.getDatabase().rawQuery("SELECT * FROM COORDENADOR INNER JOIN USUARIO ON(COORDENADOR_EMAIL = USUARIO_COORDENADOR_FK_EMAIL) ", null);

            while (cursor.moveToNext()){
                @SuppressLint("Range") Integer codigo = cursor.getInt(cursor.getColumnIndex("COORDENADOR_CODIGO"));
                @SuppressLint("Range") String nome = cursor.getString(cursor.getColumnIndex("COORDENADOR_NOME"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("COORDENADOR_EMAIL"));
                @SuppressLint("Range") Integer codigoUsuario = cursor.getInt(cursor.getColumnIndex("USUARIO_CODIGO"));
                @SuppressLint("Range") String usuario = cursor.getString(cursor.getColumnIndex("USUARIO_LOGIN"));
                @SuppressLint("Range") String senha = cursor.getString(cursor.getColumnIndex("USUARIO_SENHA"));

                listaCoordenador.add(new Coordenador(codigoUsuario, usuario, senha, codigo, nome, email));
            }

            cursor.close();
        }
        catch (Exception e){
            Log.d("erro", e.getMessage());
        }
        return listaCoordenador;
    }

}
