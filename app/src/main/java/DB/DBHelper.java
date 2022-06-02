package DB;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PAID_HOURS_DB3";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_COORDENADOR = ("CREATE TABLE COORDENADOR(COORDENADOR_CODIGO INTEGER PRIMARY KEY AUTOINCREMENT, COORDENADOR_NOME TEXT NOT NULL, COORDENADOR_EMAIL TEXT NOT NULL UNIQUE);");
    private static final String CREATE_TABLE_USUARIO = ("CREATE TABLE USUARIO(USUARIO_CODIGO INTEGER PRIMARY KEY AUTOINCREMENT, USUARIO_LOGIN TEXT NOT NULL UNIQUE, USUARIO_SENHA TEXT NOT NULL, USUARIO_COORDENADOR_FK_EMAIL TEXT NOT NULL);");
    private static final String CREATE_TABLE_CURSO = ("CREATE TABLE CURSO(CURSO_CODIGO INTEGER PRIMARY KEY AUTOINCREMENT, CURSO_NOME TEXT NOT NULL, CURSO_CARGA_HORARIA INTEGER NOT NULL, CURSO_STATUS BOOLEAN NOT NULL, CURSO_COORDENADOR_FK_CODIGO INTEGER NOT NULL );");
    private static final String CREATE_TABLE_ALUNO = ("CREATE TABLE ALUNO(ALUNO_CODIGO INTEGER PRIMARY KEY AUTOINCREMENT, ALUNO_NOME TEXT NOT NULL, ALUNO_MATRICULA INTEGER NOT NULL UNIQUE, ALUNO_STATUS BOOLEAN NOT NULL, ALUNO_CURSO_FK_CODIGO INTEGER NOT NULL );");

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_COORDENADOR);
        sqLiteDatabase.execSQL(CREATE_TABLE_USUARIO);
        sqLiteDatabase.execSQL(CREATE_TABLE_CURSO);
        sqLiteDatabase.execSQL(CREATE_TABLE_ALUNO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("testeDB", "on Upgrade disparado");
    }
}
