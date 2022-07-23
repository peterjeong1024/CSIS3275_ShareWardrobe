package com.example.sharewardrobeapp.outfits;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
    private LiveData<OutfitItem> OutfitItemLiveData;
    private LiveData<ArrayList<FashionItem>> OutfitFashionItemListLiveData;

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

    public LiveData<OutfitItem> getOutfitItemData(String id) {
        OutfitItemLiveData = repository.getOutfitItem(id);
        return OutfitItemLiveData;
    }

    public LiveData<ArrayList<FashionItem>> getOutfitDetailItemListData(String userID) {
        OutfitFashionItemListLiveData = repository.getFashionItemList(userID);
        return OutfitFashionItemListLiveData;
    }

    public void addOutfitItem(OutfitItem outfitItem) {
        repository.addOutfitItem(outfitItem);
    }

    public void updateOutfitItem(OutfitItem outfitItem) {
        repository.updateOutfitItem(outfitItem);
    }
}
