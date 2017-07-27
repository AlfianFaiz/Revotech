package com.alfianfaiz.app.revotech.DBClass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by USRER on 3/20/2017.
 */

public class DBAdapter {
    Context c;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper = new DBHelper(c);
    }

    //OPEN DB
    public DBAdapter openDB()
    {
        try
        {
            db=helper.getWritableDatabase();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return this;
    }
    //CLOSE
    public void close()
    {
        try
        {
            helper.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //ADD ATTACH
    public long add_attach(String attach, String visit, String url, String atm, String hub, String tanggal){
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constants.ATTACH_ID,attach);
            contentValues.put(Constants.VISIT_ID,visit);
            contentValues.put(Constants.IMAGE_URL,url);
            contentValues.put(Constants.ATM_ID,atm);
            contentValues.put(Constants.HUB,hub);
            contentValues.put(Constants.TANGGAL,tanggal);

           // Toast.makeText(c,"Attach ID: Berhasil Dibuat",Toast.LENGTH_SHORT).show();
            return db.insert(Constants.TB_ATTACH,null,contentValues);

        }catch (SQLException e){
            e.printStackTrace();
           // Toast.makeText(c,"Attach ID: Sudah Ada",Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    //SHOW ALL VISIT
    public Cursor showVisit()
    {
        String[] columns = {Constants.VISIT_ID,Constants.ATM_ID,Constants.HUB,Constants.TANGGAL};
        return db.query(true,Constants.TB_ATTACH,columns,null,null,null,null,Constants.TANGGAL+" DESC",null);
    }

    //SHOW ATTACH PER VISIT
    public Cursor showAttach(String visit)
    {
        String[] columns = {Constants.ATTACH_ID,Constants.VISIT_ID,Constants.IMAGE_URL,Constants.ATM_ID,Constants.HUB,Constants.TANGGAL};
        return db.query(Constants.TB_ATTACH,columns,Constants.VISIT_ID+"=?",new String[]{visit},null,null,null);
    }


    //DELETE VISIT
    public long delete_visit(String id)
    {
        try {
            return db.delete(Constants.TB_ATTACH,Constants.VISIT_ID+"=?",new String[]{String.valueOf(id)});
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //DELETE ATTACH
    public long delete_attach(String id)
    {
        try {
            return db.delete(Constants.TB_ATTACH,Constants.ATTACH_ID+"=?",new String[]{String.valueOf(id)});
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }






}
