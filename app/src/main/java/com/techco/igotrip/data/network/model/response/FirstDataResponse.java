package com.techco.igotrip.data.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techco.igotrip.data.network.model.object.Continent;
import com.techco.igotrip.data.network.model.object.Nation;

import java.util.List;

/**
 * Created by Nhat on 12/15/17.
 */

public class FirstDataResponse {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private FirstData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FirstData getData() {
        return data;
    }

    public void setData(FirstData data) {
        this.data = data;
    }

    public static class FirstData {
        @SerializedName("continents")
        @Expose
        private List<Continent> continents;
        @SerializedName("nations")
        @Expose
        private List<Nation> nations;

        public List<Continent> getContinents() {
            return continents;
        }

        public void setContinents(List<Continent> continents) {
            this.continents = continents;
        }

        public List<Nation> getNations() {
            return nations;
        }

        public void setNations(List<Nation> nations) {
            this.nations = nations;
        }
    }
}
