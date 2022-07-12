package com.example.sharewardrobeapp.fashionitems;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sharewardrobeapp.interfaces.DataRepository;
import com.example.sharewardrobeapp.objects.FashionItem;

import java.util.ArrayList;

public class FashionItemsViewModel extends ViewModel {
    private DataRepository repository = DataRepository.getInstance();

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private LiveData<ArrayList<FashionItem>> FashionItemListLiveData;

    public FashionItemsViewModel() {
        super();
        isLoading.setValue(true);
        FashionItemListLiveData = repository.getFashionItemList();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<ArrayList<FashionItem>> getFashionItemListLiveData() {
        return FashionItemListLiveData;
    }
}
