package hust.edu.vn.timem.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hust.edu.vn.timem.Activity.NoteActivity;
import hust.edu.vn.timem.Data.SQLiteNote;
import hust.edu.vn.timem.Holder.BaseHolder;
import hust.edu.vn.timem.Model.NoteModel;
import hust.edu.vn.timem.R;

public class BaseRecycleViewAdapter extends RecyclerView.Adapter<BaseHolder> {

    Context context;
    List<NoteModel> object_list;
    BaseAdapterClickListener clickListener;
    public BaseRecycleViewAdapter(Context context, List<NoteModel> object_list) {
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
        String url = object_list.get(i).getUrlImage();
//        if(url.equals("")) url=",;";
//        final String [] ds = url.split(",;",-1);
//
//        final String [] list_url = ds[0].split(";",-1);
//        final  String [] comment = ds[1].split(";",-1);
//        Log.i("URL", list_url.length+" "+comment.length +""+url);
//        Log.i("URL", ds.length+"");
        final String [] list_url = url.split(";",-1);
        if(list_url.length>0){
            baseHolder.img_note.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(list_url[0]);
            baseHolder.img_note.setImageURI(null);
            baseHolder.img_note.setImageURI(uri);
        }else baseHolder.img_note.setVisibility(View.GONE);

        baseHolder.card_item_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context, R.style.Theme_Dialog);
                dialog.setContentView(R.layout.activity_add_note);
               // dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setTitle("Edit note");
                dialog.setCancelable(false);
                final EditText edt_title;
                final EditText edt_mota;
                final ImageView mic_title;
                final ImageView mic_mota;
                final LinearLayout lst_image;
                Button btn_cancel;
                Button btn_save;
                edt_title = dialog.findViewById(R.id.edt_title);
                edt_mota = dialog.findViewById(R.id.edt_mota);
                mic_mota = dialog.findViewById(R.id. mic_mota);
                mic_title = dialog.findViewById(R.id.mic_title);
                lst_image = dialog.findViewById(R.id.lst_image);
                edt_title.setText(object_list.get(i).title);
                edt_mota.setText(object_list.get(i).mota);
                for(int j=0;j<list_url.length;j++){
                    String s = list_url[j];
//                    String c = comment[j];
                    Uri uri = Uri.parse(s);
                    ImageView img = new ImageView(context);
                    img.setImageURI(uri);
                    EditText txt = new EditText(context);
//                    if(c.equals("")){
//                        txt.setHint("no comment");
//                    }else txt.setText(c);
//                    lst_image.addView(txt);
                    lst_image.addView(img);
                }
                for(String s:list_url){

                }

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
                            NoteModel model = new NoteModel(object_list.get(i).id, time_now, title, mota, object_list.get(i).getUrlImage());
                            db_note.update(model, object_list.get(i).id);

                        }else{
                        Toast.makeText(context, "Thất bại ! Tiêu đề phải khác rỗng ! ", Toast.LENGTH_SHORT).show();
                    }
                        lst_image.removeAllViews();
                        dialog.dismiss();

                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        lst_image.removeAllViews();
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
