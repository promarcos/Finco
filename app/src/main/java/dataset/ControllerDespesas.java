package dataset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import entidades.*;

public class ControllerDespesas {

    private SQLiteDatabase db;
    private Conn conn;

    public ControllerDespesas(Context context){
        conn = new Conn(context);
    }

    public String insertDt(Despesas despesa){
        ContentValues val;
        long retorno;

        db = conn.getWritableDatabase();
        val = new ContentValues();

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

    public Cursor selectallDt(){
        Cursor cursora;
        String[] campos = {conn.ID,conn.DESCRICAO,conn.VALOR};
        db = conn.getReadableDatabase();
        cursora = db.query(conn.TAB_DESP,campos,null,null,null,null,null);
        if(cursora!= null){
            cursora.moveToFirst();
        }
        db.close();
        return cursora;
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
        String query =  "Select * from "+conn.TAB_DESP;
        db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do{
                Despesas despesas = cursorToDespesas(cursor);
                listadesp.add(despesas);
            }while
            (cursor.moveToNext());
        }
        return listadesp;
    }

    public Cursor selectDtId(Despesas despesa){
        Cursor cursora;
        String[] campos = {conn.ID,conn.DATA,conn.DATA_VENC,conn.DESCRICAO,conn.VALOR};
        String where = Conn.ID + "="+despesa.getId();
        db = conn.getReadableDatabase();
        cursora = db.query(Conn.TAB_LOGIN,campos,where,null,null,null,null);
        if(cursora!=null){
            cursora.moveToFirst();
        }
        db.close();
        return cursora;
    }


}
