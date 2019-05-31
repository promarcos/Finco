package com.example.finco;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dataset.Conn;

import entidades.Receitas;


public class FrmReceita extends AppCompatActivity {


    private Conn bd;
    protected Boolean edita;
    protected Integer id;
    Receitas receita;
    AlertDialog msg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_receita);
        bd = new Conn(this);

        Button bt_cad = (Button) findViewById(R.id.bt_cad_rec);
        Button bt_can = (Button) findViewById(R.id.bt_can_rec);
        Button bt_exc = (Button) findViewById(R.id.bt_exc_rec);
        final EditText edt_dt = (EditText) findViewById(R.id.edt_dt);
        final EditText edt_dtvenc = (EditText) findViewById(R.id.edt_dtvc);
        final EditText edt_desc = (EditText) findViewById(R.id.edt_des);
        final EditText edt_val = (EditText) findViewById(R.id.edt_val);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        edita = getIntent().getBooleanExtra("edita",false);
        if(edita){
            id = getIntent().getIntExtra("id",0);
            receita = new Receitas();
            receita = bd.getReceitabyid(id);

            edt_dt.setText(receita.getData());
            edt_dtvenc.setText(receita.getData_venc());
            edt_desc.setText(receita.getDescricao());
            edt_val.setText(String.valueOf(receita.getValor()));
        }



        bt_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String retorno;
                Receitas receitas = new Receitas();
                receitas.setId(id);
                receitas.setData(edt_dt.getText().toString());
                receitas.setData_venc(edt_dtvenc.getText().toString());
                receitas.setDescricao(edt_desc.getText().toString());
                receitas.setValor(Double.parseDouble(edt_val.getText().toString()));

                if(edita){
                    retorno = bd.atualizaRec(receitas);
                }else {
                    retorno = bd.insertRec(receitas);
                }
                finish();
                Toast.makeText(getApplicationContext(),retorno,Toast.LENGTH_LONG).show();
            }
        });

        bt_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_exc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // -- constroi a mensagem -- //

                builder.setTitle("Excluir");
                builder.setMessage("Deseja realmente Excluir o Registro?");
                builder.setNegativeButton("Não Obrigado!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(FrmReceita.this, "Ok!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this, "positivo=" + which, Toast.LENGTH_SHORT).show();
                        String retorno;
                        retorno = bd.deleteRec(receita);
                        finish();
                        Toast.makeText(getApplicationContext(),retorno,Toast.LENGTH_LONG).show();

                    }
                });
                msg = builder.create();
                msg.show();
            }
        });



    }
}
