package com.alfianfaiz.app.revotech;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.alfianfaiz.app.revotech.DBClass.DBHelper;

public class SplashScreenActivity extends AppCompatActivity {

    private int SPLASHINTERVAL = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent homeAct = new Intent(SplashScreenActivity.this, HomeActivity.class);
                startActivity(homeAct);

            }
        },SPLASHINTERVAL);

//        DBHelper dbHelper = new DBHelper(this);
//        dbHelper.onCreate();


    }
}
