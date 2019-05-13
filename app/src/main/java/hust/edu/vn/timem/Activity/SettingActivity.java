package hust.edu.vn.timem.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import hust.edu.vn.timem.Data.SQLiteNote;
import hust.edu.vn.timem.R;
import hust.edu.vn.timem.UserPreference;

public class SettingActivity extends AppCompatActivity {
    private UserPreference userPreference;
    TextView txt_username, txt_num_notes, txt_num_event_today, txt_num_event_upcoming;
    TextView txt_email, txt_sdt, txt_img, txt_comment;
    ImageView  img_add;
    CircleImageView img_user;
    Button btn_sign_out;
    public static final int REQUEST_GALLERY = 1;
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

        if(!userPreference.getUserUrl().equals("")){

//            String url = userPreference.getUserUrl();
//            Uri  uri = Uri.parse(url);
//            Log.i("IMAGE", url);
//            String [] filepath = {MediaStore.Images.Media.DATA};
//            Cursor cu = getContentResolver().query(uri,filepath, null, null,null);
//            cu.moveToFirst();
//            int index = cu.getColumnIndex(filepath[0]);
//            String picpath = cu.getColumnName(index);
//            img_user.setImageBitmap(BitmapFactory.decodeFile(picpath));
        }
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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingActivity.this);
                alertDialogBuilder.setTitle("Choose a source...");
                alertDialogBuilder.setItems(new CharSequence[]{"Gallery"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                galleryIntent.setType("image/*");
                                startActivityForResult(galleryIntent, REQUEST_GALLERY);
                                break;

                        }
                    }
                });
                alertDialogBuilder.show();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK&& null != data) {
                    Uri selectedImage = data.getData();
                    try {
                        Glide
                                .with(SettingActivity.this)
                                .load(selectedImage)
                                .into(img_user);
                        userPreference.setURL(selectedImage+"");
                    } catch (Exception e) {
                        Log.i("IMAGE", "Loi load anh tu gallery " + e);
                    }
                }
                break;

        }
    }
}
