package hust.edu.vn.timem.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import hust.edu.vn.timem.R;
import hust.edu.vn.timem.UserPreference;

public class SplashActivity extends AppCompatActivity implements Handler.Callback {
    private Handler mHandler;
    private UserPreference userPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler=new Handler(this);
        userPreference = UserPreference.getUserPreference(this);
        mHandler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Message msg=new Message();
                boolean state=userPreference.isUserLoggedIn();
                msg.arg1=0;
                msg.arg2=0;
                msg.obj=state;
                mHandler.sendMessage(msg);
            }
        },2000);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if((boolean)msg.obj){
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
        }else{
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
        return false;
    }
}
