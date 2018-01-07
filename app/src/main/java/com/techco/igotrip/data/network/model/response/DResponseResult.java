package com.techco.igotrip.data.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Nhat on 1/6/18.
 */

public class DResponseResult {
    public static final String STATUS_SUCCESS = "OK";
    public static final String STATUS_ZERO = "ZERO_RESULTS";

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("routes")
    @Expose
    private List<DRoute> routes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<DRoute> routes) {
        this.routes = routes;
    }

    public static class DOverviewPolyLine {
        @SerializedName("points")
        @Expose
        private String points;

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }
    }

    public static class DDistance {
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("value")
        @Expose
        private int value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class DDuration {
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("value")
        @Expose
        private int value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class DLocation {
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

    public static class DBound {
        @SerializedName("northeast")
        @Expose
        private DLocation northeast;
        @SerializedName("southwest")
        @Expose
        private DLocation southwest;

        public DLocation getNortheast() {
            return northeast;
        }

        public void setNortheast(DLocation northeast) {
            this.northeast = northeast;
        }

        public DLocation getSouthwest() {
            return southwest;
        }

        public void setSouthwest(DLocation southwest) {
            this.southwest = southwest;
        }
    }

    public static class DStep {
        @SerializedName("distance")
        @Expose
        private DDistance distance;
        @SerializedName("duration")
        @Expose
        private DDuration duration;
        @SerializedName("start_location")
        @Expose
        private DLocation startLocation;
        @SerializedName("end_location")
        @Expose
        private DLocation endLocation;
        @SerializedName("html_instructions")
        @Expose
        private String htmlInstruction;
        @SerializedName("travel_mode")
        @Expose
        private String travelMode;
        @SerializedName("polyline")
        @Expose
        private DOverviewPolyLine polyLine;

        public DDistance getDistance() {
            return distance;
        }

        public void setDistance(DDistance distance) {
            this.distance = distance;
        }

        public DDuration getDuration() {
            return duration;
        }

        public void setDuration(DDuration duration) {
            this.duration = duration;
        }

        public DLocation getStartLocation() {
            return startLocation;
        }

        public void setStartLocation(DLocation startLocation) {
            this.startLocation = startLocation;
        }

        public DLocation getEndLocation() {
            return endLocation;
        }

        public void setEndLocation(DLocation endLocation) {
            this.endLocation = endLocation;
        }

        public String getHtmlInstruction() {
            return htmlInstruction;
        }

        public void setHtmlInstruction(String htmlInstruction) {
            this.htmlInstruction = htmlInstruction;
        }

        public String getTravelMode() {
            return travelMode;
        }

        public void setTravelMode(String travelMode) {
            this.travelMode = travelMode;
        }

        public DOverviewPolyLine getPolyLine() {
            return polyLine;
        }

        public void setPolyLine(DOverviewPolyLine polyLine) {
            this.polyLine = polyLine;
        }
    }

    public static class Dleg {
        @SerializedName("distance")
        @Expose
        private DDistance distance;
        @SerializedName("duration")
        @Expose
        private DDuration duration;
        @SerializedName("start_location")
        @Expose
        private DLocation startLocation;
        @SerializedName("end_location")
        @Expose
        private DLocation endLocation;
        @SerializedName("start_address")
        @Expose
        private String startAddress;
        @SerializedName("end_address")
        @Expose
        private String endAddress;
        @SerializedName("steps")
        @Expose
        private List<DStep> steps;

        public DDistance getDistance() {
            return distance;
        }

        public void setDistance(DDistance distance) {
            this.distance = distance;
        }

        public DDuration getDuration() {
            return duration;
        }

        public void setDuration(DDuration duration) {
            this.duration = duration;
        }

        public DLocation getStartLocation() {
            return startLocation;
        }

        public void setStartLocation(DLocation startLocation) {
            this.startLocation = startLocation;
        }

        public DLocation getEndLocation() {
            return endLocation;
        }

        public void setEndLocation(DLocation endLocation) {
            this.endLocation = endLocation;
        }

        public String getStartAddress() {
            return startAddress;
        }

        public void setStartAddress(String startAddress) {
            this.startAddress = startAddress;
        }

        public String getEndAddress() {
            return endAddress;
        }

        public void setEndAddress(String endAddress) {
            this.endAddress = endAddress;
        }

        public List<DStep> getSteps() {
            return steps;
        }

        public void setSteps(List<DStep> steps) {
            this.steps = steps;
        }
    }

    public static class DRoute {
        @SerializedName("bounds")
        @Expose
        private DBound bound;
        @SerializedName("copyrights")
        @Expose
        private String copyrights;
        @SerializedName("legs")
        @Expose
        private List<Dleg> legs;
        @SerializedName("overview_polyline")
        @Expose
        private DOverviewPolyLine overviewPolyline;

        public DBound getBound() {
            return bound;
        }

        public void setBound(DBound bound) {
            this.bound = bound;
        }

        public String getCopyrights() {
            return copyrights;
        }

        public void setCopyrights(String copyrights) {
            this.copyrights = copyrights;
        }

        public List<Dleg> getLegs() {
            return legs;
        }

        public void setLegs(List<Dleg> legs) {
            this.legs = legs;
        }

        public DOverviewPolyLine getOverviewPolyline() {
            return overviewPolyline;
        }

        public void setOverviewPolyline(DOverviewPolyLine overviewPolyline) {
            this.overviewPolyline = overviewPolyline;
        }
    }
}
