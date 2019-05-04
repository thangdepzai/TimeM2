package hust.edu.vn.timem.Utils;

import android.widget.EditText;
import android.widget.TextView;

public class TextUtils {

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().equals("");
    }

    public static boolean isNullOrEmpty(CharSequence value) {
        return value == null || value.toString().equals("");
    }

    public static boolean isEmpty(EditText editText) {
        if (editText != null) {
            return isNullOrEmpty(editText.getText().toString());
        }

        return true;
    }

    public static boolean isEmpty(TextView textView) {
        if (textView != null) {
            return isNullOrEmpty(textView.getText());
        }

        return true;
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidWebUrl(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.WEB_URL.matcher(target).matches();
        }
    }

}
