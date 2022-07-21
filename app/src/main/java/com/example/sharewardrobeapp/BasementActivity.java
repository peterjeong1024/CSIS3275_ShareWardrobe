package com.example.sharewardrobeapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sharewardrobeapp.objects.UserAccount;
import com.example.sharewardrobeapp.util.UseLog;

public class BasementActivity extends AppCompatActivity {

    private UserAccount mUserAccount;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserAccount = new UserAccount();
        getSupportActionBar().setHomeButtonEnabled(true);
        isLogin = getUserAccount().isLogin(getApplicationContext());

        UseLog.d("Activity Name : " + getLocalClassName());

        switch (getLocalClassName()) {
            case "FashionItemsActivity":
                setTitle(getResources().getString(R.string.items_text));
                break;
            case "OutfitsActivity" :
                setTitle(getResources().getString(R.string.outfits_text));
                break;
            case "StatsActivity" :
                setTitle(getResources().getString(R.string.wardrobe_stats_text));
                break;
            case "PlannerActivity" :
                setTitle(getResources().getString(R.string.planner_text));
                break;
            case "ConsultActivity" :
                setTitle(getResources().getString(R.string.consultations_text));
                break;
            case "SignInActivity" :
                setTitle(getResources().getString(R.string.sign_in_text));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActionBar ab = getSupportActionBar();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        UseLog.v("item.getItemId() : " + item.getTitle());
        switch (item.getItemId()) {
            case android.R.id.home:
                UseLog.v("click back button in actionbar");
                finish();
                return true;
            case R.id.addItemMenu:
                UseLog.v("addItemMenu");
                return true;
            case R.id.action_log_out:
                UseLog.v("Log out");
                getUserAccount().tryLogout(getApplicationContext());
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public UserAccount getUserAccount() {
        return mUserAccount;
    }

    public void setUserAccount(UserAccount mUserAccount) {
        this.mUserAccount = mUserAccount;
    }
}