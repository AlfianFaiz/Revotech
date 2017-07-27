package com.alfianfaiz.app.revotech.AdapterDetailHistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfianfaiz.app.revotech.AdapterMain.TabelAttach;
import com.alfianfaiz.app.revotech.DetailHistory;
import com.alfianfaiz.app.revotech.Interface.ItemClickListener;
import com.alfianfaiz.app.revotech.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by USRER on 3/27/2017.
 */

public class DetailHistoryAdapter extends RecyclerView.Adapter<DetailHistoryHolder>{
    Context c;
    ArrayList<TabelAttach> tabelAttaches;

    DetailHistory detailHistory;
    public DetailHistoryAdapter(Context c, ArrayList<TabelAttach> dataTabels) {
        this.c = c;
        this.tabelAttaches = dataTabels;
    }

    @Override
    public DetailHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pic_detail,null,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        v.setLayoutParams(layoutParams);

        DetailHistoryHolder holder=new DetailHistoryHolder(v);
        detailHistory = new DetailHistory();
        return holder;
    }

    @Override
    public void onBindViewHolder(DetailHistoryHolder holder, int position) {
        String imageURL = tabelAttaches.get(position).getImgUrl();
        Glide.with(c).load(imageURL).into(holder.img);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //Toast.makeText(c,tabelAttaches.get(pos).getImgUrl(),Toast.LENGTH_SHORT).show();
                switch (v.getId()) {
                    case R.id.imageMain:
                        ((DetailHistory) c).setImageToUtama(tabelAttaches.get(pos).getImgUrl());
                        break;
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

    public void startAct(){
        ((DetailHistory)c).setImageToUtama(tabelAttaches.get(0).getImgUrl());
    }
}
