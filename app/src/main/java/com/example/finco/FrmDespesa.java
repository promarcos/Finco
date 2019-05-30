package com.example.finco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dataset.Conn;
import dataset.ControllerDespesas;
import entidades.Despesas;

public class FrmDespesa extends AppCompatActivity {


    private Conn bd;
    protected Boolean edita;
    protected Integer id;
    Despesas despesa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_despesa);
        bd = new Conn(this);

        Button bt_cad = (Button) findViewById(R.id.bt_cad_des);
        Button bt_can = (Button) findViewById(R.id.bt_can_des);
        final EditText edt_dt = (EditText) findViewById(R.id.edt_dt);
        final EditText edt_dtvenc = (EditText) findViewById(R.id.edt_dtvc);
        final EditText edt_desc = (EditText) findViewById(R.id.edt_des);
        final EditText edt_val = (EditText) findViewById(R.id.edt_val);

        edita = getIntent().getBooleanExtra("edita",false);
        if(edita){
            id = getIntent().getIntExtra("id",0);
            despesa = new Despesas();
            despesa = bd.getDespesabyid(id);

            edt_dt.setText(despesa.getData());
            edt_dtvenc.setText(despesa.getData_venc());
            edt_desc.setText(despesa.getDescricao());
            edt_val.setText(String.valueOf(despesa.getValor()));
        }



        bt_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String retorno;
                Despesas despesas = new Despesas();
                despesas.setData(edt_dt.getText().toString());
                despesas.setData_venc(edt_dtvenc.getText().toString());
                despesas.setDescricao(edt_desc.getText().toString());
                despesas.setValor(Double.parseDouble(edt_val.getText().toString()));

                if(edita){
                    retorno = bd.atualizaDesp(despesas);
                }else {
                    retorno = bd.insertDesp(despesas);
                }
                Toast.makeText(getApplicationContext(),retorno,Toast.LENGTH_LONG).show();
            }
        });

        bt_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
