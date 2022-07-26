package com.example.sharewardrobeapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sharewardrobeapp.objects.UserAccount;
import com.example.sharewardrobeapp.signin.SignInViewModel;
import com.example.sharewardrobeapp.signin.UserInfoActivity;
import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SignInActivity extends BasementActivity {

    private GoogleSignInOptions mGoogleSignInOptions;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;

    private ImageView signBtnGoogle;

    private EditText mId;
    private EditText mPw;
    private Button mSignInBtn;
    private Button mSignUpBtn;

    private SignInViewModel mViewModel;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().setHomeButtonEnabled(true);

        mId = findViewById(R.id.editTextUserName);
        mPw = findViewById(R.id.editTextPassword);
        mSignInBtn = findViewById(R.id.btnLogin);
        mSignUpBtn = findViewById(R.id.btnSignUp);
        signBtnGoogle = findViewById(R.id.imgGoogle);

        mSignInBtn.setOnClickListener(btnClickListener);
        mSignUpBtn.setOnClickListener(btnClickListener);
        signBtnGoogle.setOnClickListener(btnClickListener);

        mViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        handleGoogleSignInTask(task);
                    }
                });
    }

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btnLogin) {
                // run login code
                signInAccount(mId.getText().toString(), mPw.getText().toString());
            } else if (view.getId() == R.id.btnSignUp) {
                // go to sign up activity
                Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                startActivity(intent);
            } else if (view.getId() == R.id.imgGoogle) {
                // check google account
                GoogleSignInSetup();
            }
        }
    };

    private void signInAccount(String id, String pw) {
        mViewModel.signInUAItemData(id, pw).observe(this, new Observer<UserAccount>() {
            @Override
            public void onChanged(UserAccount userAccount) {
                if (userAccount == null) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_cannot_find_id), Toast.LENGTH_LONG).show();
                } else {
                    UseLog.d(userAccount.toString());
                    setUserAccount(userAccount);
                    UseLog.d(getUserAccount().toString());
                    getUserAccount().tryLogin(getApplicationContext());
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            }
        });
    }

    private void GoogleSignInSetup() {
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);
        mGoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        // checking if user is already logged in
        if (mGoogleSignInAccount != null) {
            String UserID = mGoogleSignInAccount.getEmail();
            String UserPW = mGoogleSignInAccount.getId();
            signInAccount(UserID, UserPW);
        } else {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            activityResultLauncher.launch(signInIntent);
        }
    }

    private void handleGoogleSignInTask(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
            String UserID = googleSignInAccount.getEmail();
            String UserPW = googleSignInAccount.getId();
            String UserEmail = googleSignInAccount.getEmail();
            String UserName = googleSignInAccount.getDisplayName();

            UserAccount ua = new UserAccount(UserID, UserPW, UserName, UserEmail, true);
            mViewModel.addUserAccount(ua).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if (aBoolean) {
                        signInAccount(ua.getUserID(), ua.getUserPW());
                    }
                }
            });
        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed or Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        return true;
    }

    @Override
    public void onBackPressed() {
        UseLog.i("onBackPressed");
        displayFinishDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                displayFinishDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displayFinishDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.getResources().getString(R.string.dialog_end_app_title));
        builder.setMessage(this.getResources().getString(R.string.dialog_end_app_description));
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton(this.getResources().getString(R.string.dialog_end_app_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setResult(ConstantValue.LOGIN_ACTIVITY_FINISH_APP);
                finish();
            }
        });

        builder.setNegativeButton(this.getResources().getString(R.string.dialog_end_app_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}