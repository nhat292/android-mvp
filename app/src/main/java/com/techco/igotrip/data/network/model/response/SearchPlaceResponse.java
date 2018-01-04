package com.techco.igotrip.data.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nhat on 1/4/18.
 */

public class SearchPlaceResponse {

    @SerializedName("next_page_token")
    @Expose
    private String nextPageToken;
    @SerializedName("results")
    @Expose
    private List<SResult> results;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<SResult> getResults() {
        return results;
    }

    public void setResults(List<SResult> results) {
        this.results = results;
    }

    public static class SResult {
        @SerializedName("formatted_address")
        @Expose
        private String formattedAddress;
        @SerializedName("geometry")
        @Expose
        private SGeometry geometry;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("icon")
        @Expose
        private String icon;

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        public SGeometry getGeometry() {
            return geometry;
        }

        public void setGeometry(SGeometry geometry) {
            this.geometry = geometry;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class SGeometry {
        @SerializedName("location")
        @Expose
        public SLocation location;
        @SerializedName("viewport")
        @Expose
        private SViewPort viewPort;

        public SLocation getLocation() {
            return location;
        }

        public void setLocation(SLocation location) {
            this.location = location;
        }

        public SViewPort getViewPort() {
            return viewPort;
        }

        public void setViewPort(SViewPort viewPort) {
            this.viewPort = viewPort;
        }
    }

    public static class SViewPort {
        @SerializedName("northeast")
        @Expose
        private SLocation northeast;
        @SerializedName("southwest")
        @Expose
        private SLocation southweat;

        public SLocation getNortheast() {
            return northeast;
        }

        public void setNortheast(SLocation northeast) {
            this.northeast = northeast;
        }

        public SLocation getSouthweat() {
            return southweat;
        }

        public void setSouthweat(SLocation southweat) {
            this.southweat = southweat;
        }
    }

    public static class SLocation {
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
