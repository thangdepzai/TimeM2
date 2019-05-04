package hust.edu.vn.timem.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import hust.edu.vn.timem.Data.SQLiteUser;
import hust.edu.vn.timem.Model.UserModel;
import hust.edu.vn.timem.R;
import hust.edu.vn.timem.UserPreference;
import hust.edu.vn.timem.Utils.PasswordValidator;
import hust.edu.vn.timem.Utils.TextUtils;

public class SignUpActivity extends Activity {
    EditText edtUsername, edtPassW;
    Button btnSignUp;
    private static final int REQUEST_CODE = 999;
    private SQLiteUser userDatabaseHelper;
    private UserModel userModel = new UserModel();
    private UserPreference userPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassW = findViewById(R.id.edtPassW);
        btnSignUp = findViewById(R.id.btnSignUp);
        userDatabaseHelper = new SQLiteUser(this);
        userPreference = UserPreference.getUserPreference(this);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validate()){
                    return;
                }
                 if(isExists(edtUsername.getText().toString())){
                     Toast.makeText(getBaseContext(), "Tên đăng nhập đã tồn tại", Toast.LENGTH_LONG).show();
                }else{
                     startLoginPage(LoginType.PHONE);
                 }
            }
        });

    }

    private boolean isExists(String username) {
        return userDatabaseHelper.checkUser(username);
    }
    private boolean isExistsSDT(String sdt){
        return userDatabaseHelper.checkUserSDT(sdt);
    }
    private boolean isExistsEmail(String email){
        return userDatabaseHelper.checkUserMail(email);
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
                Toast.makeText(this, ""+result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
                return;

            }
            else if(result.wasCancelled()){
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                return;
            }else{
                AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                                                 @Override
                                                 public void onSuccess(Account account) {
                                                     if(account.getPhoneNumber()!=null) {

                                                         PhoneNumber phoneNumber = account.getPhoneNumber();
                                                         String phoneNumberString = phoneNumber.toString();
                                                         if(isExistsSDT(phoneNumberString)){
                                                             Toast.makeText(SignUpActivity.this, "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();
                                                             return;
                                                         }
                                                         Log.i("Success",phoneNumberString );
                                                         userModel.setSdt(phoneNumberString);
                                                         userPreference.setUserSdt(phoneNumberString);


                                                     }

                                                     if(account.getEmail()!=null){

                                                         String mail = account.getEmail();
                                                         if(isExistsSDT(mail)){
                                                             Toast.makeText(SignUpActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                                                             return;
                                                         }
                                                         userModel.setMail(mail);
                                                         userPreference.setUserEmail(mail);

                                                     }
                                                     userModel.setUserName(edtUsername.getText().toString());
                                                     userModel.setPassW(edtPassW.getText().toString());
                                                     userPreference.setUserName(edtUsername.getText().toString());
                                                     userPreference.setPassword(edtPassW.getText().toString());
                                                     userPreference.setUserSignInStatus(true);
                                                     userDatabaseHelper.addUser(userModel);
                                                     Log.i("Success", userModel.getSdt());
                                                     userPreference.setUserSignInStatus(true);
                                                     Toast.makeText(SignUpActivity.this, "Successfuly !"+ userModel.getSdt(), Toast.LENGTH_SHORT).show();
                                                     startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                                     finish();
                                                 }

                                                 @Override
                                                 public void onError(AccountKitError accountKitError) {

                                                 }
                                             });


            }

        }
    }


    public boolean isUsername( String user){
        String regex = "^[a-zA-Z_][a-zA-Z0-9_]*$";
        return user.matches(regex) && user.length()>=6 ;
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
    public boolean validate() {
        boolean valid = true;

        String username = edtUsername.getText().toString();
        String password = edtPassW.getText().toString();
        if (TextUtils.isNullOrEmpty(username)) {
            edtUsername.setError(getString(R.string.alert_enter_name));
            valid = false;
        } else  if (!isUsername(username)) {
            edtUsername.setError(getString(R.string.alert_username_error));
            valid = false;
        } else {
            edtUsername.setError(null);
        }


        if (!PasswordValidator.isPasswordLengthValid(password)) {
            edtPassW.setError(getString(R.string.alert_password_error));
            valid = false;
        } else {
            edtPassW.setError(null);
        }
        return valid;
    }
}
