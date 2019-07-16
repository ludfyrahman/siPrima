package com.android.primaitech.siprima.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.primaitech.siprima.Database.Model.Role_User;

public class Database_Helper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "siprima";
    public Database_Helper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Role_User.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Role_User.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
    public void truncateRole_User(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+Role_User.TABLE_NAME);
    }

    public long insertRole_User(String kode_role, String nama_role, String revisi_code) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Role_User.final_revisi_code, revisi_code);
        values.put(Role_User.final_nama_role, nama_role);
        values.put(Role_User.final_kode_role, kode_role);
        // insert row
        long id = db.insert(Role_User.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public Role_User getRole_UserDetail(String kode_role) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Role_User.TABLE_NAME,
                new String[]{Role_User.final_kode_role, Role_User.final_nama_role, Role_User.final_revisi_code},
                Role_User.final_kode_role + "=?",
                new String[]{String.valueOf(kode_role)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Role_User role = new Role_User(
                cursor.getString(cursor.getColumnIndex(Role_User.final_kode_role)),
                cursor.getString(cursor.getColumnIndex(Role_User.final_nama_role)),
                cursor.getString(cursor.getColumnIndex(Role_User.final_revisi_code)));

        // close the db connection
        cursor.close();

        return role;
    }
    public int getRoleUserCount() {
        String countQuery = "SELECT  * FROM " + Role_User.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
}
