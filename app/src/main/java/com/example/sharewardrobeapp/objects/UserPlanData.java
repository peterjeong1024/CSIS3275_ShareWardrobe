package com.example.sharewardrobeapp.objects;

public class UserPlanData {
    private String _id;

    private String FItemsSerialize;

    private String UserID;

    private String OutFitsSerialize;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFItemsSerialize() {
        return FItemsSerialize;
    }

    public void setFItemsSerialize(String FItemsSerialize) {
        this.FItemsSerialize = FItemsSerialize;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getOutFitsSerialize() {
        return OutFitsSerialize;
    }

    public void setOutFitsSerialize(String OutFitsSerialize) {
        this.OutFitsSerialize = OutFitsSerialize;
    }

    @Override
    public String toString() {
        return "UserPlanData [_id = " + _id + ", FItemsSerialize = " + FItemsSerialize + ", UserID = " + UserID + ", OutFitsSerialize = " + OutFitsSerialize + "]";
    }
}
