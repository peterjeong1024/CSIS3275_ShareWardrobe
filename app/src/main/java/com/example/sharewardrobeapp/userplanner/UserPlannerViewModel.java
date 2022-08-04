package com.example.sharewardrobeapp.userplanner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sharewardrobeapp.interfaces.DataRepository;
import com.example.sharewardrobeapp.objects.Consultations;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.UserPlanData;

import java.util.ArrayList;

public class UserPlannerViewModel extends ViewModel {
    private DataRepository repository = DataRepository.getInstance();

    private LiveData<ArrayList<UserPlanData>> UserPlanListLiveData;
    private LiveData<UserPlanData> UserPlanLiveData;

    public UserPlannerViewModel() {
        super();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<ArrayList<UserPlanData>> getUserPlanDataList(String userID) {
        UserPlanListLiveData = repository.getUserPlanDataList(userID);
        return UserPlanListLiveData;
    }

    public LiveData<UserPlanData> getUserPlanData(String id) {
        UserPlanLiveData = repository.getUserPlanDataItem(id);
        return UserPlanLiveData;
    }

    public void addUserPlanDataItem(UserPlanData userPlanData) {
        repository.addUserPlanDataItem(userPlanData);
    }

    public void updateUserPlanDataItem(UserPlanData userPlanData) {
        repository.updateUserPlanDataItem(userPlanData);
    }
}
