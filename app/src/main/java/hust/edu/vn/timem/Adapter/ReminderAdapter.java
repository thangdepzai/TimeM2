package hust.edu.vn.timem.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hust.edu.vn.timem.Activity.CalendarActivity;
import hust.edu.vn.timem.Activity.MainActivity;
import hust.edu.vn.timem.Activity.ReminderEditActivity;
import hust.edu.vn.timem.Data.ReminderDatabase;
import hust.edu.vn.timem.Data.SQLiteNote;

import hust.edu.vn.timem.Holder.ReminderHolder;

import hust.edu.vn.timem.Model.Reminder;
import hust.edu.vn.timem.R;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderHolder> {

    Context context;
    List<Reminder> object_list;
    BaseRecycleViewAdapter.BaseAdapterClickListener clickListener;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;
    public ReminderAdapter(Context context, List<Reminder> object_list) {
        this.context = context;
        this.object_list = object_list;
    }

    private OnItemClickListener mListener;
    @NonNull
    @Override
    public ReminderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reminder_recycleview, null);
        ReminderHolder holder = new ReminderHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReminderHolder baseHolder, final int i) {
        baseHolder.recycle_title.setText(object_list.get(i).getTitle());
        baseHolder.recycle_date_time.setText(object_list.get(i).getDate()+", "+object_list.get(i).getTime());
        baseHolder.recycle_repeat_info.setText(object_list.get(i).getRepeat());

        if(object_list.get(i).getRepeat().equals("true")){
            baseHolder.recycle_repeat_info.setText("Every " + object_list.get(i).getRepeatNo() + " " + object_list.get(i).getRepeatType() + "(s)");
        }else if (object_list.get(i).getRepeat().equals("false")) {
            baseHolder.recycle_repeat_info.setText("Repeat Off");
        }

        if(object_list.get(i).getActive().equals("true")){
            baseHolder.active_image.setImageResource(R.drawable.ic_notifications_on_white_24dp);
        }else if (object_list.get(i).getActive().equals("false")) {
            baseHolder.active_image.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
        }


        String letter = object_list.get(i).getTime();

//        if(object_list.get(i).getTitle() != null && !object_list.get(i).getTitle().isEmpty()) {
//            letter = object_list.get(i).getTitle().substring(0, 1);
//        }

        int color = mColorGenerator.getRandomColor();
        baseHolder.card_reminder.setCardBackgroundColor(color);
        int color1 = mColorGenerator.getRandomColor();
        while(color==color1){
            color1 = mColorGenerator.getRandomColor();
        }
        // Create a circular icon consisting of  a random background colour and first letter of title
        mDrawableBuilder = TextDrawable.builder().beginConfig().fontSize(35).bold().endConfig()
                .buildRound(letter, color1);
        baseHolder.thumbnail_image.setImageDrawable(mDrawableBuilder);

        baseHolder.card_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(object_list.get(i));
                }
            }
                });

                baseHolder.card_reminder.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        // xoa database
                                        ReminderDatabase db = new ReminderDatabase(context);
                                        db.deleteReminder(object_list.get(i));
                                        //cap nhat giao dien
                                        object_list.remove(i);
                                        baseHolder.alarmReceiver.cancelAlarm(context, object_list.get(i).getID());
                                        notifyItemRemoved(i);
                                        notifyItemRangeChanged(i, object_list.size());
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure to delete ?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
                        return true;
                    }
                });

            }
            public void setOnItemClickListener(OnItemClickListener listener) {
                     mListener = listener;
            }


            @Override
            public int getItemCount() {
                return object_list.size();
            }
            public interface OnItemClickListener {
                void onItemClick(Reminder reminder);
            }
        }




