package hust.edu.vn.timem.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import hust.edu.vn.timem.Data.SQLiteUser;
import hust.edu.vn.timem.Model.UserModel;
import hust.edu.vn.timem.R;
import hust.edu.vn.timem.UserPreference;
import hust.edu.vn.timem.Utils.PasswordValidator;
import hust.edu.vn.timem.Utils.TextUtils;

public class LoginActivity extends Activity {
    Button btnPhoneLogin;
    Button btnEmailLogin, btnLogin;
    EditText edtPassW;
    EditText edtUsername;
    TextView txtSignUp;
    private static final int REQUEST_CODE = 999;

    private SQLiteUser userDatabaseHelper;
    private UserPreference userPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        btnEmailLogin = findViewById(R.id.btnEmailLogin);
        btnPhoneLogin = findViewById(R.id.btnPhoneLogin);
        btnLogin = findViewById(R.id.btnLogin);
        edtPassW = findViewById(R.id.edtPassW);
        edtUsername = findViewById(R.id.edtUsername);
        txtSignUp = findViewById(R.id.txtSignUp);
        userDatabaseHelper = new SQLiteUser(this);
        userPreference = UserPreference.getUserPreference(this);
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(it);
            }
        });


        //printKeyhash();
        btnEmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginPage(LoginType.EMAIL);
            }
        });
        btnPhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginPage(LoginType.PHONE);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validate()){
                    return;
                }
                String user  = edtUsername.getText().toString();
                String pass = edtPassW.getText().toString();

                if( userDatabaseHelper.checkUser(user, pass) ){
                    UserModel userModel = userDatabaseHelper.getUser(user, pass);
                    userPreference.setUserName(user);
                    userPreference.setPassword(pass);
                    userPreference.setUserSdt(userModel.getSdt());
                    userPreference.setUserEmail(userModel.getMail());
                    userPreference.setUserSignInStatus(true);
                    Log.i("Success",userModel.getUserName()+ ", "+ userModel.getSdt()+", "+ userModel.getPassW() );
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else Toast.makeText(getApplicationContext(), "Sai username hoặc password", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public boolean isUsername( String user){
        String regex = "^[a-zA-Z_][a-zA-Z0-9_]*$";
        return user.matches(regex) && user.length()>=6;
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
                        UserModel userModel = null;
                        if(account.getPhoneNumber()!=null) {

                            PhoneNumber phoneNumber = account.getPhoneNumber();
                            String phoneNumberString = phoneNumber.toString();
                            if(!isExistsSDT(phoneNumberString)){
                                Toast.makeText(LoginActivity.this, "Số điện thoại chưa đăng ký", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.i("Success",phoneNumberString );
                            userModel = userDatabaseHelper.getUserSDT(phoneNumberString);
                        }

                        if(account.getEmail()!=null){

                            String mail = account.getEmail();
                            if(! isExistsSDT(mail)){
                                Toast.makeText(LoginActivity.this, "Email chưa đăng ký", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            userModel = userDatabaseHelper.getUserMail(mail);

                        }

                        userPreference.setUserName(userModel.getUserName());
                        userPreference.setPassword(userModel.getPassW());
                        userPreference.setUserSdt(userModel.getSdt());
                        userPreference.setUserEmail(userModel.getMail());
                        userPreference.setUserSignInStatus(true);

                        Toast.makeText(LoginActivity.this, "Successfuly !"+ userModel.getSdt(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(AccountKitError accountKitError) {

                    }
                });

            }

        }
    }

    private void printKeyhash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "hust.edu.vn.timem",
                    PackageManager.GET_SIGNATURES
            );
            for(Signature sign : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(sign.toByteArray());
                Log.d("KEYHASH", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
    public boolean validate() {
        boolean valid = true;

        String username = edtUsername.getText().toString();
        String password = edtPassW.getText().toString();

        if (TextUtils.isNullOrEmpty(username) || !isUsername(username)) {
            edtUsername.setError(getString(R.string.alert_username_error));
            valid = false;
        } else {
            edtUsername.setError(null);
        }
        if (!PasswordValidator.isPasswordLengthValid(password)) {
            edtPassW.setError(getString(R.string.alert_password_length_error));
            valid = false;
        } else {
            edtPassW.setError(null);
        }
        return valid;
    }
}
