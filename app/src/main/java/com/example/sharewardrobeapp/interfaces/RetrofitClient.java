package com.example.sharewardrobeapp.interfaces;

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

public class RetrofitClient {
    //    private static final String Base_URL = "http://localhost:5000/";
//    private static final String Base_URL = "http://192.168.31.18:5000/";
    private static final String Base_URL = "https://sharewardrobe-api-server.herokuapp.com/";

    public static Retrofit getInstance() {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    public static RetrofitInterface getApi() {
        return getInstance().create(RetrofitInterface.class);
    }

    public interface RetrofitInterface {
        @GET("/FashionItem/")
        Call<ArrayList<FashionItem>> getFashionItemList();

        @GET("/FashionItem/:id")
        Call<FashionItem> getFashionItem(@Body int id);

        @POST("/FashionItem/add/")
        Call<String> addFashionItem(@Body FashionItem fi);

        @GET("/OutfitItem/")
        Call<ArrayList<OutfitItem>> getOutfitItemList();

        @GET("/OutfitItem/:id")
        Call<OutfitItem> getOutfitItem(@Body int id);

        @POST("/OutfitItem/add/")
        Call<String> addOutfitItem(@Body OutfitItem oi);

        @GET("/UserAccount/")
        Call<ArrayList<UserAccount>> getUserAccountList();

        @POST("/UserAccount/add/")
        Call<String> addUserAccount(@Body UserAccount ua);

        @GET("/UserPlanData/")
        Call<ArrayList<UserPlanData>> getUserPlanDataList();

        @POST("/UserPlanData/add/")
        Call<String> addUserPlanData(@Body UserPlanData upd);
    }
}
