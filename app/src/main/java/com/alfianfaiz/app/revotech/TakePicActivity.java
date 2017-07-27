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

public class TakePicActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imageUtama;
    TextView txtJudul;
    MainAdapter mainAdapter;
    RecyclerView recyclerView;
    Button btnTakePict,btnBrowse;
    ArrayList<TabelAttach> tabelAttaches = new ArrayList<>();
    String hub,atm,judul;
    Calendar calendar;
    String tanggal,idVisit,idAttach,tgl;
    DBAdapter db;

   // RecyclerViewMargin recyclerViewMargin;

   // private String name = "";
    private String folder = "Al-Faiz/";

    private MagicalCamera magicalCamera;
    private PermissionGranted permissionGranted;
    private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 30;
    private int STORAGE_PERMISSION_CODE = 88;

    private String path= null;
    String UURRLL;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_upload, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pic);

        //buat tanggal
        //calendar = Calendar.getInstance();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        //tanggal = dateFormat.format(calendar.getTime());
        //String tgl = tanggal.replaceAll("-","");

        UURRLL = "http://www.ptrevotech.com/cameroo/upload2.php";

       // dbHelper =new DBHelper(this);

        permissionGranted = new PermissionGranted(this);
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            permissionGranted.checkAllMagicalCameraPermission();
        }else{
            permissionGranted.checkCameraPermission();
            permissionGranted.checkReadExternalPermission();
            permissionGranted.checkWriteExternalPermission();
            permissionGranted.checkLocationPermission();
        }
        magicalCamera = new MagicalCamera(this, RESIZE_PHOTO_PIXELS_PERCENTAGE, permissionGranted);

//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        txtJudul = (TextView)findViewById(R.id.txtJudul);
        imageUtama = (ImageView)findViewById(R.id.imgUtama);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerMain);

        //recyclerViewMargin = new RecyclerViewMargin(5,5);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        mainAdapter = new MainAdapter(this,tabelAttaches);
        db=new DBAdapter(this);

            Bundle extras = getIntent().getExtras();
            hub = extras.getString("HUB");
            atm =  extras.getString("ATM");
            tanggal = extras.getString("TANGGAL");
            judul = hub + " - " + atm ;

        txtJudul.setText(judul);

        tgl = tanggal.replaceAll("-","");
        idVisit = tgl+atm;
        //txtJudul.setText(idVisit);

        //Toast.makeText(this,idVisit,Toast.LENGTH_SHORT).show();
        imageUtama.setOnClickListener(this);

        retrieve();

        if (tabelAttaches.size()>0){
            //Glide.with(this).load()
            mainAdapter.startAct();
        }
    }
    @Override
    public void onClick(View v) {
        if(isReadStorageAllowed()) {
            switch (v.getId()) {
                // action with ID action_refresh was selected
                case R.id.imgUtama:
                    showDialog();
                    break;
                case R.id.button_X:

                    break;
                default:
                    break;
            }
        }
        else {
            requestStoragePermission();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.btnUpload:
            //    Toast.makeText(this, "Upload Coyyyyy", Toast.LENGTH_SHORT)
            //            .show();
                ActionUpload();
                break;
            case R.id.btnAddImage:
                showDialog();
                break;
            default:
                break;
        }

        return true;
    }

    private void ActionUpload() {
        final String nama = idVisit;

        new MaterialDialog.Builder(this)
                .title("Upload Visit Attachmnet")
                .content("Upload all "+tabelAttaches.size()+" captured attachment?")
                .positiveText("YES")
                .negativeText("CANCEL")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        for (int i = 0; i <tabelAttaches.size(); i++){
                    //        Toast.makeText(TakePicActivity.this,tabelAttaches.get(i).getImgUrl(),Toast.LENGTH_SHORT).show();
                            String fileLoc = tabelAttaches.get(i).getImgUrl();
                            uploadAll(fileLoc,nama);
                        }
                    }
                })
                .show();
    }

    private void showDialog()
    {
        final Dialog d=new Dialog(this);
        //NO TITLE
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //layout of dialog
        d.setContentView(R.layout.dialog_take_pic);
        btnTakePict= (Button) d.findViewById(R.id.btnTakePict);
        btnBrowse= (Button) d.findViewById(R.id.btnBrowse);
        //ONCLICK LISTENERS
        btnTakePict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
     //           Toast.makeText(TakePicActivity.this,"Take Picture",Toast.LENGTH_SHORT).show();
                magicalCamera.takePhoto();
               d.hide();
            }
        });
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
      //          Toast.makeText(TakePicActivity.this,"Browse",Toast.LENGTH_SHORT).show();
                magicalCamera.selectedPicture(atm);
                d.hide();
            }
        });
        //SHOW DIALOG
        d.show();
    }


    public void setImageToUtama(String resource){
        Glide.with(this).load(resource).into(imageUtama);
    }

    public void hapusAttachGalery(final String idGambar){
        try {
            File filess= new File(idGambar);
            filess.delete();

            Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            scanIntent.setData(Uri.fromFile(filess));
            sendBroadcast(scanIntent);
        }
        catch (Exception e)
        {

        }
    }
    public void hapusAttach(final String idGambar , final String pathGambar)
    {
        new MaterialDialog.Builder(this)
                .title("Delete Picture")
                .content("Are you sure to delete this picture?")
                .positiveText("YES")
                .negativeText("CANCEL")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        db.openDB();
                       // db.delete_visit(idGambar);
                        long result= db.delete_attach(idGambar);
                        if(result>0)
                        {
                           // Toast.makeText(TakePicActivity.this,"failed ",Toast.LENGTH_SHORT).show();
                            retrieve();
                            if (tabelAttaches.size()>0)
                            {
                                mainAdapter.startAct();
                            }else {
                                imageUtama.setImageResource(getResources().getIdentifier("com.alfianfaiz.app.revotech:drawable/noimage",null,null));
                            }
                        }else
                        {
                            retrieve();
                            if (tabelAttaches.size()>0)
                            {
                                mainAdapter.startAct();
                            }else {
                                imageUtama.setImageResource(getResources().getIdentifier("com.alfianfaiz.app.revotech:drawable/noimage",null,null));
                            }
                        }
                        hapusAttachGalery(pathGambar);
                        db.close();
                    }
                })
                .show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        magicalCamera.resultPhoto(requestCode, resultCode, data);

        if(magicalCamera.getPhoto()!=null) {
            if(magicalCamera.getPhoto().getHeight() < magicalCamera.getPhoto().getWidth())
            {
                magicalCamera.setPhoto(magicalCamera.rotatePicture(magicalCamera.getPhoto(), MagicalCamera.ORIENTATION_ROTATE_90));
            }
            else
            {
                magicalCamera.setPhoto(magicalCamera.rotatePicture(magicalCamera.getPhoto(), MagicalCamera.ORIENTATION_ROTATE_NORMAL));

            }
            imageUtama.setImageBitmap(magicalCamera.getPhoto());
            //path = "";
            path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), atm, folder + atm, MagicalCamera.JPEG, true);




            if (path != null) {
                calendar = Calendar.getInstance();
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
                String waktu = dateTimeFormat.format(calendar.getTime());
                idAttach = waktu.replaceAll("-","");

                saveAttachtoSQLite(idAttach,idVisit,path,atm,hub,tanggal);

//                Toast.makeText(TakePicActivity.this,
//                 "The photo is save in device, please check this path: " + path,Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(TakePicActivity.this,
                        "Sorry your photo dont write in devide, please contact your Administrator and say this error",
                        Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(TakePicActivity.this,
                    "Your image is null, please debug, or test with another device, thanks and sorry",
                    Toast.LENGTH_LONG).show();
        }
        magicalCamera.setPhoto(null);
        retrieve();
    }

    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }

    private void requestStoragePermission(){



        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
        }
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permissionGranted.permissionGrant(requestCode, permissions, grantResults);

        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void saveAttachtoSQLite(String id,String visit,String url, String atm, String hub, String tanggal)
    {

        //OPEN
        db.openDB();
        //INSERT
        long result=db.add_attach(id,visit,url,atm,hub,tanggal);
        if(result>0)
        {
            Toast.makeText(this,"success "+visit+" "+url,Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(this,"unable to create new",Toast.LENGTH_SHORT).show();
            //Snackbar.make(nameTxt,"Unable To Insert",Snackbar.LENGTH_SHORT).show();
        }
        //CLOSE
        db.close();
        //refresh
        retrieve();
    }

    private void retrieve()
    {

        //OPEN
        db.openDB();
        tabelAttaches.clear();
        //SELECT
        Cursor c=db.showAttach(idVisit);
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
            recyclerView.setAdapter(mainAdapter);
        }
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieve();
    }

}
