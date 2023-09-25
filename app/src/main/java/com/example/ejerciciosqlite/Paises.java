package com.example.ejerciciosqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Paises extends SQLiteOpenHelper {
    final static String NOMBRE_BD="paises.db3";
    final static int VERSION=2;

    public Paises(@Nullable Context context) {
        super(context, NOMBRE_BD,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Paises (\n" +
                "    codigo TEXT PRIMARY KEY,\n" +
                "    nombre TEXT\n" +
                ")");

        String SQLInsert="INSERT INTO Paises(codigo,nombre) VALUES('ES','España')";
        db.execSQL(SQLInsert);

        onUpgrade(db,1,VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion==1 && newVersion>=2){
            String SQLAlter="ALTER TABLE Paises ADD COLUMN Fecha_creacion DATETIME";
            db.execSQL(SQLAlter);
        }

    }
}
