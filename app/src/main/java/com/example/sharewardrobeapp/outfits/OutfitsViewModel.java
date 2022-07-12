package com.example.sharewardrobeapp.outfits;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sharewardrobeapp.interfaces.DataRepository;
import com.example.sharewardrobeapp.objects.OutfitItem;

import java.util.ArrayList;

public class OutfitsViewModel extends ViewModel {
    private DataRepository repository = DataRepository.getInstance();

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private LiveData<ArrayList<OutfitItem>> OutfitItemListLiveData;

    public OutfitsViewModel() {
        super();
        isLoading.setValue(true);
        OutfitItemListLiveData = repository.getOutfitList();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<ArrayList<OutfitItem>> getOutfitItemListLiveData() {
        return OutfitItemListLiveData;
    }
}
