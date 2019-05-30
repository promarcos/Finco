package dataset;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import entidades.Usuarios;

public class Controller {

    private SQLiteDatabase db;
    private Conn conn;

    public Controller(Context context){
        conn = new Conn(context);
    }

    public String insertDt(Usuarios usuario){
        ContentValues val;
        long retorno;

        db = conn.getWritableDatabase();
        val = new ContentValues();

        val.put(Conn.USUARIO,usuario.getUsuario());
        val.put(Conn.SENHA,usuario.getSenha());
        val.put(Conn.STATUS,usuario.getStatus());

        retorno = db.insert(Conn.TAB_LOGIN, null,val);
        db.close();

        if(retorno == -1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com Sucesso!";
    }

    public Cursor selectallDt(){
        Cursor cursora;
        String[] campos = {conn.ID,conn.USUARIO};
        db = conn.getReadableDatabase();
        cursora = db.query(conn.TAB_LOGIN,campos,null,null,null,null,null);
        if(cursora!= null){
            cursora.moveToFirst();
        }
            db.close();
            return cursora;
    }

    public Cursor selectDtId(Usuarios usuario){
        Cursor cursora;
        String[] campos = {conn.ID,conn.USUARIO,conn.SENHA,conn.STATUS};
        String where = Conn.ID + "="+usuario.getId();
        db = conn.getReadableDatabase();
        cursora = db.query(Conn.TAB_LOGIN,campos,where,null,null,null,null);
        if(cursora!=null){
            cursora.moveToFirst();
        }
        db.close();
        return cursora;
    }

    public Cursor selectDtAut(Usuarios usuario){
        Cursor cursora;
        String[] campos = {conn.ID,conn.USUARIO,conn.SENHA,conn.STATUS};
        String where = Conn.USUARIO + " = '" + usuario.getUsuario() +"' and "+ Conn.SENHA + " = '" + usuario.getSenha()+"'";
        db = conn.getReadableDatabase();
        cursora = db.query(Conn.TAB_LOGIN,campos,where,null,null,null,null);
        if(cursora!=null){
            cursora.moveToFirst();
        }
        db.close();
        return cursora;
    }

}
