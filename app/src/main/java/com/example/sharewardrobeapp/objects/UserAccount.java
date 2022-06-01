package com.example.sharewardrobeapp.objects;

public class UserAccount
{
    private String UserPW;

    private String UserName;

    private String UserID;

    public String getUserPW ()
    {
        return UserPW;
    }

    public void setUserPW (String UserPW)
    {
        this.UserPW = UserPW;
    }

    public String getUserName ()
    {
        return UserName;
    }

    public void setUserName (String UserName)
    {
        this.UserName = UserName;
    }

    public String getUserID ()
    {
        return UserID;
    }

    public void setUserID (String UserID)
    {
        this.UserID = UserID;
    }

    @Override
    public String toString()
    {
        return "UserAccount [UserPW = "+UserPW+", UserName = "+UserName+", UserID = "+UserID+"]";
    }
}
