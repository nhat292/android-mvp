package com.techco.igotrip.data.network.model.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nhat on 1/6/18.
 */

public class PlaceDetail {
    @SerializedName("formatted_address")
    @Expose
    private String addressName;
    @SerializedName("geometry")
    @Expose
    private PGeometry geometry;

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public PGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(PGeometry geometry) {
        this.geometry = geometry;
    }

    public static class PGeometry {
        @SerializedName("location")
        @Expose
        private PLocation location;

        public PLocation getLocation() {
            return location;
        }

        public void setLocation(PLocation location) {
            this.location = location;
        }
    }

    public static class PLocation {
        @SerializedName("lat")
        @Expose
        private double lat;
        @SerializedName("lng")
        @Expose
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }
}
