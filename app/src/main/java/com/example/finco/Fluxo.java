package com.example.finco;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

import dataset.Conn;
import dataset.ConnDes;
import dataset.ControllerDespesas;
import dataset.UserAdapter;
import entidades.Despesas;

public class Fluxo extends AppCompatActivity {

    ListView list_des;
    ControllerDespesas crud = new ControllerDespesas(getBaseContext());
    private Conn bd;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluxo);

        bd = new Conn(this);

        list_des = (ListView) findViewById(R.id.list_des);
        ListView list_rec = (ListView) findViewById(R.id.list_rec);
        Button bt_des = (Button) findViewById(R.id.bt_des);
        Button bt_rec = (Button) findViewById(R.id.bt_rec);


        bt_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),FrmDespesa.class);
                startActivity(it);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        final ArrayList<Despesas> listades = bd.getAllDespesas();
        UserAdapter adapter = new UserAdapter(this,listades);
        //ArrayAdapter<Despesas> adapter = new ArrayAdapter<Despesas>(this,android.R.layout.simple_list_item_1,listades);
        list_des.setAdapter(adapter);

        list_des.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getApplicationContext(),FrmDespesa.class);
                it.putExtra("id",listades.get(position).getId());
                it.putExtra("edita",true);
                startActivity(it);
            }
        });
    }



}
