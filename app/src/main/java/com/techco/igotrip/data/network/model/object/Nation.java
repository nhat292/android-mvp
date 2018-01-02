package com.techco.igotrip.data.network.model.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nhat on 12/15/17.
 */

public class Nation implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("continent_id")
    @Expose
    private int continentId;
    @SerializedName("acronym")
    @Expose
    private String countryCode;
    @SerializedName("sequence")
    @Expose
    private int sequence;

    protected Nation(Parcel in) {
        id = in.readInt();
        name = in.readString();
        continentId = in.readInt();
        countryCode = in.readString();
        sequence = in.readInt();
    }

    public static final Creator<Nation> CREATOR = new Creator<Nation>() {
        @Override
        public Nation createFromParcel(Parcel in) {
            return new Nation(in);
        }

        @Override
        public Nation[] newArray(int size) {
            return new Nation[size];
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

    public int getContinentId() {
        return continentId;
    }

    public void setContinentId(int continentId) {
        this.continentId = continentId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(continentId);
        parcel.writeString(countryCode);
        parcel.writeInt(sequence);
    }
}
