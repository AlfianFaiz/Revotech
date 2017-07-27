package com.alfianfaiz.app.revotech.AdapterMain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alfianfaiz.app.revotech.Interface.ItemClickListener;
import com.alfianfaiz.app.revotech.R;
import com.alfianfaiz.app.revotech.TakePicActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by USRER on 3/20/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainHolder> {
    Context c;
    ArrayList<TabelAttach> tabelAttaches;

    TakePicActivity takePicActivity;
    public MainAdapter(Context c, ArrayList<TabelAttach> dataTabels) {
        this.c = c;
        this.tabelAttaches = dataTabels;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pict,null,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        v.setLayoutParams(layoutParams);

        MainHolder holder=new MainHolder(v);
        takePicActivity = new TakePicActivity();
        return holder;
    }

    @Override
    public void onBindViewHolder(final MainHolder holder, int position) {

        String imageURL = tabelAttaches.get(position).getImgUrl();
        Glide.with(c).load(imageURL).into(holder.img);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //Toast.makeText(c,tabelAttaches.get(pos).getImgUrl(),Toast.LENGTH_SHORT).show();
                switch (v.getId()) {
                    case R.id.imageMain:
                        ((TakePicActivity) c).setImageToUtama(tabelAttaches.get(pos).getImgUrl());
                        break;
                    case R.id.button_X:
                        ((TakePicActivity)c).hapusAttach(tabelAttaches.get(pos).getIdAttach(),tabelAttaches.get(pos).getImgUrl());
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
        ((TakePicActivity)c).setImageToUtama(tabelAttaches.get(0).getImgUrl());
    }
}