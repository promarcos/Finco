package dataset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import entidades.Despesas;


public class Conn extends SQLiteOpenHelper {


    private static String DATABASE_NAME = "DbFinco";
    private static int DATABASE_VERSION = 2;

    protected static final String TAB_LOGIN = "Usuarios";

    protected static final String ID = "Id";
    protected static final String USUARIO = "Usuario";
    protected static final String SENHA = "Senha";
    protected static final String STATUS = "Status";

    /////////////////////////////////////////////////////
    protected static final String TAB_DESP = "Despesas";

    //protected static final String ID = "Id";
    protected static final String DATA = "Data";
    protected static final String DATA_VENC = "Data_venc";
    protected static final String DESCRICAO = "Descricao";
    protected static final String VALOR = "Valor";
    private static final String[] COLUNASDESP = {ID,DATA,DATA_VENC,DESCRICAO,VALOR};

    private static final String CREATE_TABLE1 = "CREATE TABLE Usuarios("+
            "Id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "Usuario TEXT,"+
            "Senha TEXT,"+
            "Status BOOL)";

    private static final String CREATE_TABLE2 = "CREATE TABLE Despesas("+
            "Id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "Data TEXT,"+
            "Data_venc TEXT,"+
            "Descricao TEXT,"+
            "Valor REAL)";


    public Conn(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Usuarios;");
        db.execSQL("DROP TABLE IF EXISTS Despesas;");
        onCreate(db);
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

    public String atualizaDesp(Despesas despesa){
        long retorno;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(DATA,despesa.getData());
        val.put(DATA_VENC,despesa.getData_venc());
        val.put(DESCRICAO,despesa.getDescricao());
        val.put(VALOR,new Double(despesa.getValor()));
        retorno = db.update(TAB_DESP,val,ID + " = ?",new String[]{String.valueOf(despesa.getId())});
        db.close();
        if(retorno == -1)
            return "Erro ao alterar registro";
        else
            return "Registro alterado com Sucesso!";

    }

    public int deleteDesp(Despesas despesa){
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TAB_DESP,ID + " = ?",new String[]{String.valueOf(despesa.getId())});
        db.close();
        return i;
    }


    public Despesas getDespesabyid(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TAB_DESP,COLUNASDESP,ID + " = ?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor == null){
            return null;
        }else{
            cursor.moveToFirst();
            Despesas despesa = cursorToDespesas(cursor);
            return despesa;
        }

    }

    public String insertDesp(Despesas despesa){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();;
        long retorno;

        val.put(Conn.DATA,despesa.getData());
        val.put(Conn.DATA_VENC,despesa.getData_venc());
        val.put(Conn.DESCRICAO,despesa.getDescricao());
        val.put(Conn.VALOR,despesa.getValor());

        retorno = db.insert(Conn.TAB_DESP, null,val);
        db.close();

        if(retorno == -1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com Sucesso!";
    }

}
