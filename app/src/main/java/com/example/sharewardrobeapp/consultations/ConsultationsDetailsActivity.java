package com.example.sharewardrobeapp.consultations;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharewardrobeapp.BasementActivity;
import com.example.sharewardrobeapp.R;
import com.example.sharewardrobeapp.interfaces.DataRepository;
import com.example.sharewardrobeapp.interfaces.GlideApp;
import com.example.sharewardrobeapp.objects.Consultations;
//import com.example.sharewardrobeapp.util.UseLog;

public class ConsultationsDetailsActivity extends BasementActivity {

    private ConsultationsViewModel mViewModel;
    private DataRepository repository = DataRepository.getInstance();

    private ImageView mImageView;

    private Button mTimeOne;
    private Button mTimeTwo;
    private Button mTimeThree;

    private TextView mConsultantName;
    private TextView mConsultantDesc;

    private Consultations mConsultations;
    private String selectedConsultationID;
    Intent intent;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultations_details);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getResources().getString(R.string.consultations_text));
        intent = getIntent();
        b = intent.getExtras();
       // mImageView = findViewById(R.id.consultant_image);
        mImageView = findViewById(R.id.imageViewConsultant);
        mTimeOne = findViewById(R.id.time_one);
        mTimeTwo = findViewById(R.id.time_two);
        mTimeThree = findViewById(R.id.time_three);

        mConsultantName = findViewById(R.id.consultantName);
        mConsultantDesc = findViewById(R.id.consultation_desc);
        mConsultantName.setText(b.getString("name"));
        mConsultantDesc.setText(b.getString("desc"));
        mImageView.setImageResource(b.getInt("imageRes"));
        //From the FashionItemDetailActivity.java
        //Do I need to add the (check it is Add or Edit mode?)

    }

    private void drawScreenData(){
        mConsultantName.setText(b.getString("name"));
        mConsultantDesc.setText(b.getString("desc"));


        //I don't know how to input the data of the available time

        if(!mConsultations.getConsultantImg().equals("")){
            GlideApp.with(getApplicationContext()).load(mConsultations.getConsultantImgBitmap()).into(mImageView);
            mImageView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume(){super.onResume();}

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        //return super.onPrepareOptionsMenu(menu);
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        return true;
    }

   // From The OnClickListener??

}