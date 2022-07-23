package com.example.sharewardrobeapp.signin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sharewardrobeapp.interfaces.DataRepository;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.UserAccount;

import java.util.ArrayList;

public class SignInViewModel extends ViewModel {
    private DataRepository repository = DataRepository.getInstance();

    private LiveData<UserAccount> UALiveData;

    public SignInViewModel() {
        super();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


    public LiveData<UserAccount> checkUAItemData(String id, String pw) {
        UALiveData = repository.checkUserAccount(id, pw);
        return UALiveData;
    }
}
