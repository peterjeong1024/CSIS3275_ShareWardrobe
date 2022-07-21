package com.example.sharewardrobeapp.objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;

public class UserAccount implements Parcelable {
    private String _id;

    private String UserPW = null;

    private String UserName = null;

    private String UserID = null;

    private SharedPreferences mSPref;

    public UserAccount() {
    }

    public UserAccount(String id, String pw) {
        this.UserID = id;
        this.UserPW = pw;
    }

    public UserAccount(String id, String pw, String userName) {
        this.UserID = id;
        this.UserPW = pw;
        this.UserName = userName;
    }

    protected UserAccount(Parcel in) {
        _id = in.readString();
        UserPW = in.readString();
        UserName = in.readString();
        UserID = in.readString();
    }

    // user class method
    public boolean isLogin(Context context) {
        mSPref = context.getSharedPreferences(ConstantValue.SHARED_PREFERENCE_FILENAME, Context.MODE_PRIVATE);
        UserID = mSPref.getString(ConstantValue.SHARED_PREFERENCE_ID_VALUE, null);
        UserPW = mSPref.getString(ConstantValue.SHARED_PREFERENCE_PASSWORD_VALUE, null);

        return UserID != null && UserPW != null;
    }

    public boolean tryLogin(Context context) {
        if (UserID == null || UserPW == null) {
            UseLog.w("ID or Password is not available.");
            return false;
        }
        mSPref = context.getSharedPreferences(ConstantValue.SHARED_PREFERENCE_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSPref.edit();
        editor.putString(ConstantValue.SHARED_PREFERENCE_ID_VALUE, UserID);
        editor.putString(ConstantValue.SHARED_PREFERENCE_PASSWORD_VALUE, UserPW);
        if (editor.commit()) {
            return true;
        } else {
            UseLog.w("Something was wrong during SharedPreferences logic");
            return false;
        }
    }

    public void tryLogout(Context context) {
        UserID = null;
        UserPW = null;

        SharedPreferences pref = context.getSharedPreferences(ConstantValue.SHARED_PREFERENCE_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public static final Creator<UserAccount> CREATOR = new Creator<UserAccount>() {
        @Override
        public UserAccount createFromParcel(Parcel in) {
            return new UserAccount(in);
        }

        @Override
        public UserAccount[] newArray(int size) {
            return new UserAccount[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserPW() {
        return UserPW;
    }

    public void setUserPW(String UserPW) {
        this.UserPW = UserPW;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    @Override
    public String toString() {
        return "UserAccount [_id = " + _id + ", UserPW = " + UserPW + ", UserName = " + UserName + ", UserID = " + UserID + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(UserPW);
        parcel.writeString(UserName);
        parcel.writeString(UserID);
    }
}
