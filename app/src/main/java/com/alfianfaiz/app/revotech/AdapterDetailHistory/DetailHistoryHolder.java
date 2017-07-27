package com.alfianfaiz.app.revotech.AdapterDetailHistory;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alfianfaiz.app.revotech.Interface.ItemClickListener;
import com.alfianfaiz.app.revotech.R;

/**
 * Created by USRER on 3/27/2017.
 */

public class DetailHistoryHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView img,buttonX;
    ItemClickListener itemClickListener;
    public DetailHistoryHolder (View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.imageMain);

        img.setOnClickListener(this);
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
