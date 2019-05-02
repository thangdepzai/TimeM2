package hust.edu.vn.timem.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

import hust.edu.vn.timem.Data.SQLiteNote;
import hust.edu.vn.timem.R;

public class AddNoteActivity extends AppCompatActivity {
    EditText txt_title, txt_mota;
    ImageView btn_Add;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        txt_title = findViewById(R.id.txt_title);
        txt_mota = findViewById(R.id.txt_mota);

        txt_title.setText(getIntent().getStringExtra("title"));
        txt_mota.setText(getIntent().getStringExtra("mota"));
        btn_Add = findViewById(R.id.btnAdd);
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txt_mota.getText().toString().isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd-MM-yyyy");
                    String time_now = sdf.format(new Date());
                    SQLiteNote db_note = new SQLiteNote(getApplicationContext());
                    db_note.addData(time_now, txt_title.getText().toString(), txt_mota.getText().toString());
                }
            }
        });
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                finish();
//                break;
//        }
//        return true;
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
