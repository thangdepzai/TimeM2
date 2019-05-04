package hust.edu.vn.timem.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hust.edu.vn.timem.Adapter.BaseRecycleViewAdapter;
import hust.edu.vn.timem.Adapter.ImageListAdapter;
import hust.edu.vn.timem.Data.SQLiteNote;
import hust.edu.vn.timem.R;

public class NoteActivity extends AppCompatActivity {

    FloatingActionButton btn_add_note;
    ImageButton btn_filter;
    //private List<String> listOfImagesPath;
    View edt;
    RecyclerView lst_note;
//    ImageView  img;
    LinearLayout lst_image;
    String url="";
    String comment="";
//    List<EditText> lst_edt;
    public static final int SPEECH_MOTA_RECOGNITION_CODE = 1;
    public static final int SPEECH_TITLE_RECOGNITION_CODE = 2;
    public static final int REQUEST_GALLERY = 3;
    public static final int REQUEST_CAMERA = 4;
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
                final ImageView img_attack;
                final ImageView img_content;
                final GridView  grid_image;
//                listOfImagesPath = new ArrayList<>();

                Button btn_cancel;
                Button btn_save;
                AlertDialog.Builder dialogBuilder =	new AlertDialog.Builder(NoteActivity.this, R.style.my_dialog);
                LayoutInflater inflater	= NoteActivity.this.getLayoutInflater();
                @SuppressLint("ResourceType") final View dialogView	=	inflater.inflate(R.layout.activity_add_note, (ViewGroup)findViewById(R.layout.activity_note));
                edt_title = dialogView.findViewById(R.id.edt_title);
                edt_mota = dialogView.findViewById(R.id.edt_mota);
                mic_mota = dialogView.findViewById(R.id.mic_mota);
                mic_title = dialogView.findViewById(R.id.mic_title);
                lst_image = dialogView.findViewById(R.id.lst_image);
                url = "";
                comment="";
//                lst_edt = new ArrayList<>();
//                img_content = dialogView.findViewById(R.id.img_content);
//                grid_image=  dialogView.findViewById(R.id.grid_image);
//                grid_image.setAdapter(new ImageListAdapter(NoteActivity.this,listOfImagesPath));
                img_attack = dialogView.findViewById(R.id.img_attack);
                img_attack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NoteActivity.this);
                        alertDialogBuilder.setTitle("Choose a source...");
                        alertDialogBuilder.setItems(new CharSequence[]{"Gallery", "Camera"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,
                                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        galleryIntent.setType("image/*");
                                        startActivityForResult(galleryIntent, REQUEST_GALLERY);
                                        break;
                                    case 1:
                                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        saveImage(camera);
                                        startActivityForResult(camera, REQUEST_CAMERA);
                                        break;
                                }
                            }
                        });
                        alertDialogBuilder.show();
                    }
                });

                dialogBuilder.setView(dialogView);
                dialogBuilder.setTitle("Add note");
                final AlertDialog b = dialogBuilder.create();
                b.setCancelable(false);
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
//                            String comment="";
//
//                            for(EditText e: lst_edt){
//                                comment=comment+";"+e.getText().toString();
//                            }
//                            url= url+","+comment;
                            db_note.addData(time_now, edt_title.getText().toString(), edt_mota.getText().toString(), url);
                            DataItem();
                        }else Toast.makeText(NoteActivity.this, "Thất bại ! Tiêu đề phải khác rỗng ! ", Toast.LENGTH_SHORT).show();

                        b.dismiss();
//                        lst_edt.clear();
                        lst_image.removeAllViews();

                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        lst_image.removeAllViews();
//                        lst_edt.clear();
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
            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK&& null != data) {
                    Uri selectedImage = data.getData();
                    ImageView img = new ImageView(NoteActivity.this);
                    img.setImageURI(selectedImage);
                    if(url.equals("")){
                        url = selectedImage+"";
                    }else  url = url+";"+selectedImage;
//                    EditText txt = new EditText(NoteActivity.this);
//                    txt.setHint("comment");
//                    lst_image.addView(txt);
                    lst_image.addView(img);
//                    lst_edt.add(txt);
                }
                break;
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK&& null != data) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    ImageView img = new ImageView(NoteActivity.this);
                    img.setImageBitmap(photo);
//                    EditText txt = new EditText(NoteActivity.this);
//                    txt.setHint("comment");
//                    lst_image.addView(txt);
                    lst_image.addView(img);
//                    lst_edt.add(txt);
                }
                break;
        }
    }
    public void saveImage(Intent camera){
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd|hh:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String name = "CameraTemp".concat(sdf.format(timestamp)).concat(".jpeg");
        File output = new File(dir, name);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
    }


    private void DataItem() {
        SQLiteNote db_note = new SQLiteNote(this);
        BaseRecycleViewAdapter adapter = new BaseRecycleViewAdapter(this, db_note.getData());
        adapter.notifyDataSetChanged();
        lst_note.setAdapter(adapter);

    }
}
