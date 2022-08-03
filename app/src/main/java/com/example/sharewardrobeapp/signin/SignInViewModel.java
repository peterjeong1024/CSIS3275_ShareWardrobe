package com.example.sharewardrobeapp.signin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sharewardrobeapp.interfaces.DataRepository;
import com.example.sharewardrobeapp.objects.UserAccount;

public class SignInViewModel extends ViewModel {
    private DataRepository repository = DataRepository.getInstance();

    private LiveData<UserAccount> UALiveData;
    private LiveData<Boolean> IsExisted;
    private LiveData<Boolean> IsSuccessSignIn;
    private LiveData<Boolean> IsSuccessUpdate;

    private MutableLiveData<Boolean> isWorking = new MutableLiveData<>();

    public SignInViewModel() {
        super();
        isWorking.postValue(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }


    public LiveData<UserAccount> signInUAItemData(String id, String pw) {
        UALiveData = repository.signInUserAccount(id, pw);
        return UALiveData;
    }

    public LiveData<Boolean> checkUA(String id) {
        IsExisted = repository.checkUserAccount(id);
        return IsExisted;
    }

    public LiveData<Boolean> addUserAccount(UserAccount ua) {
        isWorking.postValue(true);
        IsSuccessSignIn = repository.addUserAccount(ua);
        return IsSuccessSignIn;
    }

    public LiveData<Boolean> updateUserAccount(UserAccount ua) {
        IsSuccessUpdate = repository.updateUserAccount(ua);
        return IsSuccessUpdate;
    }

    public MutableLiveData<Boolean> getIsWorking() {
        return isWorking;
    }
}
