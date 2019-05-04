package hust.edu.vn.timem.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hust.edu.vn.timem.Activity.NoteActivity;
import hust.edu.vn.timem.Data.SQLiteNote;
import hust.edu.vn.timem.Holder.BaseHolder;
import hust.edu.vn.timem.Model.NoteModel;
import hust.edu.vn.timem.R;

public class BaseAdapter extends RecyclerView.Adapter<BaseHolder> {

    Context context;
    List<NoteModel> object_list;
    BaseAdapterClickListener clickListener;
    public BaseAdapter(Context context, List<NoteModel> object_list) {
        this.context = context;
        this.object_list = object_list;
    }

    public interface BaseAdapterClickListener {
        void testClick(String waktu);
    }
    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_note, null);
        BaseHolder holder = new BaseHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseHolder baseHolder, final int i) {
        baseHolder.txt_time.setText(object_list.get(i).time);
        baseHolder.txt_title.setText(object_list.get(i).title);
        baseHolder.txt_mota.setText(object_list.get(i).mota);

        baseHolder.card_item_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
                dialog.setContentView(R.layout.activity_add_note);
               // dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setTitle("Edit note");

                final EditText edt_title;
                final EditText edt_mota;
                final ImageView mic_title;
                final ImageView mic_mota;

                Button btn_cancel;
                Button btn_save;
                edt_title = dialog.findViewById(R.id.edt_title);
                edt_mota = dialog.findViewById(R.id.edt_mota);
                mic_mota = dialog.findViewById(R.id. mic_mota);
                mic_title = dialog.findViewById(R.id.mic_title);

                edt_title.setText(object_list.get(i).title);
                edt_mota.setText(object_list.get(i).mota);

                mic_mota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((NoteActivity)context).startSpeechToText(NoteActivity.SPEECH_MOTA_RECOGNITION_CODE, edt_mota);
                    }
                });
                mic_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((NoteActivity)context).startSpeechToText(NoteActivity.SPEECH_TITLE_RECOGNITION_CODE, edt_title);

                    }
                });





                btn_cancel= dialog.findViewById(R.id.cancel);
                btn_save = dialog.findViewById(R.id.save);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(! edt_title.getText().toString().equals("")) {
                            String mota = edt_mota.getText().toString();
                            String title = edt_title.getText().toString();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd-MM-yyyy");
                            String time_now = sdf.format(new Date());
                            // update view

                            baseHolder.txt_title.setText(title);
                            baseHolder.txt_mota.setText(mota);
                            baseHolder.txt_time.setText(time_now);
                            // update object
                            object_list.get(i).time = time_now;
                            object_list.get(i).mota = mota;
                            object_list.get(i).title = title;
                            // update database
                            SQLiteNote db_note = new SQLiteNote(context);
                            NoteModel model = new NoteModel(object_list.get(i).id, time_now, title, mota);
                            db_note.update(model, object_list.get(i).id);

                        }else{
                            Toast.makeText(context, "Thất bại ! Tiêu đề phải khác rỗng ! ", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();

                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }


        });


        baseHolder.card_item_note.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                // xoa database
                                SQLiteNote db_note = new SQLiteNote(context);
                                db_note.deleteItemKey(object_list.get(i).id);
                                //cap nhat giao dien
                                object_list.remove(i);
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

    @Override
    public int getItemCount() {
        return object_list.size();
    }
}
