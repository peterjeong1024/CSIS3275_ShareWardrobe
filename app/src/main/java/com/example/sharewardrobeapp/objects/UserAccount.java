package com.example.sharewardrobeapp.objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.sharewardrobeapp.util.ConstantValue;
import com.example.sharewardrobeapp.util.UseLog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UserAccount implements Parcelable {
    private String _id;
    private String UserPW = null;
    private String UserName = null;
    private String UserID = null;
    private String UserEmail = null;
    private boolean IsGoogleAccount = false;

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

    public UserAccount(String userID, String userPW, String userName, String userEmail, boolean isGoogleAccount) {
        UserPW = userPW;
        UserName = userName;
        UserID = userID;
        UserEmail = userEmail;
        IsGoogleAccount = isGoogleAccount;
    }

    protected UserAccount(Parcel in) {
        _id = in.readString();
        UserPW = in.readString();
        UserName = in.readString();
        UserID = in.readString();
        UserEmail = in.readString();
        IsGoogleAccount = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(UserPW);
        dest.writeString(UserName);
        dest.writeString(UserID);
        dest.writeString(UserEmail);
        dest.writeByte((byte) (IsGoogleAccount ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
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

    // user class method
    public boolean isLogin(Context context) {
        mSPref = context.getSharedPreferences(ConstantValue.SHARED_PREFERENCE_FILENAME, Context.MODE_PRIVATE);
        UserID = mSPref.getString(ConstantValue.SHARED_PREFERENCE_ID_VALUE, null);
        UserPW = mSPref.getString(ConstantValue.SHARED_PREFERENCE_PASSWORD_VALUE, null);
        IsGoogleAccount = mSPref.getBoolean(ConstantValue.SHARED_PREFERENCE_IS_GOOGLE_ACCOUNT, false);

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
        editor.putBoolean(ConstantValue.SHARED_PREFERENCE_IS_GOOGLE_ACCOUNT, IsGoogleAccount);
        if (editor.commit()) {
            return true;
        } else {
            UseLog.w("Something was wrong during SharedPreferences logic");
            return false;
        }
    }

    public void tryLogout(Context context) {
        UseLog.d("toString() : " + toString());

        UserID = null;
        UserPW = null;

        SharedPreferences pref = context.getSharedPreferences(ConstantValue.SHARED_PREFERENCE_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();

        if (IsGoogleAccount) {
            UseLog.d("getIsGoogleAccount() : " + getIsGoogleAccount());
            GoogleSignInOptions mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, mGoogleSignInOptions);
            mGoogleSignInClient.signOut();
        }
    }

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

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public boolean getIsGoogleAccount() {
        return IsGoogleAccount;
    }

    public void setIsGoogleAccount(boolean isGoogleAccount) {
        IsGoogleAccount = isGoogleAccount;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "_id='" + _id + '\'' +
                ", UserPW='" + UserPW + '\'' +
                ", UserName='" + UserName + '\'' +
                ", UserID='" + UserID + '\'' +
                ", UserEmail='" + UserEmail + '\'' +
                ", IsGoogleAccount='" + IsGoogleAccount + '\'' +
                ", mSPref=" + mSPref +
                '}';
    }

}
