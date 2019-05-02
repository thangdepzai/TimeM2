package hust.edu.vn.timem.Holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hust.edu.vn.timem.R;

public class BaseHolder extends RecyclerView.ViewHolder {


    public TextView txt_time;
    public TextView txt_title;
    public TextView txt_mota;
    public ImageView img_note;
    public CardView card_item_note;

    public BaseHolder(View itemView) {
        super(itemView);
        txt_time =  itemView.findViewById(R.id.txt_time);
        txt_mota = itemView.findViewById(R.id.txt_mota);
        txt_title =  itemView.findViewById(R.id.txt_title);
        img_note =  itemView.findViewById(R.id.img_note);
        card_item_note =  itemView.findViewById(R.id.card_item_note);
    }
}
