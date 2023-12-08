package com.example.project_duan1.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context){
        super(context,"DANGNHAP",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String DBAccounts="Create Table Accounts(name text,email text ,username text Primary Key,password text,loaitaikhoan text)";
        db.execSQL(DBAccounts);
        db.execSQL("Insert into Accounts Values('Lê Đức Phương','ldphuong20122003@gmail.com','ldphuong2012','phuong2012','Admin')," +
                "('Tạ Quang Chiến','chien123@gmail.com','chien123','12345678','User')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
