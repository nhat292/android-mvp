package com.techco.igotrip.data.network.model.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nhat on 12/15/17.
 */

public class Continent implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;

    private boolean selected;
    private int color = 0;

    protected Continent(Parcel in) {
        id = in.readInt();
        name = in.readString();
        selected = in.readByte() != 0;
        color = in.readInt();
    }

    public static final Creator<Continent> CREATOR = new Creator<Continent>() {
        @Override
        public Continent createFromParcel(Parcel in) {
            return new Continent(in);
        }

        @Override
        public Continent[] newArray(int size) {
            return new Continent[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeByte((byte) (selected ? 1 : 0));
        parcel.writeInt(color);
    }
}
