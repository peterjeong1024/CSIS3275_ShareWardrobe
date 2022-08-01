package com.example.sharewardrobeapp.outfits;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.sharewardrobeapp.interfaces.DataRepository;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;
import java.util.Objects;

public class OutfitsViewModel extends ViewModel {
    private DataRepository repository = DataRepository.getInstance();

    private LiveData<ArrayList<OutfitItem>> OutfitItemListLiveData;
    private LiveData<ArrayList<OutfitItem>> OutfitAllListLiveData;
    private LiveData<OutfitItem> OutfitItemLiveData;
    private LiveData<ArrayList<FashionItem>> OutfitFashionItemListLiveData;

    private LiveData<FashionItem> FashionItemLiveData;

    public OutfitsViewModel() {
        super();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


    public LiveData<ArrayList<OutfitItem>> getOutfitItemListLiveData(String userID) {
        OutfitItemListLiveData = repository.getOutfitList(userID);
        return OutfitItemListLiveData;
    }

    public LiveData<ArrayList<OutfitItem>> getOutfitAllListLiveData() {
        OutfitAllListLiveData = repository.getAllOutfitList();
        return OutfitAllListLiveData;
    }

    public LiveData<OutfitItem> getOutfitItemData(String id) {
        OutfitItemLiveData = repository.getOutfitItem(id);
        return OutfitItemLiveData;
    }

    public LiveData<ArrayList<FashionItem>> getOutfitDetailItemListData(String userID) {
        OutfitFashionItemListLiveData = repository.getFashionItemList(userID);
        return OutfitFashionItemListLiveData;
    }

    public LiveData<FashionItem> getFashionItemData(String id) {
        FashionItemLiveData = repository.getFashionItem(id);
        return FashionItemLiveData;
    }

    public void addOutfitItem(OutfitItem outfitItem) {
        repository.addOutfitItem(outfitItem);
    }

    public void updateOutfitItem(OutfitItem outfitItem) {
        repository.updateOutfitItem(outfitItem);
    }
}
