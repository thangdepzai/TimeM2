package hust.edu.vn.timem.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import hust.edu.vn.timem.Activity.AddNoteActivity;
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
    public void onBindViewHolder(@NonNull BaseHolder baseHolder, final int i) {
        baseHolder.txt_time.setText(object_list.get(i).time);
        baseHolder.txt_title.setText(object_list.get(i).title);
        baseHolder.txt_mota.setText(object_list.get(i).mota);

        baseHolder.card_item_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddNoteActivity.class);
                intent.putExtra("title", object_list.get(i).title);
                intent.putExtra("mota", object_list.get(i).mota);
                view.getContext().startActivity(intent);
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
                                SQLiteNote db_note = new SQLiteNote(context);
                                db_note.deleteItemSelected(object_list.get(i).time);
                                //db_note.getData();
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
