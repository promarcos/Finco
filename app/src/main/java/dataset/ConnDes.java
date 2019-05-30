package dataset;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import entidades.Despesas;

public class ConnDes extends SQLiteOpenHelper {


    private static String DATABASE_NAME = "DbFinco";
    private static int DATABASE_VERSION = 1;

    protected static final String TAB_DESP = "Despesas";

    protected static final String ID = "Id";
    protected static final String DATA = "Data";
    protected static final String DATA_VENC = "Data_venc";
    protected static final String DESCRICAO = "Descricao";
    protected static final String VALOR = "Valor";


    public ConnDes(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE Despesas("+
                "Id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "Data TEXT,"+
                "Data_venc TEXT,"+
                "Descricao TEXT,"+
                "Valor REAL)";
        db.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Despesas;");

    }

    private Despesas cursorToDespesas(Cursor cursor){
        Despesas despesas = new Despesas();
        despesas.setId(Integer.parseInt(cursor.getString(0)));
        despesas.setData(cursor.getString(1));
        despesas.setData_venc(cursor.getString(2));
        despesas.setDescricao(cursor.getString(3));
        despesas.setValor(cursor.getDouble(4));
        return despesas;
    }

    public ArrayList<Despesas> getAllDespesas(){
        ArrayList<Despesas> listadesp = new ArrayList<Despesas>();
        String query =  "SELECT * FROM "+TAB_DESP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    Despesas despesas = cursorToDespesas(cursor);
                    listadesp.add(despesas);
                } while
                (cursor.moveToNext());
            }
        }
        db.close();
        return listadesp;
    }

}
