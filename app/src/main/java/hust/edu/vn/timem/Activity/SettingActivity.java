package hust.edu.vn.timem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import hust.edu.vn.timem.Data.SQLiteNote;
import hust.edu.vn.timem.R;
import hust.edu.vn.timem.UserPreference;

public class SettingActivity extends AppCompatActivity {
    private UserPreference userPreference;
    TextView txt_username, txt_num_notes, txt_num_event_today, txt_num_event_upcoming;
    TextView txt_email, txt_sdt, txt_img, txt_comment;
    ImageView img_user, img_add;
    Button btn_sign_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        txt_username = findViewById(R.id.txt_username);
        txt_num_notes = findViewById(R.id.txt_num_notes);
        txt_num_event_today = findViewById(R.id.txt_num_event_upcoming);
        txt_email = findViewById(R.id.txt_email);
        txt_sdt = findViewById(R.id.txt_sdt);
        txt_img = findViewById(R.id.txt_img);
        txt_comment = findViewById(R.id.txt_comment);
        img_user = findViewById(R.id.img_user);
        img_add = findViewById(R.id.img_add);
        btn_sign_out = findViewById(R.id.btn_sign_out);
        userPreference = UserPreference.getUserPreference(this);
        txt_username.setText(userPreference.getUserName());
        txt_sdt.setText(userPreference.getUserSDT());
        if(userPreference.getUserEmail().equals("")){
            img_add.setVisibility(View.VISIBLE);
            img_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }else{
        img_add.setVisibility(View.GONE);
        txt_email.setText(userPreference.getUserEmail());
    }
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPreference.setUserSignInStatus(false);
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
            }
        });
        txt_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cap nhat anh dai dien
            }
        });
        txt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        SQLiteNote s = new SQLiteNote(this);
        txt_num_notes.setText(s.count()+"");


    }
}
