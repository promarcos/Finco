package com.example.finco;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dataset.Conn;
import dataset.Controller;
import entidades.Usuarios;

public class MainActivity extends AppCompatActivity {


    Cursor cursor;
    AlertDialog msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_log = (Button) findViewById(R.id.btn_log);
        Button btn_can = (Button) findViewById(R.id.btn_can);
        final EditText edt_log = (EditText) findViewById(R.id.edt_log);
        final EditText edt_sen = (EditText) findViewById(R.id.edt_sen);
        final Usuarios usuario = new Usuarios();

        // -- constroi a mensagem -- //
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cadastrar Usuário");
        builder.setMessage("Usuario ainda não cadastrado, deseja cadastrar?");
        builder.setNegativeButton("Não Obrigado!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Não Cadastrado!", Toast.LENGTH_SHORT).show();
                edt_log.setText("");
                edt_sen.setText("");
            }
        });

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Controller crud = new Controller(getBaseContext());

                usuario.setUsuario(edt_log.getText().toString());
                usuario.setSenha(edt_sen.getText().toString());
                usuario.setStatus(true);

                cursor = crud.selectDtAut(usuario);

                if(cursor.getCount() == 1){
                    Intent it = new Intent(getApplicationContext(),Fluxo.class);
                    edt_log.setText("");
                    edt_sen.setText("");
                    startActivity(it);

                }else{
                    builder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(MainActivity.this, "positivo=" + which, Toast.LENGTH_SHORT).show();
                            String retorno;
                            retorno = crud.insertDt(usuario);
                            Toast.makeText(getApplicationContext(),retorno,Toast.LENGTH_LONG).show();
                        }
                    });
                    msg = builder.create();
                    msg.show();
                }


            }
        });

        btn_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
