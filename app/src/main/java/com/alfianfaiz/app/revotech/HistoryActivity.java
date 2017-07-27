package com.alfianfaiz.app.revotech;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alfianfaiz.app.revotech.AdapterHistory.HistoryAdapter;
import com.alfianfaiz.app.revotech.AdapterMain.TabelAttach;
import com.alfianfaiz.app.revotech.DBClass.DBAdapter;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;

    ArrayList<TabelAttach> tabelAttaches = new ArrayList<>();
    DBAdapter db = new DBAdapter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        historyAdapter=new HistoryAdapter(this,tabelAttaches);
        retrieve();
    }


    public void hapusViisit(final String visitId){
        new MaterialDialog.Builder(this)
                .title("Delete Visit")
                .content("Are you sure to delete this visit")
                .positiveText("YES")
                .negativeText("CANCEL")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        db.openDB();
                        db.delete_visit(visitId);
                        long result= db.delete_visit(visitId);
                        if(result>0)
                        {
                            //Toast.makeText(HistoryActivity.this,"failed ",Toast.LENGTH_SHORT).show();
                            retrieve();
                        }else
                        {
                            retrieve();
                        }
                        db.close();
                    }
                })
                .show();
    }

    private void retrieve()
    {
        db.openDB();
        tabelAttaches.clear();
        Cursor c = db.showVisit();
        while (c.moveToNext())
        {
            String xIdVisit=c.getString(0);
            String xIdATM=c.getString(1);
            String xHub=c.getString(2);
            String xTanggal=c.getString(3);

            TabelAttach dt=new TabelAttach(null,xIdVisit,null,xIdATM,xHub,xTanggal);

            tabelAttaches.add(dt);
        }
        if(tabelAttaches.size()>-1)
        {
            recyclerView.setAdapter(historyAdapter );
        }
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieve();
    }

}
