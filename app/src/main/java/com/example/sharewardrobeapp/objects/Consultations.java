package com.example.sharewardrobeapp.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import java.util.ArrayList;

public class Consultations implements Parcelable {

    private String ConsultantImg;
    private String ConsultantName;
    private String ConsultantDescription;
    private String ConsultantContactLine;
    private ArrayList<ConsultantScheduleItem> ConsultantSchedule;
    private String _id;
    private String __v;

    public Consultations() {
    }

    protected Consultations(Parcel in) {
        ConsultantImg = in.readString();
        ConsultantName = in.readString();
        ConsultantDescription = in.readString();
        ConsultantContactLine = in.readString();
        _id = in.readString();
        __v = in.readString();
    }

    public static final Creator<Consultations> CREATOR = new Creator<Consultations>() {
        @Override
        public Consultations createFromParcel(Parcel in) {
            return new Consultations(in);
        }

        @Override
        public Consultations[] newArray(int size) {
            return new Consultations[size];
        }
    };

    public String getConsultantImg() {
        return ConsultantImg;
    }

    public void setConsultantImg(String consultantImg) {
        ConsultantImg = consultantImg;
    }

    public String getConsultantName() {
        return ConsultantName;
    }

    public void setConsultantName(String consultantName) {
        ConsultantName = consultantName;
    }

    public String getConsultantDescription() {
        return ConsultantDescription;
    }

    public void setConsultantDescription(String consultantDescription) {
        ConsultantDescription = consultantDescription;
    }

    public String getConsultantContactLine() {
        return ConsultantContactLine;
    }

    public void setConsultantContactLine(String consultantContactLine) {
        ConsultantContactLine = consultantContactLine;
    }

    public ArrayList<ConsultantScheduleItem> getConsultantSchedule() {
        return ConsultantSchedule;
    }

    public void setConsultantSchedule(ArrayList<ConsultantScheduleItem> consultantSchedule) {
        ConsultantSchedule = consultantSchedule;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public Bitmap getConsultantImgBitmap() {
        if (ConsultantImg == null) {
            return null;
        }
        byte[] bytes = Base64.decode(ConsultantImg, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ConsultantImg);
        parcel.writeString(ConsultantName);
        parcel.writeString(ConsultantDescription);
        parcel.writeString(ConsultantContactLine);
        parcel.writeString(_id);
        parcel.writeString(__v);
    }

    public class ConsultantScheduleItem {
        private String Time;
        private boolean IsFree;

        public ConsultantScheduleItem(String time, Boolean isFree) {
            Time = time;
            IsFree = isFree;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }

        public boolean isFree() {
            return IsFree;
        }

        public void setFree(boolean free) {
            IsFree = free;
        }

    }
}
