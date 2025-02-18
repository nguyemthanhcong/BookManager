package com.example.bookmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bookmanager.database.DatabaseHelper;
import com.example.bookmanager.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDao {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "NguoiDung";
    public static final String SQL_NGUOI_DUNG="CREATE TABLE NguoiDung (username text primary key, " +
            "password text, phone text, hoten text);";
    public static final String TAG = "NguoiDungDao";

    public NguoiDungDao(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //Insert Data

    public int insertNguoiDung(NguoiDung nd){
        ContentValues  values = new ContentValues();
        values.put("username", nd.getUserName());
        values.put("password", nd.getPassword());
        values.put("phone", nd.getPhone());
        values.put("hoten", nd.getHoTen());
        try {
            if (db.insert(TABLE_NAME, null, values) == -1){
                return -1;
            }

        }catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    //Get All

    public List<NguoiDung> getAllNguoiDung(){
        List<NguoiDung> dsNguoiDung = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false){
            NguoiDung ee = new NguoiDung();
            ee.setUserName(c.getString(0));
            ee.setPassword(c.getString(1));
            ee.setPhone(c.getString(2));
            ee.setHoTen(c.getString(3));
            dsNguoiDung.add(ee);
            Log.d("//=====", ee.toString());
             c.moveToNext();
        }
        c.close();
        return dsNguoiDung;
    }

    //Update Data
    public int updateNguoiDung(NguoiDung nd){
        ContentValues values = new ContentValues();
        values.put("username", nd.getUserName());
        values.put("password", nd.getPassword());
        values.put("phone", nd.getPhone());
        values.put("hoten", nd.getHoTen());

        int result = db.update(TABLE_NAME, values, "username=?", new String[]{nd.getUserName()});
        if (result == 0 ){
            return -1;
        }
        return 1;
    }

    //Change Password User
    public int changePasswordNguoiDung(NguoiDung nd){
        ContentValues values = new ContentValues();
        values.put("username", nd.getUserName());
        values.put("password", nd.getPassword());
        int result = db.update(TABLE_NAME, values, "password=?", new String[]{nd.getPassword()});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int updateInfoNguoiDung(String username, String phone, String name){
        ContentValues values = new ContentValues();
        values.put("phone", phone);
        values.put("hoten", name);
        int result = db.update(TABLE_NAME, values, "username=?", new String[]{username});
        if (result == 0 ){
            return -1;
        }
        return 1;
    }

    //Delete Data

    public int deleteNguoiDungByID(String username){
        int result = db.delete(TABLE_NAME, "username=?", new String[]{username});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    //Check Login

    public int checkLoginOne(String username, String password){
        int result = db.delete(TABLE_NAME, "username=? AND password=?", new String[]{username, password} );
        if (result == 0){
            return -1;
        }
        return 1;
    }

}
