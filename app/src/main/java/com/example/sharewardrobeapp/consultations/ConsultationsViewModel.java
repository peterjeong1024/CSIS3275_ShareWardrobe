package com.example.sharewardrobeapp.consultations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sharewardrobeapp.interfaces.DataRepository;
import com.example.sharewardrobeapp.objects.Consultations;
import com.example.sharewardrobeapp.objects.FashionItem;

import java.util.ArrayList;

public class ConsultationsViewModel extends ViewModel {
    private DataRepository repository = DataRepository.getInstance();

    private LiveData<ArrayList<Consultations>> ConsultationListLiveData;

    public ConsultationsViewModel() {
        super();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<ArrayList<Consultations>> getConsultationDataList() {
        ConsultationListLiveData = repository.getConsultationDataList();
        return ConsultationListLiveData;
    }
}
