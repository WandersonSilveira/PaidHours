package DB;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PAID_HOURS_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = ("CREATE TABLE CURSO(CODIGO INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT NOT NULL, CARGA_HORARIA INTEGER NOT NULL, STATUS BOOLEAN NOT NULL);");

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("testeDB", "on Upgrade disparado");
    }
}
