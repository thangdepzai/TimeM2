package hust.edu.vn.timem.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import hust.edu.vn.timem.Adapter.BaseAdapter;
import hust.edu.vn.timem.Data.SQLiteNote;
import hust.edu.vn.timem.R;

public class NoteActivity extends AppCompatActivity {

    FloatingActionButton btn_add_note;

    RecyclerView lst_note;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        lst_note = findViewById(R.id.lst_note);
        btn_add_note = findViewById(R.id.btn_add_note);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        lst_note.setLayoutManager(gridLayoutManager);

        DataItem();
        btn_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText edt_title;
                final EditText edt_mota;

                Button btn_cancel;
                Button btn_save;
                AlertDialog.Builder dialogBuilder =	new AlertDialog.Builder(NoteActivity.this);
                LayoutInflater inflater	= NoteActivity.this.getLayoutInflater();
                @SuppressLint("ResourceType") View dialogView	=	inflater.inflate(R.layout.activity_add_note, (ViewGroup)findViewById(R.layout.activity_note));
                edt_title = dialogView.findViewById(R.id.edt_title);
                edt_mota = dialogView.findViewById(R.id.edt_mota);

                dialogBuilder.setView(dialogView);
                dialogBuilder.setTitle("Add Note");
                final AlertDialog b = dialogBuilder.create();

                btn_cancel= dialogView.findViewById(R.id.cancel);
                btn_save = dialogView.findViewById(R.id.save);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd-MM-yyyy");
                        String time_now = sdf.format(new Date());
                        SQLiteNote db_note = new SQLiteNote(getApplicationContext());
                        db_note.addData(time_now, edt_title.getText().toString(), edt_mota.getText().toString());
                        DataItem();
                        b.dismiss();

                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        b.dismiss();
                    }
                });
                b.show();

            }

            });



    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_note, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.mn_refresh:
//                DataItem();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    private void DataItem() {
        SQLiteNote db_note = new SQLiteNote(this);
        BaseAdapter adapter = new BaseAdapter(this, db_note.getData());
        adapter.notifyDataSetChanged();
        lst_note.setAdapter(adapter);


    }
}
