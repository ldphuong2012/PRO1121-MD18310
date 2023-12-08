package com.example.project_duan1.DAO;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project_duan1.Database.DBHelper;

public class AccountDAO {
    DBHelper dbHelper;
    SharedPreferences sharedPreferences;
    public AccountDAO(Context context){
        sharedPreferences = context.getSharedPreferences("Accounts",MODE_PRIVATE);
        dbHelper= new DBHelper(context);
    }
    public boolean checkDangNhap(String user,String pass){
        SQLiteDatabase sqLiteDatabase= dbHelper.getReadableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("Select*From Accounts where username=? And password =?",new String[]{user,pass});
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("name",cursor.getString(0));
            editor.putString("username", cursor.getString(2));
            editor.putString("loaitaikhoan",cursor.getString(4));
            editor.commit();
            return true;
        }else {
            return false;
        }
    }
    public boolean registerTaiKhoan(String name, String email,String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("username", username);
        values.put("password", password);

        values.put("loaitaikhoan", "User");

        long result = db.insert("Accounts", null, values);
        return result != -1;
    }
    public boolean capnhatmk(String username, String oldpass,String newpass){
        SQLiteDatabase sqLiteDatabase= dbHelper.getWritableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery("Select*From Accounts where username=? And password=?",new String[]{username,oldpass});
        if (cursor.getCount()>0){//Nhập đúng thông tin
            ContentValues contentValues= new ContentValues();
            contentValues.put("password",newpass);
           long check= sqLiteDatabase.update("Accounts",contentValues,"username=?",new String[]{username});
            if (check ==-1)
                return false;

                return true;

        }
        return false;
    }
}
