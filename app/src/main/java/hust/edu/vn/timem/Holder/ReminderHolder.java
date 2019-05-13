package hust.edu.vn.timem.Holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hust.edu.vn.timem.R;
import hust.edu.vn.timem.Receiver.AlarmReceiver;

public class ReminderHolder extends RecyclerView.ViewHolder {

    public ImageView thumbnail_image, active_image;
    public TextView recycle_title, recycle_date_time, recycle_repeat_info;
    public CardView card_reminder;
    public AlarmReceiver alarmReceiver;
    public ReminderHolder(View itemView) {
        super(itemView);
        recycle_title = itemView.findViewById(R.id.recycle_title);
        recycle_date_time = itemView.findViewById(R.id.recycle_date_time);
        recycle_repeat_info = itemView.findViewById(R.id.recycle_repeat_info);
        thumbnail_image = itemView.findViewById(R.id.thumbnail_image);
        active_image = itemView.findViewById(R.id.active_image);
        card_reminder = itemView.findViewById(R.id.card_reminder);
        alarmReceiver = new AlarmReceiver();
    }
}