package com.example.finco;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dataset.Conn;
import dataset.ControllerDespesas;
import dataset.DespAdapter;
import dataset.RecAdapter;
import entidades.Despesas;
import entidades.Receitas;

public class Fluxo extends AppCompatActivity {

    ListView list_des,list_rec;
    TextView txt_soma_des,txt_soma_rec,txt_soma_tot;
    ControllerDespesas crud = new ControllerDespesas(getBaseContext());
    private Conn bd;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluxo);

        bd = new Conn(this);

        list_des = (ListView) findViewById(R.id.list_des);
        list_rec = (ListView) findViewById(R.id.list_rec);
        txt_soma_des = (TextView) findViewById(R.id.txt_soma_des);
        txt_soma_rec = (TextView) findViewById(R.id.txt_soma_rec);
        txt_soma_tot = (TextView) findViewById(R.id.txt_soma_tot);
        Button bt_des = (Button) findViewById(R.id.bt_des);
        Button bt_rec = (Button) findViewById(R.id.bt_rec);


        bt_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),FrmDespesa.class);
                startActivity(it);
            }
        });

        bt_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),FrmReceita.class);
                startActivity(it);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        final ArrayList<Despesas> listades = bd.getAllDespesas();
        final ArrayList<Receitas> listarec = bd.getAllReceitas();
        txt_soma_des.setText(bd.somaDesp());
        txt_soma_rec.setText(bd.somaRec());
        txt_soma_tot.setText(bd.somaTotal());
        DespAdapter adap_des = new DespAdapter(this,listades);
        RecAdapter adap_rec = new RecAdapter(this,listarec);

        //ArrayAdapter<Despesas> adapter = new ArrayAdapter<Despesas>(this,android.R.layout.simple_list_item_1,listades);
        list_des.setAdapter(adap_des);
        list_rec.setAdapter(adap_rec);

        list_des.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getApplicationContext(),FrmDespesa.class);
                it.putExtra("id",listades.get(position).getId());
                it.putExtra("edita",true);
                startActivity(it);
            }
        });
        list_rec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getApplicationContext(),FrmReceita.class);
                it.putExtra("id",listarec.get(position).getId());
                it.putExtra("edita",true);
                startActivity(it);
            }
        });
    }



}
