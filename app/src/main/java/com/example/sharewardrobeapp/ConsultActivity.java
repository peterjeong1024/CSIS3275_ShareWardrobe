package com.example.sharewardrobeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.sharewardrobeapp.consultations.ConsultationsDetailsActivity;
import com.example.sharewardrobeapp.consultations.ConsultationsRecyclerAdapter;
import com.example.sharewardrobeapp.consultations.ConsultationsViewModel;
import com.example.sharewardrobeapp.fashionitems.FashionItemDetailActivity;
import com.example.sharewardrobeapp.objects.Consultations;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.stats.StatsViewModel;
import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;

public class ConsultActivity extends BasementActivity implements ConsultationsRecyclerAdapter.OnItemClick{

    public String[] names = {"Nicole Warne", "Margareth Zhang", "Jane Aldridge", "Gabi Gregg", "Wendy Nguyen"};
    private int[] images = {R.drawable.nicole_warne,
            R.drawable.margareth_zhang,
            R.drawable.jane_aldridge, R.drawable.gabi_gregg, R.drawable.wendy_nguyen};

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    ConsultationsRecyclerAdapter adapter;


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
            }
        });

        displayScreen();
    }

   // private void displayScreen(ArrayList<Consultations> items) {
   private void displayScreen() {
        recyclerView = findViewById(R.id.consultRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ConsultationsRecyclerAdapter(this,names,images);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        // drawing code is here
        //UseLog.d("" + items.get(0).getConsultantSchedule().get(0).getTime());
        //UseLog.d("" + items.get(0).getConsultantSchedule().get(0).isFree());
    }

    @Override
    public void onClickItem(int position) {

  //      ConsultationsRecyclerAdapter accesNames = new ConsultationsRecyclerAdapter();
    //    String name = accesNames.names[position];
        String name = names[position];
        String desc = "Check pat pat!!";
        int imageRes = images[position];

        Intent i = new Intent(this, ConsultationsDetailsActivity.class);
        Bundle b = new Bundle();
        b.putString("name", name);
      //  b.putString("image", mConsultationsItems.get(position).getConsultantImg());
        b.putString("desc", desc );
        b.putInt("imageRes", imageRes);
        i.putExtras(b);
        startActivity(i);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        menu.clear();

        return true;
    }
}