package com.alfianfaiz.app.revotech.AdapterHistory;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alfianfaiz.app.revotech.HistoryActivity;
import com.alfianfaiz.app.revotech.Interface.ItemClickListener;
import com.alfianfaiz.app.revotech.AdapterMain.TabelAttach;
import com.alfianfaiz.app.revotech.DBClass.DBAdapter;
import com.alfianfaiz.app.revotech.DetailHistory;
import com.alfianfaiz.app.revotech.R;

import java.util.ArrayList;

/**
 * Created by USRER on 3/23/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {
    Context c;
    ArrayList<TabelAttach> tabelAttaches;
    DBAdapter db = new DBAdapter(c);

    public HistoryAdapter(Context c, ArrayList<TabelAttach> tabelAttaches) {
        this.c = c;
        this.tabelAttaches = tabelAttaches;
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_history,null, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);

        HistoryHolder holder = new HistoryHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, int position) {
        holder.txtTanggal.setText(tabelAttaches.get(position).getTanggal());
        holder.txtHUB.setText(tabelAttaches.get(position).getHub());
        holder.txtATM.setText(tabelAttaches.get(position).getIdATM());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                switch (v.getId()){
                    case R.id.card_view:
                        Intent i = new Intent(c, DetailHistory.class);
                        i.putExtra("IDVISIT", tabelAttaches.get(pos).getIdVisit());
                        i.putExtra("IDATM", tabelAttaches.get(pos).getIdATM());
                        i.putExtra("HUB", tabelAttaches.get(pos).getHub());
                        i.putExtra("TANGGAL", tabelAttaches.get(pos).getTanggal());
                        c.startActivity(i);
                        break;
//                    case R.id.hapus:
//                        ((HistoryActivity)c).hapusViisit(tabelAttaches.get(pos).getIdVisit());
//                        break;
                    default:
                        break;
                }



            }
        });
    }

    @Override
    public int getItemCount() {
        return tabelAttaches.size();
    }

}