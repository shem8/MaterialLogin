package shem.com.materiallogin;

import android.support.design.widget.TextInputLayout;

/**
 * Created by shem on 1/15/16.
 */
public interface LoginViewListener {
    void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep);

    void onLogin(TextInputLayout loginUser, TextInputLayout loginPass);
}
