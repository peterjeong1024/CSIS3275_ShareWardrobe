package com.example.sharewardrobeapp.fashionitems;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sharewardrobeapp.interfaces.DataRepository;
import com.example.sharewardrobeapp.objects.FashionItem;

import java.util.ArrayList;

public class FashionItemsViewModel extends ViewModel {
    private DataRepository repository = DataRepository.getInstance();

    private LiveData<ArrayList<FashionItem>> FashionItemListLiveData;
    private LiveData<FashionItem> FashionItemLiveData;

    public FashionItemsViewModel() {
        super();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<ArrayList<FashionItem>> getFashionItemListLiveData(String userId) {
        FashionItemListLiveData = repository.getFashionItemList(userId);
        return FashionItemListLiveData;
    }

    public LiveData<FashionItem> getFashionItemData(String id) {
        FashionItemLiveData = repository.getFashionItem(id);
        return FashionItemLiveData;
    }

    public void addFashionItem(FashionItem fashionItem) {
        repository.addFashionItem(fashionItem);
    }

    public void updateFashionItem(FashionItem fashionItem) {
        repository.updateFashionItem(fashionItem);
    }
}
