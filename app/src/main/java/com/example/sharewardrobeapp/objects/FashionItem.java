package com.example.sharewardrobeapp.objects;

public class FashionItem {
    private String ItemFabric;

    private String ItemColor;

    private String ItemDesc;

    private String ItemSeason;

    private String __v;

    private String ItemPrice;

    private String ItemLocation;

    private String ItemName;

    private String _id;

    private String ItemBrand;

    private String ItemSize;

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

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String ItemPrice) {
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

    @Override
    public String toString() {
        return "FashionItem [ItemFabric = " + ItemFabric + ", ItemColor = " + ItemColor + ", ItemDesc = " + ItemDesc + ", ItemSeason = " + ItemSeason + ", __v = " + __v + ", ItemPrice = " + ItemPrice + ", ItemLocation = " + ItemLocation + ", ItemName = " + ItemName + ", _id = " + _id + ", ItemBrand = " + ItemBrand + ", ItemSize = " + ItemSize + "]";
    }
}
