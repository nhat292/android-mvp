package com.techco.igotrip.data.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.data.network.model.object.Type;

import java.util.List;

/**
 * Created by Nhat on 12/17/17.
 */

public class ExploreDataResponse {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ExploreDataResponse.ExploreData data;

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

    public ExploreData getData() {
        return data;
    }

    public void setData(ExploreData data) {
        this.data = data;
    }

    public static class ExploreData {
        @SerializedName("types")
        @Expose
        private List<Type> types;
        @SerializedName("house_types")
        @Expose
        private List<SubType> subTypes;

        public List<Type> getTypes() {
            return types;
        }

        public void setTypes(List<Type> types) {
            this.types = types;
        }

        public List<SubType> getSubTypes() {
            return subTypes;
        }

        public void setSubTypes(List<SubType> subTypes) {
            this.subTypes = subTypes;
        }
    }
}
