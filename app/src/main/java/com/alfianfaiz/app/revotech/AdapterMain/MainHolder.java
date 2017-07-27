package com.alfianfaiz.app.revotech.AdapterMain;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alfianfaiz.app.revotech.Interface.ItemClickListener;
import com.alfianfaiz.app.revotech.R;

/**
 * Created by USRER on 3/21/2017.
 */

public class MainHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView img,buttonX;
    ItemClickListener itemClickListener;
    public MainHolder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.imageMain);
        buttonX = (ImageView) itemView.findViewById(R.id.button_X);

        img.setOnClickListener(this);
        buttonX.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());
    }
    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }
}
