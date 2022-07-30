package com.example.sharewardrobeapp.interfaces;

import com.example.sharewardrobeapp.objects.Consultations;
import com.example.sharewardrobeapp.objects.FashionItem;
import com.example.sharewardrobeapp.objects.OutfitItem;
import com.example.sharewardrobeapp.objects.UserAccount;
import com.example.sharewardrobeapp.objects.UserPlanData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RetrofitClient {
    //    private static final String Base_URL = "http://localhost:5000/";
//    private static final String Base_URL = "http://192.168.31.18:5000/";
    private static final String Base_URL = "https://sharewardrobe-api-server.herokuapp.com/";
    private static volatile Retrofit RetrofitInstance;
    private static volatile RetrofitInterface RetrofitInterfaceInstance;

    public static Retrofit getInstance() {
        if (RetrofitInstance == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            RetrofitInstance = new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return RetrofitInstance;
    }

    public static RetrofitInterface getApi() {
        if (RetrofitInterfaceInstance == null) {
            RetrofitInterfaceInstance = getInstance().create(RetrofitInterface.class);
        }
        return RetrofitInterfaceInstance;
    }

    public interface RetrofitInterface {
        @GET("/FashionItem/UserItems/{ItemOwner}")
        Call<ArrayList<FashionItem>> getFashionItemList(@Path("ItemOwner") String userId);

        @GET("/FashionItem/{id}")
        Call<FashionItem> getFashionItem(@Path("id") String id);

        @POST("/FashionItem/add/")
        Call<String> addFashionItem(@Body FashionItem fi);

        @POST("/FashionItem/update/{id}")
        Call<String> updateFashionItem(@Path("id") String _id, @Body FashionItem fi);




        @GET("/OutfitItem/UserItems/{OutfitOwnerID}")
        Call<ArrayList<OutfitItem>> getOutfitItemList(@Path("OutfitOwnerID") String userId);

        @GET("/OutfitItem/{id}")
        Call<OutfitItem> getOutfitItem(@Path("id") String id);

        @POST("/OutfitItem/add/")
        Call<String> addOutfitItem(@Body OutfitItem oi);

        @POST("/OutfitItem/update/{id}")
        Call<String> updateOutfitItem(@Path("id") String _id, @Body OutfitItem fi);


        @GET("/UserAccount/")
        Call<ArrayList<UserAccount>> getUserAccountList();

        @GET("/UserAccount/checkID/{UserID}")
        Call<UserAccount> getUserAccount(@Path("UserID") String id);

        @GET("/UserAccount/{UserID}/{UserPW}")
        Call<UserAccount> signInUserAccount(@Path("UserID") String id, @Path("UserPW") String pw);

        @POST("/UserAccount/add/")
        Call<String> addUserAccount(@Body UserAccount ua);

        @POST("/UserAccount/update/{id}/")
        Call<String> updateUserAccount(@Path("id") String _id, @Body UserAccount ua);


        @GET("/UserPlanData/")
        Call<ArrayList<UserPlanData>> getUserPlanDataList();

        @GET("/FashionItem/{id}")
        Call<UserPlanData> getUserPlanDataItem(@Path("id") String id);

        @POST("/UserPlanData/add/")
        Call<String> addUserPlanData(@Body UserPlanData upd);

        @POST("/UserPlanData/update/{id}/")
        Call<String> updateUserPlanData(@Path("id") String _id, @Body UserPlanData upd);


        @GET("/Consultations/")
        Call<ArrayList<Consultations>> getConsultationDataList();
    }
}
