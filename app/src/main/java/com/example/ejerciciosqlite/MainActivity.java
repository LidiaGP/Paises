package com.example.ejerciciosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Paises BD;
    SQLiteDatabase bd;
    private ListView listView;
    Button btn_añadir;
    Button btn_actualizar;
    EditText ed_pais;
    EditText ed_codigo;
    Button btn_borrar;
    SimpleCursorAdapter adapter;

    void actualizarListView() {
        Cursor cursor = bd.rawQuery("SELECT rowid as _id,* FROM Paises ORDER BY codigo", null);
        adapter.changeCursor(cursor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listview);
        btn_añadir=findViewById(R.id.btn_añadir);
        ed_pais=findViewById(R.id.ed_pais);
        ed_codigo=findViewById(R.id.ed_codigo);
        btn_actualizar=findViewById(R.id.btn_actualizar);
        btn_borrar=findViewById(R.id.btn_borrar);



        btn_añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = ed_codigo.getText().toString();
                String nombre = ed_pais.getText().toString();

                if (codigo.length()>0 && nombre.length()>0) {

                    ContentValues valoresAInsertar = new ContentValues();
                    valoresAInsertar.put("codigo", codigo);
                    valoresAInsertar.put("nombre", nombre);

                    long rowid = bd.insert("Paises", null, valoresAInsertar);
                    if (rowid == -1 ){
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ed_codigo.setText("");
                        ed_pais.setText("");
                        actualizarListView();
                    }

                }
                else {
                    Toast.makeText(MainActivity.this, "Debe introducir datos", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = ed_codigo.getText().toString();
                String nombre = ed_pais.getText().toString();
                if (codigo.length()>0 && nombre.length()>0) {
                    ContentValues valoresActualizar = new ContentValues();
                    valoresActualizar.put("nombre", nombre);

                    String parametrosCondicionUpdate[] = {codigo};

                    int num_filas_actualizadas= bd.update("Paises", valoresActualizar, "codigo=?", parametrosCondicionUpdate);
                    actualizarListView();
                }
                else {
                    Toast.makeText(MainActivity.this, "Debe introducir datos a actualizar", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = ed_codigo.getText().toString();

                if (codigo.length()>0 ) {
                    String SQLDeleteParametros = "DELETE FROM Paises WHERE codigo=?";
                    String valoresParametrosCondicion[] = {codigo};
                    bd.execSQL(SQLDeleteParametros, valoresParametrosCondicion);

                    //int num_filas_borradas=bd.delete("Paises", "codigo=?",valoresParametrosCondicion);
                    actualizarListView();
                }
                else {
                    Toast.makeText(MainActivity.this, "Debe introducir datos a borrar", Toast.LENGTH_SHORT).show();
                }


            }
        });
        BD=new Paises(MainActivity.this);
        bd=BD.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT rowid as _id,* FROM Paises ORDER BY codigo", null);

        String columnas[]={"codigo","nombre"};
        int controles[]={R.id.tv_codigo,R.id.tv_nombre};
        adapter=new SimpleCursorAdapter(this,R.layout.item_listview,cursor,columnas,controles,0);
        listView.setAdapter(adapter);


    }

    @Override
    protected void onStop() {
        super.onStop();
        bd.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        bd=BD.getReadableDatabase();
    }
}