package dataset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import entidades.Despesas;
import entidades.Receitas;


public class Conn extends SQLiteOpenHelper {


    private static String DATABASE_NAME = "DbFinco";
    private static int DATABASE_VERSION = 3;

    protected static final String TAB_LOGIN = "Usuarios";

    protected static final String ID = "Id";
    protected static final String USUARIO = "Usuario";
    protected static final String SENHA = "Senha";
    protected static final String STATUS = "Status";

    /////////////////////////////////////////////////////
    protected static final String TAB_DESP = "Despesas";
    
    protected static final String DATA = "Data";
    protected static final String DATA_VENC = "Data_venc";
    protected static final String DESCRICAO = "Descricao";
    protected static final String VALOR = "Valor";
    private static final String[] COLUNASDESP = {ID,DATA,DATA_VENC,DESCRICAO,VALOR};

    /////////////////////////////////////////////////////
    protected static final String TAB_REC = "Receitas";
    private static final String[] COLUNASREC = {ID,DATA,DATA_VENC,DESCRICAO,VALOR};    

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

    private static final String CREATE_TABLE3 = "CREATE TABLE Receitas("+
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
        db.execSQL(CREATE_TABLE3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Usuarios;");
        db.execSQL("DROP TABLE IF EXISTS Despesas;");
        db.execSQL("DROP TABLE IF EXISTS Receitas;");
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

    private Receitas cursorToReceitas(Cursor cursor){
        Receitas receitas = new Receitas();
        receitas.setId(Integer.parseInt(cursor.getString(0)));
        receitas.setData(cursor.getString(1));
        receitas.setData_venc(cursor.getString(2));
        receitas.setDescricao(cursor.getString(3));
        receitas.setValor(cursor.getDouble(4));
        return receitas;
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

    public ArrayList<Receitas> getAllReceitas(){
        ArrayList<Receitas> listarec = new ArrayList<Receitas>();
        String query =  "SELECT * FROM "+TAB_REC;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    Receitas receitas = cursorToReceitas(cursor);
                    listarec.add(receitas);
                } while
                (cursor.moveToNext());
            }
        }
        db.close();
        return listarec;
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

    public Receitas getReceitabyid(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TAB_REC,COLUNASREC,ID + " = ?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor == null){
            return null;
        }else{
            cursor.moveToFirst();
            Receitas receita = cursorToReceitas(cursor);
            return receita;
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

    public String insertRec(Receitas receita){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();;
        long retorno;

        val.put(Conn.DATA,receita.getData());
        val.put(Conn.DATA_VENC,receita.getData_venc());
        val.put(Conn.DESCRICAO,receita.getDescricao());
        val.put(Conn.VALOR,receita.getValor());

        retorno = db.insert(Conn.TAB_REC, null,val);
        db.close();

        if(retorno == -1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com Sucesso!";
    }    

    public String atualizaDesp(Despesas despesa){
        long retorno;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(DATA,despesa.getData());
        val.put(DATA_VENC,despesa.getData_venc());
        val.put(DESCRICAO,despesa.getDescricao());
        val.put(VALOR,new Double(despesa.getValor().toString()));
        retorno = db.update(TAB_DESP,val,ID + " = ?",new String[]{String.valueOf(despesa.getId())});
        db.close();
        if(retorno == -1)
            return "Erro ao alterar registro";
        else
            return "Registro alterado com Sucesso!";

    }

    public String atualizaRec(Receitas receita){
        long retorno;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(DATA,receita.getData());
        val.put(DATA_VENC,receita.getData_venc());
        val.put(DESCRICAO,receita.getDescricao());
        val.put(VALOR,new Double(receita.getValor().toString()));
        retorno = db.update(TAB_REC,val,ID + " = ?",new String[]{String.valueOf(receita.getId())});
        db.close();
        if(retorno == -1)
            return "Erro ao alterar registro";
        else
            return "Registro alterado com Sucesso!";

    }    
    
    public String deleteDesp(Despesas despesa){
        long retorno;
        SQLiteDatabase db = this.getWritableDatabase();
        retorno = db.delete(TAB_DESP,ID + " = ?",new String[]{String.valueOf(despesa.getId())});
        db.close();
        if(retorno == -1)
            return "Erro ao deletar registro";
        else
            return "Registro apagado com Sucesso!";
    }

    public String deleteRec(Receitas receita){
        long retorno;
        SQLiteDatabase db = this.getWritableDatabase();
        retorno = db.delete(TAB_REC,ID + " = ?",new String[]{String.valueOf(receita.getId())});
        db.close();
        if(retorno == -1)
            return "Erro ao deletar registro";
        else
            return "Registro apagado com Sucesso!";
    }




    public String somaDesp(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(VALOR) AS SOMA FROM "+TAB_DESP;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        db.close();
        return String.valueOf(cursor.getDouble(0));

    }

    public String somaRec(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(VALOR) AS SOMA FROM "+TAB_REC;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        db.close();
        return String.valueOf(cursor.getDouble(0));

    }

    public String somaTotal(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(SOMA) AS TOTAL FROM (SELECT (SUM(VALOR)*-1) AS SOMA FROM "+TAB_DESP+" UNION ALL SELECT SUM(VALOR) AS SOMA FROM "+TAB_REC+")";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        db.close();
        return String.valueOf(cursor.getDouble(0));

    }

}
