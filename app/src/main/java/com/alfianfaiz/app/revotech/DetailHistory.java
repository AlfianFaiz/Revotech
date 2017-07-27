package com.alfianfaiz.app.revotech;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alfianfaiz.app.revotech.AdapterDetailHistory.DetailHistoryAdapter;
import com.alfianfaiz.app.revotech.AdapterMain.MainAdapter;
import com.alfianfaiz.app.revotech.AdapterMain.TabelAttach;
import com.alfianfaiz.app.revotech.DBClass.DBAdapter;
import com.bumptech.glide.Glide;
import com.frosquivel.magicalcamera.Functionallities.PermissionGranted;
import com.frosquivel.magicalcamera.MagicalCamera;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class DetailHistory extends AppCompatActivity implements View.OnClickListener{

    TextView txtJudul,txtTanggal;
    ImageView imageUtama;
    RecyclerView recyclerView;
    DBAdapter db = new DBAdapter(this);
    String visitId,AtmId,Hub,Tanggal;

    DetailHistoryAdapter detailHistoryAdapter;
    ArrayList<TabelAttach> tabelAttaches = new ArrayList<>();

    String UURRLL;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_upload_delete, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);

        UURRLL = "http://www.ptrevotech.com/cameroo/upload2.php";

        txtJudul = (TextView)findViewById(R.id.txtJudul);
        txtTanggal = (TextView)findViewById(R.id.txtTanggal);
        imageUtama = (ImageView)findViewById(R.id.imgUtama);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerMain);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        detailHistoryAdapter = new DetailHistoryAdapter(this,tabelAttaches);
        db=new DBAdapter(this);

            Bundle extras = getIntent().getExtras();
            visitId = extras.getString("IDVISIT");
            AtmId = extras.getString("IDATM");
            Hub = extras.getString("HUB");
            Tanggal = extras.getString("TANGGAL");

        txtJudul.setText(Hub+" - "+AtmId);
        txtTanggal.setText(Tanggal);

        imageUtama.setOnClickListener(this);

        retrieve();
        detailHistoryAdapter.startAct();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnUpload:
                ActionUpload();
                break;
            case  R.id.btnDelete:
                ActionHapus();
                break;
            default:
                break;
        }

        return true;
    }
    @Override
    public void onClick(View v) {

    }
    private void ActionHapus(){
        new MaterialDialog.Builder(this)
                .title("Delete Visit")
                .content("Delete this visit history?")
                .positiveText("YES")
                .negativeText("CANCEL")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        for (int i =0; i<tabelAttaches.size();i++){
                            //Toast.makeText(this,tabelAttaches.get(i).getImgUrl(),Toast.LENGTH_SHORT).show();
                            try {
                                File filess= new File(tabelAttaches.get(i).getImgUrl());
                                filess.delete();

                                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                scanIntent.setData(Uri.fromFile(filess));
                                sendBroadcast(scanIntent);
                            }
                            catch (Exception e)
                            {

                            }
                        }
                        //DetailHistory.this.finish();
                        hapusViisit(visitId);
                    }
                })
                .show();
    }
    private void ActionUpload() {
        final String nama = visitId;

        new MaterialDialog.Builder(this)
                .title("Upload Visit Attachmnet")
                .content("Upload all "+tabelAttaches.size()+" captured attachment?")
                .positiveText("YES")
                .negativeText("CANCEL")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        for (int i = 0; i <tabelAttaches.size(); i++){
                           // Toast.makeText(DetailHistory.this,tabelAttaches.get(i).getImgUrl(),Toast.LENGTH_SHORT).show();
                            String fileLoc = tabelAttaches.get(i).getImgUrl();
                            uploadAll(fileLoc,nama);
                        }
                    }
                })
                .show();
    }

    public void hapusViisit(final String visitId){
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
        DetailHistory.this.finish();
    }

    public void setImageToUtama(String resource){
        Glide.with(this).load(resource).into(imageUtama);
    }

    public void uploadAll(String fileLoc, String name){
        try {
            String uploadId = UUID.randomUUID().toString();

            new MultipartUploadRequest(this, uploadId, UURRLL)
                    .addFileToUpload(fileLoc, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void retrieve()
    {

        //OPEN
        db.openDB();
        tabelAttaches.clear();
        //SELECT
        Cursor c=db.showAttach(visitId);
        //LOOP THRU THE DATA ADDING TO ARRAYLIST
        while (c.moveToNext())
        {
            String xIdAttach=c.getString(0);
            String xIdVisit=c.getString(1);
            String ximgURL=c.getString(2);
            String xIdATM=c.getString(3);
            String xHub=c.getString(4);
            String xTanggal=c.getString(5);
            //CREATE PLAYER
            TabelAttach dt=new TabelAttach(xIdAttach,xIdVisit,ximgURL,xIdATM,xHub,xTanggal);
            //ADD TO PLAYERS
            tabelAttaches.add(dt);
        }
        //SET ADAPTER TO RV
        if(tabelAttaches.size()>-1)
        {
            recyclerView.setAdapter(detailHistoryAdapter);
        }
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieve();
    }


}
