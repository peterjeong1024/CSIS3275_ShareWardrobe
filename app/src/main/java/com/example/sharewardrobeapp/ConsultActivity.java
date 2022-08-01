package com.example.sharewardrobeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.sharewardrobeapp.consultations.ConsultationsRecyclerAdapter;
import com.example.sharewardrobeapp.consultations.ConsultationsViewModel;
import com.example.sharewardrobeapp.objects.Consultations;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.stats.StatsViewModel;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;

public class ConsultActivity extends BasementActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    RecyclerView.Adapter adapter;


    private ConsultationsViewModel mViewModel;
    private ArrayList<Consultations> mConsultationsItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);

        // sample code to use ViewModel object
        // create new ViewModel object
        mViewModel = new ViewModelProvider(this).get(ConsultationsViewModel.class);
        // bring data from ViewModel object. data is brought automatically
        mViewModel.getConsultationDataList().observe(this, new Observer<ArrayList<Consultations>>() {
            @Override
            public void onChanged(ArrayList<Consultations> items) {
                // data(fashionItems) is brought here  automatically and asynchronously
                mConsultationsItems = items;
                displayScreen(items);

                recyclerView = findViewById(R.id.consultRecyclerView);
                recyclerView.setLayoutManager(layoutManager);
                adapter = new ConsultationsRecyclerAdapter();
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void displayScreen(ArrayList<Consultations> items) {
        // drawing code is here
        UseLog.d("" + items.get(0).getConsultantSchedule().get(0).getTime());
        UseLog.d("" + items.get(0).getConsultantSchedule().get(0).isFree());
    }
}