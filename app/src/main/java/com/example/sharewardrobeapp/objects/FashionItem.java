package com.example.sharewardrobeapp.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import com.example.sharewardrobeapp.util.UseLog;

import java.util.Date;

public class FashionItem implements Parcelable {

    private String _id;

    private String ItemName = "";

    private String ItemOwner = "";

    private String ItemDesc;

    private String ItemCategory;

    private String ItemColor;

    private String ItemFabric;

    private double ItemPrice = 0;

    private String ItemSize;

    private String ItemSeason;

    private String ItemBrand;

    private String ItemImg;

    private String ItemLocation = "";

    private Date ItemBuyDate;

    private int ItemWornCount;

    private String __v;

    public FashionItem() {
        ItemName = "None";
        ItemOwner = "None";
        ItemPrice = 0;
        ItemLocation = "None";
    }

    protected FashionItem(Parcel in) {
        _id = in.readString();
        ItemName = in.readString();
        ItemOwner = in.readString();
        ItemDesc = in.readString();
        ItemCategory = in.readString();
        ItemColor = in.readString();
        ItemFabric = in.readString();
        ItemPrice = in.readDouble();
        ItemSize = in.readString();
        ItemSeason = in.readString();
        ItemBrand = in.readString();
        ItemImg = in.readString();
        ItemLocation = in.readString();
        ItemWornCount = in.readInt();
        __v = in.readString();
    }

    public static final Creator<FashionItem> CREATOR = new Creator<FashionItem>() {
        @Override
        public FashionItem createFromParcel(Parcel in) {
            return new FashionItem(in);
        }

        @Override
        public FashionItem[] newArray(int size) {
            return new FashionItem[size];
        }
    };

    public String getItemImg() {
        return ItemImg;
    }

    public void setItemImg(String itemImg) {
        ItemImg = itemImg;
    }

    public String getItemCategory() {
        return ItemCategory;
    }

    public void setItemCategory(String itemCategory) {
        ItemCategory = itemCategory;
    }

    public void setItemPrice(double itemPrice) {
        ItemPrice = itemPrice;
    }

    public Date getItemBuyDate() {
        return ItemBuyDate;
    }

    public void setItemBuyDate(Date itemBuyDate) {
        ItemBuyDate = itemBuyDate;
    }

    public int getItemWornCount() {
        return ItemWornCount;
    }

    public void setItemWornCount(int itemWornCount) {
        ItemWornCount = itemWornCount;
    }

    public String getItemFabric() {
        return ItemFabric;
    }

    public void setItemFabric(String ItemFabric) {
        this.ItemFabric = ItemFabric;
    }

    public String getItemColor() {
        return ItemColor;
    }

    public void setItemColor(String ItemColor) {
        this.ItemColor = ItemColor;
    }

    public String getItemDesc() {
        return ItemDesc;
    }

    public void setItemDesc(String ItemDesc) {
        this.ItemDesc = ItemDesc;
    }

    public String getItemSeason() {
        return ItemSeason;
    }

    public void setItemSeason(String ItemSeason) {
        this.ItemSeason = ItemSeason;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public double getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(int ItemPrice) {
        this.ItemPrice = ItemPrice;
    }

    public String getItemLocation() {
        return ItemLocation;
    }

    public void setItemLocation(String ItemLocation) {
        this.ItemLocation = ItemLocation;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getItemBrand() {
        return ItemBrand;
    }

    public void setItemBrand(String ItemBrand) {
        this.ItemBrand = ItemBrand;
    }

    public String getItemSize() {
        return ItemSize;
    }

    public void setItemSize(String ItemSize) {
        this.ItemSize = ItemSize;
    }

    public String getItemOwner() {
        return ItemOwner;
    }

    public void setItemOwner(String itemOwner) {
        ItemOwner = itemOwner;
    }

    public Bitmap getItemImgBitmap() {
        if (ItemImg == null || ItemImg.equals("")) {
            return null;
        }

        byte[] bytes = Base64.decode(ItemImg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @Override
    public String toString() {
        return "FashionItem [ItemFabric = " + ItemFabric + ", ItemColor = " + ItemColor + ", ItemDesc = " + ItemDesc + ", ItemSeason = " + ItemSeason + ", __v = " + __v + ", ItemPrice = " + ItemPrice + ", ItemLocation = " + ItemLocation + ", ItemName = " + ItemName + ", _id = " + _id + ", ItemBrand = " + ItemBrand + ", ItemSize = " + ItemSize + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(ItemName);
        parcel.writeString(ItemOwner);
        parcel.writeString(ItemDesc);
        parcel.writeString(ItemCategory);
        parcel.writeString(ItemColor);
        parcel.writeString(ItemFabric);
        parcel.writeDouble(ItemPrice);
        parcel.writeString(ItemSize);
        parcel.writeString(ItemSeason);
        parcel.writeString(ItemBrand);
        parcel.writeString(ItemImg);
        parcel.writeString(ItemLocation);
        parcel.writeInt(ItemWornCount);
        parcel.writeString(__v);
    }
}
