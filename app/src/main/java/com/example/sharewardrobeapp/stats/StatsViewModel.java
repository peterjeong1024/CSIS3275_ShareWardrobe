package com.example.sharewardrobeapp.stats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sharewardrobeapp.interfaces.DataRepository;
import com.example.sharewardrobeapp.objects.FashionItem;

import java.util.ArrayList;

public class StatsViewModel extends ViewModel {
    private DataRepository repository = DataRepository.getInstance();

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private LiveData<ArrayList<FashionItem>> FashionItemListLiveData;
    private LiveData<FashionItem> FashionItemLiveData;

    public StatsViewModel() {
        super();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<ArrayList<FashionItem>> getFashionItemListLiveData(String userId) {
        isLoading.setValue(true);
        FashionItemListLiveData = repository.getFashionItemList(userId);
        return FashionItemListLiveData;
    }

    public LiveData<FashionItem> getFashionItemData(String id) {
        isLoading.setValue(true);
        FashionItemLiveData = repository.getFashionItem(id);
        return FashionItemLiveData;
    }
}
