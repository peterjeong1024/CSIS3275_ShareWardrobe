package com.example.sharewardrobeapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sharewardrobeapp.main.MainRecyclerAdapter;
import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;

public class MainActivity extends BasementActivity implements MainRecyclerAdapter.OnMenuClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MainRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recycler_view);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainRecyclerAdapter();
        adapter.setOnMenuClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!getUserAccount().isLogin(getApplicationContext())) {
//            startActivity(new Intent(this, SignInActivity.class));
            mStartForResult.launch(new Intent(this, SignInActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.addItemMenu).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        UseLog.v("item.getItemId() : " + item.getTitle());
        switch (item.getItemId()) {
            case R.id.action_log_out:
                UseLog.i("action_log_out");
                getUserAccount().tryLogout(getApplicationContext());
                onResume();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickMenuItem(int position) {
        switch(position) {
            case 0: // Items
                startActivity(new Intent(this, FashionItemsActivity.class));
                break;
            case 1: // Outfit
                startActivity(new Intent(this, OutfitsActivity.class));
                break;
            case 2: // Wardrobe Stats
                startActivity(new Intent(this, StatsActivity.class));
                break;
            case 3: // Planners
                startActivity(new Intent(this, PlannerActivity.class));
                break;
            case 4: // Consultations
                startActivity(new Intent(this, ConsultActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        UseLog.i("onBackPressed");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.getResources().getString(R.string.dialog_end_app_title));
        builder.setMessage(this.getResources().getString(R.string.dialog_end_app_description));
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton(this.getResources().getString(R.string.dialog_end_app_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UseLog.i("onDestroy()");
        getUserAccount().tryLogout(getApplicationContext());
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case Activity.RESULT_OK:
                        UseLog.i("Activity.RESULT_OK");
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_succeed_to_login), Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        UseLog.i("Activity.RESULT_CANCELED");
                        break;
                    case ConstantValue.LOGIN_ACTIVITY_FINISH_APP:
                        UseLog.i("Activity.ConstantValue.LOGIN_ACTIVITY_FINISH_APP");
                        finish();
                        break;
                    default:
                        break;
                }
            }
    );
}