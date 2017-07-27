package com.alfianfaiz.app.revotech;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{


    Button btTakePict, btHistory;
    Spinner idHub,idAtm;
    String[] HubList= {"Select HUB","HUB II", "HUB III", "HUB IV", "HUB VII"};
    ArrayAdapter<String> arrayHub, arrayAtm;
    TextView tTanggal;
    int year,month,day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        arrayHub = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, HubList);

        idAtm = (Spinner) findViewById(R.id.idATM);
        idHub = (Spinner) findViewById(R.id.idHUB);
        btTakePict = (Button) findViewById(R.id.btnTakePict);
        btHistory = (Button) findViewById(R.id.btnHistory);
        tTanggal = (TextView)findViewById(R.id.tTanggal);

        idAtm.setEnabled(false);

        idHub.setAdapter(arrayHub);

        idHub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                if (idHub.getSelectedItem().toString().trim() != "Select HUB")
                {
                    String a = HubList[position];

                    switch (a)
                    {
                        case "HUB II":
                            arrayAtm = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.HubII));
                            break;
                        case "HUB III":
                            arrayAtm = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.HubIII));
                            break;
                        case "HUB IV":
                            arrayAtm = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.HubIV));
                            break;
                        case "HUB VII":
                            arrayAtm = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.HubVII));
                            break;
                        default:
                            break;
                    }
                    idAtm.setAdapter(arrayAtm);
                    idAtm.setEnabled(true);
                }
                else {
                    idAtm.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String tanggal = dateFormat.format(calendar.getTime());

        tTanggal.setText(tanggal);

        Calendar c = Calendar.getInstance();

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        btTakePict.setOnClickListener(this);
        btHistory.setOnClickListener(this);
        tTanggal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTakePict :
                String getHub = idHub.getSelectedItem().toString().trim();
                String getAtm ="Select ATM";
                String getTanggal = tTanggal.getText().toString().trim();
                if (idAtm.isEnabled()){
                    getAtm = idAtm.getSelectedItem().toString().trim();
                }
                // Toast.makeText(this,getHub+"-"+getAtm,Toast.LENGTH_SHORT).show();

                if (getHub != "Select HUB" && getAtm != "Select ATM")
                {
                    Intent take = new Intent(HomeActivity.this, TakePicActivity.class);
                    take.putExtra("HUB",getHub);
                    take.putExtra("ATM",getAtm);
                    take.putExtra("TANGGAL",getTanggal);
                    startActivity(take);
                }
                break;
            case R.id.btnHistory:
                Intent his = new Intent(HomeActivity.this, HistoryActivity.class);
                startActivity(his);
                break;

            case R.id.tTanggal:
                setTanggal();
                break;
            default:
                break;
        }

    }
    private void setTanggal(){

        DatePickerDialog dateDlg = new DatePickerDialog(HomeActivity.this, new DatePickerDialog.OnDateSetListener()
        {

            public void onDateSet(DatePicker view, int Year, int monthOfYear, int dayOfMonth)
            {
                CharSequence strDate = null;
                Time chosenDate = new Time();
                chosenDate.set(dayOfMonth, monthOfYear, Year);
                long dtDob = chosenDate.toMillis(true);

                strDate = DateFormat.format("dd-MM-yyyy", dtDob);

                tTanggal.setText(strDate);
                year = Year;
                month = monthOfYear;
                day = dayOfMonth;
            }}, year,month,day);

        dateDlg.show();
    }

}
