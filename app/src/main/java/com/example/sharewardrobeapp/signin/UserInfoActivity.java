package com.example.sharewardrobeapp.signin;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sharewardrobeapp.BasementActivity;
import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.objects.UserAccount;
import com.example.sharewardrobeapp.util.UseLog;

public class UserInfoActivity extends BasementActivity {

    private EditText mUserIdEdit;
    private EditText mUserPwEdit;
    private EditText mUserNameEdit;
    private Button mSubmitBtn;
    private Button mCheckIDBtn;

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
        mSubmitBtn = findViewById(R.id.btn_user_info_submit);
        mCheckIDBtn = findViewById(R.id.btn_edit_user_info_id_check);

        mSubmitBtn.setOnClickListener(mSubmitBtnClickListener);
        mCheckIDBtn.setOnClickListener(mCheckBtnClickListener);
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
                // insert new user account process
                String newID = mUserIdEdit.getText().toString();
                String newPw = mUserPwEdit.getText().toString();
                String newName = mUserNameEdit.getText().toString();

                if (newID.equals("") || newPw.equals("") || newName.equals("")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_input_data_is_wrong), Toast.LENGTH_LONG).show();
                    return;
                }

                UserAccount ua = new UserAccount(newID, newPw, newName);

//                if (DBQuery.checkUserIDInUSERS(mDBHelper, ua)) {
//                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_id_already_used), Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                long result = DBQuery.insertUserToUSERS(mDBHelper, ua);
//                if (result > 0) {
//                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_succeed_to_create_new_account), Toast.LENGTH_LONG).show();
//                    finish();
//                }

            } else {
                // update user account process
                String newPw = mUserPwEdit.getText().toString();
                String newName = mUserNameEdit.getText().toString();

                if (newPw.equals("") || newName.equals("")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_input_data_is_wrong), Toast.LENGTH_LONG).show();
                    return;
                }

                UserAccount ua = new UserAccount(getUserAccount().getUserID(), newPw, newName);
//                int result = DBQuery.updateUserToUSERS(mDBHelper, ua);
//                if (result > 0) {
//                    setUserAccount(ua);
//                    getUserAccount().tryLogin(getApplicationContext());
//                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_succeed_to_update_your_account), Toast.LENGTH_LONG).show();
//                    finish();
//                }
            }
        }
    };

    View.OnClickListener mCheckBtnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String newID = mUserIdEdit.getText().toString();
            String newPw = mUserPwEdit.getText().toString();

            UserAccount ua = new UserAccount(newID, newPw);

//            if (DBQuery.checkUserIDInUSERS(mDBHelper, ua)) {
//                Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_id_already_used), Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_this_id_can_use), Toast.LENGTH_LONG).show();
//            }
        }
    };
}