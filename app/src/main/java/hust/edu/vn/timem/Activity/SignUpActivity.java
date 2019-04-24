package hust.edu.vn.timem.Activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import hust.edu.vn.timem.Activity.MainActivity;
import hust.edu.vn.timem.R;

public class SignUpActivity extends Activity {
    EditText edtUsername, edtPassW;
    TextView txtUserError,txtPassError;
    Button btnSignUp;
    boolean vaildUsername = false,  vaildPass = false;
    private static final int REQUEST_CODE = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassW = findViewById(R.id.edtPassW);
        txtUserError = findViewById(R.id.txtUserError);
        txtPassError = findViewById(R.id.txtPassError);

        btnSignUp = findViewById(R.id.btnSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isUsername(edtUsername.getText().toString())) {
                    txtUserError.setText("Only chars, numbers, underlines and start by a char");
                    vaildUsername = false;
                }else if(isExists(edtUsername.getText().toString())){
                    txtUserError.setText("Tên đăng nhập đã tồn tại");
                    vaildUsername = false;
                }else{
                    txtUserError.setText("");
                    vaildUsername = true;
                }
                if(edtPassW.getText().toString().length()<6){
                    txtPassError.setText("Mật khẩu phải ít nhất 6 ký tự");
                    vaildPass = false;
                }else{
                    vaildPass = true;
                    txtPassError.setText("");
                }
                if(vaildUsername && vaildPass){
                    startLoginPage(LoginType.PHONE);

                }
            }
        });

    }

    private boolean isExists(String toString) {
        // firebase
        return false;
    }

    private void startLoginPage(LoginType loginType) {
        Intent it = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder config = new
                AccountKitConfiguration.AccountKitConfigurationBuilder(
                loginType,
                AccountKitActivity.ResponseType.TOKEN
        ); // use token when acess Bật quy trình mã truy cập khách hàng
        it.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,config.build());
        startActivityForResult(it, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode  == REQUEST_CODE){
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if(result.getError()!=null){
                Toast.makeText(this, ""+result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT);
                return;

            }
            else if(result.wasCancelled()){
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT);
                return;
            }else{
                Toast.makeText(this, "Successfuly !", Toast.LENGTH_SHORT);
                startActivity(new Intent(this, MainActivity.class));
            }

        }
    }


    public boolean isUsername( String user){
        String regex = "^[a-zA-Z_][a-zA-Z0-9_]*$";
        return user.matches(regex);

    }
    public  boolean isEmail(String email)
    {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        return  email.matches(regex);
    }
    public  boolean isPhone(String phone )
    {
        String regex = "^(03[2-9]|07[0|1|2|6|8]|08[3|4|5|7|9]|05[6|8|9])([0-9]{7})$";
        return  phone.matches(regex);
    }
}
