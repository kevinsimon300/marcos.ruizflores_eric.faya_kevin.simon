package edu.androidprpr2.marcosruizflores_ericfaya_kevinsimon.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Stat implements Parcelable {
    private String name;
    private int value;

    // Constructor, getters, setters, etc.

    // Implementación de Parcelable
    protected Stat(Parcel in) {
        name = in.readString();
        value = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Stat> CREATOR = new Creator<Stat>() {
        @Override
        public Stat createFromParcel(Parcel in) {
            return new Stat(in);
        }

        @Override
        public Stat[] newArray(int size) {
            return new Stat[size];
        }
    };

    // Otros métodos si los hay
}

