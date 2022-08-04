package com.example.sharewardrobeapp.interfaces;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sharewardrobeapp.objects.Consultations;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.objects.UserAccount;
import com.example.sharewardrobeapp.objects.UserPlanData;
import com.example.sharewardrobeapp.util.UseLog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private static final DataRepository ourInstance = new DataRepository();
    private RetrofitClient.RetrofitInterface api;

    private MutableLiveData<ArrayList<FashionItem>> ItemListLiveData = new MutableLiveData<>();
    private MutableLiveData<FashionItem> ItemLiveData = new MutableLiveData<>();

    private MutableLiveData<ArrayList<OutfitItem>> OutfitListLiveData = new MutableLiveData<>();
    private MutableLiveData<OutfitItem> OutfitLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<OutfitItem>> OutfitAllItemsLiveData = new MutableLiveData<>();

    private MutableLiveData<UserAccount> UALiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> IsExisted = new MutableLiveData<>();
    private MutableLiveData<Boolean> IsSuccessSignIn = new MutableLiveData<>();
    private MutableLiveData<Boolean> IsSuccessUpdate = new MutableLiveData<>();

    private MutableLiveData<ArrayList<UserPlanData>> UserPlanDataListLiveData = new MutableLiveData<>();
    private MutableLiveData<UserPlanData> UserPlanLiveData = new MutableLiveData<>();

    private MutableLiveData<ArrayList<Consultations>> ConsultationLiveData = new MutableLiveData<>();



    public static DataRepository getInstance() {
        return ourInstance;
    }

    private DataRepository() {
        api = RetrofitClient.getApi();
    }

    /*
     -- /FashionItem/ API list
     */
    public LiveData<ArrayList<FashionItem>> getFashionItemList(String userID) {
        api.getFashionItemList(userID).enqueue(new Callback<ArrayList<FashionItem>>() {
            @Override
            public void onResponse(Call<ArrayList<FashionItem>> call, Response<ArrayList<FashionItem>> response) {
                ItemListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<FashionItem>> call, Throwable t) {
                UseLog.d("Fail to get the FashionItem list from server");
                t.printStackTrace();
            }
        });
        return ItemListLiveData;
    }

    public LiveData<FashionItem> getFashionItem(String id) {
        api.getFashionItem(id).enqueue(new Callback<FashionItem>() {
            @Override
            public void onResponse(Call<FashionItem> call, Response<FashionItem> response) {
                ItemLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<FashionItem> call, Throwable t) {
                UseLog.d("Fail to get the FashionItem from server");
                t.printStackTrace();
            }
        });
        return ItemLiveData;
    }

    public void addFashionItem(FashionItem fashionItem) {
        api.addFashionItem(fashionItem).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UseLog.d("Succeed to send and reply : " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UseLog.d("Fail to send the FashionItem to server");
                t.printStackTrace();
            }
        });
    }

    public void updateFashionItem(FashionItem fashionItem) {
        api.updateFashionItem(fashionItem.get_id(), fashionItem).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UseLog.d("Succeed to send and reply : " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UseLog.d("Fail to send the FashionItem to server");
                t.printStackTrace();
            }
        });
    }

    /*
     -- /OutfitItem/ API list
     */

    public LiveData<ArrayList<OutfitItem>> getOutfitList(String userID) {
        api.getOutfitItemList(userID).enqueue(new Callback<ArrayList<OutfitItem>>() {
            @Override
            public void onResponse(Call<ArrayList<OutfitItem>> call, Response<ArrayList<OutfitItem>> response) {
                OutfitListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<OutfitItem>> call, Throwable t) {
                UseLog.d("Fail to get the OutfitItem list from server");
                t.printStackTrace();
            }
        });
        return OutfitListLiveData;
    }

    public LiveData<OutfitItem> getOutfitItem(String id) {
        api.getOutfitItem(id).enqueue(new Callback<OutfitItem>() {
            @Override
            public void onResponse(Call<OutfitItem> call, Response<OutfitItem> response) {
                OutfitLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<OutfitItem> call, Throwable t) {
                UseLog.d("Fail to get the OutfitItem from server");
                t.printStackTrace();
            }
        });
        return OutfitLiveData;
    }

    public void addOutfitItem(OutfitItem outfitItem) {
        api.addOutfitItem(outfitItem).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UseLog.d("Succeed to send and reply : " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UseLog.d("Fail to send the OutfitItem to server");
                t.printStackTrace();
            }
        });
    }

    public void updateOutfitItem(OutfitItem outfitItem) {
        api.updateOutfitItem(outfitItem.get_id(), outfitItem).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UseLog.d("Succeed to send and reply : " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UseLog.d("Fail to send the outfitItem to server");
                t.printStackTrace();
            }
        });
    }

    public LiveData<ArrayList<OutfitItem>> getAllOutfitList() {
        api.getAllOutfitItem().enqueue(new Callback<ArrayList<OutfitItem>>() {
            @Override
            public void onResponse(Call<ArrayList<OutfitItem>> call, Response<ArrayList<OutfitItem>> response) {
                OutfitAllItemsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<OutfitItem>> call, Throwable t) {
                UseLog.d("Fail to get the OutfitItem list from server");
                t.printStackTrace();
            }
        });
        return OutfitAllItemsLiveData;
    }


    /*
         -- /UserAccount/ API list
         */
    public LiveData<UserAccount> signInUserAccount(String id, String pw) {
        api.signInUserAccount(id, pw).enqueue(new Callback<UserAccount>() {
            @Override
            public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                UALiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserAccount> call, Throwable t) {
                UseLog.d("Fail to get the UserAccount from server");
                t.printStackTrace();
            }
        });
        return UALiveData;
    }

    public LiveData<Boolean> checkUserAccount(String id) {
        api.getUserAccount(id).enqueue(new Callback<UserAccount>() {
            @Override
            public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                if (response.body() == null) {
                    IsExisted.postValue(false);
                } else {
                    IsExisted.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<UserAccount> call, Throwable t) {
                UseLog.d("Fail to get the UserAccount from server");
                t.printStackTrace();
                IsExisted.postValue(false);
            }
        });
        return IsExisted;
    }

    public MutableLiveData<Boolean> addUserAccount(UserAccount ua) {
        api.addUserAccount(ua).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UseLog.d("Succeed to send and reply : " + response.body());
                IsSuccessSignIn.postValue(true);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UseLog.d("Fail to send the UserAccount to server");
                t.printStackTrace();
                IsSuccessSignIn.postValue(false);
            }
        });
        return IsSuccessSignIn;
    }

    public MutableLiveData<Boolean> updateUserAccount(UserAccount ua) {
        api.updateUserAccount(ua.getUserID(), ua).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UseLog.d("Succeed to send and reply : " + response.body());
                IsSuccessUpdate.postValue(true);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UseLog.d("Fail to send the UserAccount to server");
                t.printStackTrace();
                IsSuccessUpdate.postValue(false);
            }
        });
        return IsSuccessUpdate;
    }


    /*
            -- /UserPlanData/ API list
     */
    public LiveData<ArrayList<UserPlanData>> getUserPlanDataList(String userID) {
        api.getUserPlanDataList(userID).enqueue(new Callback<ArrayList<UserPlanData>>() {
            @Override
            public void onResponse(Call<ArrayList<UserPlanData>> call, Response<ArrayList<UserPlanData>> response) {
                UserPlanDataListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<UserPlanData>> call, Throwable t) {
                UseLog.d("Fail to get the OutfitItem list from server");
                t.printStackTrace();
            }
        });
        return UserPlanDataListLiveData;
    }

    public LiveData<UserPlanData> getUserPlanDataItem(String id) {
        api.getUserPlanDataItem(id).enqueue(new Callback<UserPlanData>() {
            @Override
            public void onResponse(Call<UserPlanData> call, Response<UserPlanData> response) {
                UserPlanLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserPlanData> call, Throwable t) {
                UseLog.d("Fail to get the UserPlanData from server");
                t.printStackTrace();
            }
        });
        return UserPlanLiveData;
    }

    public void addUserPlanDataItem(UserPlanData upd) {
        api.addUserPlanData(upd).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UseLog.d("Succeed to send and reply : " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UseLog.d("Fail to send the UserPlanData to server");
                t.printStackTrace();
            }
        });
    }

    public void deleteUserPlanData(String id) {
        api.deleteUserPlanData(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UseLog.d("Succeed to delete and reply : " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UseLog.d("Fail to delete UserPlanData");
                t.printStackTrace();
            }
        });
    }

    public void updateUserPlanDataItem(UserPlanData upd) {
        api.updateUserPlanData(upd.get_id(), upd).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UseLog.d("Succeed to send and reply : " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UseLog.d("Fail to send the UserPlanData to server");
                t.printStackTrace();
            }
        });
    }


    /*
            -- /Consultations/ API list
     */
    public LiveData<ArrayList<Consultations>> getConsultationDataList() {
        api.getConsultationDataList().enqueue(new Callback<ArrayList<Consultations>>() {
            @Override
            public void onResponse(Call<ArrayList<Consultations>> call, Response<ArrayList<Consultations>> response) {
                ConsultationLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Consultations>> call, Throwable t) {
                UseLog.d("Fail to get the OutfitItem list from server");
                t.printStackTrace();
            }
        });
        return ConsultationLiveData;
    }


}
