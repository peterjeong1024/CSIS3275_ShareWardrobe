package com.example.sharewardrobeapp.signin;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharewardrobeapp.BasementActivity;
import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.interfaces.AES;
import com.example.sharewardrobeapp.objects.UserAccount;
import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;

public class UserInfoActivity extends BasementActivity {

    private EditText mUserIdEdit;
    private EditText mUserPwEdit;
    private EditText mUserNameEdit;
    private EditText mUserEmailEdit;

    private Button mSubmitBtn;
    private Button mCheckIDBtn;

    private SignInViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        if (!getUserAccount().isLogin(getApplicationContext())) {
            UseLog.i("logout status");
            // send it to login activity
        }

        mUserIdEdit = findViewById(R.id.edit_user_info_id);
        mUserPwEdit = findViewById(R.id.edit_user_info_pw);
        mUserNameEdit = findViewById(R.id.edit_user_info_name);
        mUserEmailEdit = findViewById(R.id.edit_user_info_email);

        mSubmitBtn = findViewById(R.id.btn_user_info_submit);
        mCheckIDBtn = findViewById(R.id.btn_edit_user_info_id_check);

        mSubmitBtn.setOnClickListener(mSubmitBtnClickListener);
        mCheckIDBtn.setOnClickListener(mCheckBtnClickListener);

        mViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // layout redraw
        if (getUserAccount().isLogin(getApplicationContext())) {
            // update user info
            mCheckIDBtn.setVisibility(View.GONE);
            mUserIdEdit.setEnabled(false);

            UserAccount ua = getUserAccount();
            mUserIdEdit.setText(ua.getUserID());
            mUserPwEdit.setText(ua.getUserPW());
            mUserNameEdit.setText(ua.getUserName());
        }
        mUserNameEdit.requestFocus();
    }

    View.OnClickListener mSubmitBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mUserIdEdit.isEnabled()) {
                signUpAccount();
            } else {
                updateAccount();
            }
        }
    };

    private void signUpAccount() {
        // insert new user account process
        String newID = mUserIdEdit.getText().toString();
        String newPw = mUserPwEdit.getText().toString();
        String newName = mUserNameEdit.getText().toString();
        String newEmail = mUserNameEdit.getText().toString();

        if (newID.equals("") || newPw.equals("") || newName.equals("") || newEmail.equals("")) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_input_data_is_wrong), Toast.LENGTH_LONG).show();
            return;
        }
        UserAccount ua = new UserAccount(newID, newPw, newName, newEmail, false);
        mViewModel.checkUA(ua.getUserID()).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isExisted) {
                if (isExisted) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_id_already_used), Toast.LENGTH_LONG).show();
                } else {
                    if (Boolean.FALSE.equals(mViewModel.getIsWorking().getValue())) {
                        createAccount(ua);
                    }
                }
            }
        });
    }

    private void createAccount(UserAccount ua) {
        UseLog.d("createAccount : " + ua.toString());
        String encPw = "";

        try {
            encPw = AES.encByKey(ConstantValue.CIPHER_ENCRYPT_KEY_VALUE, ua.getUserPW());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ua.setUserPW(encPw);

        mViewModel.addUserAccount(ua).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_succeed_to_create_new_account), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_failed_to_create_new_account), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateAccount() {
        // update user account process
        String newPw = mUserPwEdit.getText().toString();
        String newName = mUserNameEdit.getText().toString();
        String newEmail = mUserEmailEdit.getText().toString();

        if (newPw.equals("") || newName.equals("") || newEmail.equals("")) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_input_data_is_wrong), Toast.LENGTH_LONG).show();
            return;
        }

        UserAccount ua = new UserAccount(getUserAccount().getUserID(), newPw, newName, newEmail, getUserAccount().getIsGoogleAccount());

        String encPw = "";

        try {
            encPw = AES.encByKey(ConstantValue.CIPHER_ENCRYPT_KEY_VALUE, ua.getUserPW());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ua.setUserPW(encPw);

        mViewModel.updateUserAccount(ua).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    setUserAccount(ua);
                    getUserAccount().tryLogin(getApplicationContext());
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_succeed_to_update_your_account), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    View.OnClickListener mCheckBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            checkAccountDuplicate();
        }
    };

    private void checkAccountDuplicate() {
        String newID = mUserIdEdit.getText().toString();
        String newPw = mUserPwEdit.getText().toString();

        UserAccount ua = new UserAccount(newID, newPw);
        mViewModel.checkUA(ua.getUserID()).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isExisted) {
                if (isExisted) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_id_already_used), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_this_id_can_use), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}