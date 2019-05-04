package hust.edu.vn.timem.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import hust.edu.vn.timem.Adapter.BaseAdapter;
import hust.edu.vn.timem.Data.SQLiteNote;
import hust.edu.vn.timem.R;

public class NoteActivity extends AppCompatActivity {

    FloatingActionButton btn_add_note;
    ImageButton btn_filter;

    View edt;
    RecyclerView lst_note;
    public static final int SPEECH_MOTA_RECOGNITION_CODE = 1;
    public static final int SPEECH_TITLE_RECOGNITION_CODE = 2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        lst_note = findViewById(R.id.lst_note);
        btn_add_note = findViewById(R.id.btn_add_note);
        btn_filter = findViewById(R.id.btn_filter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        lst_note.setLayoutManager(gridLayoutManager);
        DataItem();
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(NoteActivity.this, btn_filter);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_note, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                NoteActivity.this,
                                "You Clicked : " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });



        btn_add_note.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                final EditText edt_title;
                final EditText edt_mota;
                final ImageView mic_mota;
                final ImageView mic_title;

                Button btn_cancel;
                Button btn_save;
                AlertDialog.Builder dialogBuilder =	new AlertDialog.Builder(NoteActivity.this);
                LayoutInflater inflater	= NoteActivity.this.getLayoutInflater();
                @SuppressLint("ResourceType")
                View dialogView	=	inflater.inflate(R.layout.activity_add_note, (ViewGroup)findViewById(R.layout.activity_note));
                edt_title = dialogView.findViewById(R.id.edt_title);
                edt_mota = dialogView.findViewById(R.id.edt_mota);
                mic_mota = dialogView.findViewById(R.id.mic_mota);
                mic_title = dialogView.findViewById(R.id.mic_title);

                dialogBuilder.setView(dialogView);
                dialogBuilder.setTitle("Add note");
                final AlertDialog b = dialogBuilder.create();

                btn_cancel= dialogView.findViewById(R.id.cancel);
                btn_save = dialogView.findViewById(R.id.save);
                mic_mota.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startSpeechToText(SPEECH_MOTA_RECOGNITION_CODE,  edt_mota);
                    }

                });
                mic_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startSpeechToText(SPEECH_TITLE_RECOGNITION_CODE,edt_title);
                    }
            });
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!edt_title.getText().toString().equals("")){
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd-MM-yyyy");
                            String time_now = sdf.format(new Date());
                            SQLiteNote db_note = new SQLiteNote(getApplicationContext());
                            db_note.addData(time_now, edt_title.getText().toString(), edt_mota.getText().toString());
                            DataItem();
                        }else Toast.makeText(NoteActivity.this, "Thất bại ! Tiêu đề phải khác rỗng ! ", Toast.LENGTH_SHORT).show();

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



    public void startSpeechToText(int code, View v) {
        this.edt = v;
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak something...");
        try {
            startActivityForResult(intent, code);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_MOTA_RECOGNITION_CODE:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    ((EditText)edt).setText(text);
                }
                break;
            case SPEECH_TITLE_RECOGNITION_CODE:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    ((EditText)edt).setText(text);
                }
                break;
        }
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
