package com.example.sharewardrobeapp.interfaces;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private static final DataRepository ourInstance = new DataRepository();
    private RetrofitClient.RetrofitInterface api;

    private MutableLiveData<ArrayList<FashionItem>> ItemListLiveData = new MutableLiveData<>();
    private MutableLiveData<FashionItem> ItemLiveData = new MutableLiveData<>();

    private MutableLiveData<ArrayList<OutfitItem>> OutfitListLiveData = new MutableLiveData<>();
    private MutableLiveData<OutfitItem> OutfitLiveData = new MutableLiveData<>();

    public static DataRepository getInstance() {
        return ourInstance;
    }

    private DataRepository() {
        api = RetrofitClient.getApi();
    }


    /*
     -- /FashionItem/ API list
     */
    public LiveData<ArrayList<FashionItem>> getFashionItemList() {
        api.getFashionItemList().enqueue(new Callback<ArrayList<FashionItem>>() {
            @Override
            public void onResponse(Call<ArrayList<FashionItem>> call, Response<ArrayList<FashionItem>> response) {
                ItemListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<FashionItem>> call, Throwable t) {
                UseLog.d("Fail to get the FashionItem list from server");
                t.printStackTrace();
            }
        });
        return ItemListLiveData;
    }

    public LiveData<FashionItem> getFashionItem(int id) {
        api.getFashionItem(id).enqueue(new Callback<FashionItem>() {
            @Override
            public void onResponse(Call<FashionItem> call, Response<FashionItem> response) {
                ItemLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<FashionItem> call, Throwable t) {
                UseLog.d("Fail to get the FashionItem from server");
                t.printStackTrace();
            }
        });
        return ItemLiveData;
    }

    /*
     -- /OutfitItem/ API list
     */

    public LiveData<ArrayList<OutfitItem>> getOutfitList() {
        api.getOutfitItemList().enqueue(new Callback<ArrayList<OutfitItem>>() {
            @Override
            public void onResponse(Call<ArrayList<OutfitItem>> call, Response<ArrayList<OutfitItem>> response) {
                OutfitListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<OutfitItem>> call, Throwable t) {
                UseLog.d("Fail to get the OutfitItem list from server");
                t.printStackTrace();
            }
        });
        return OutfitListLiveData;
    }

    public LiveData<OutfitItem> getOutfitItem(int id) {
        api.getOutfitItem(id).enqueue(new Callback<OutfitItem>() {
            @Override
            public void onResponse(Call<OutfitItem> call, Response<OutfitItem> response) {
                OutfitLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<OutfitItem> call, Throwable t) {
                UseLog.d("Fail to get the OutfitItem from server");
                t.printStackTrace();
            }
        });
        return OutfitLiveData;
    }
}
