package hust.edu.vn.timem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
                Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
                startActivity(intent);

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
        lst_note.setAdapter(adapter);


    }
}
