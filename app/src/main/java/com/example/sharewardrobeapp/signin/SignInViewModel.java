package com.example.sharewardrobeapp.signin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sharewardrobeapp.interfaces.DataRepository;
import com.example.sharewardrobeapp.objects.UserAccount;

public class SignInViewModel extends ViewModel {
    private DataRepository repository = DataRepository.getInstance();

    private LiveData<UserAccount> UALiveData;
    private LiveData<Boolean> IsSuccessSignIn;
    private LiveData<Boolean> IsSuccessUpdate;

    public SignInViewModel() {
        super();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


    public LiveData<UserAccount> signInUAItemData(String id, String pw) {
        UALiveData = repository.signInUserAccount(id, pw);
        return UALiveData;
    }

    public LiveData<UserAccount> checkUA(String id) {
        UALiveData = repository.checkUserAccount(id);
        return UALiveData;
    }

    public LiveData<Boolean> addUserAccount(UserAccount ua) {
        IsSuccessSignIn = repository.addUserAccount(ua);
        return IsSuccessSignIn;
    }

    public LiveData<Boolean> updateUserAccount(UserAccount ua) {
        IsSuccessUpdate = repository.updateUserAccount(ua);
        return IsSuccessUpdate;
    }

}
