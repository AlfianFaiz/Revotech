package com.alfianfaiz.app.revotech.AdapterHistory;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfianfaiz.app.revotech.Interface.ItemClickListener;
import com.alfianfaiz.app.revotech.R;

/**
 * Created by USRER on 3/23/2017.
 */

public class HistoryHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView txtTanggal, txtHUB, txtATM;
    CardView cardView;
   // ImageView imgRemove;
    ItemClickListener itemClickListener;
    public HistoryHolder(View itemView) {
        super(itemView);
        txtTanggal = (TextView) itemView.findViewById(R.id.txtTanggal);
        txtHUB = (TextView) itemView.findViewById(R.id.txtHub);
        txtATM = (TextView) itemView.findViewById(R.id.txtIdAtm);
        //imgRemove = (ImageView) itemView.findViewById(R.id.hapus);
        cardView = (CardView) itemView.findViewById(R.id.card_view);

        cardView.setOnClickListener(this);
       // imgRemove.setOnClickListener(this);
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
