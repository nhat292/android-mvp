package com.techco.igotrip.data.network.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.techco.igotrip.data.network.model.object.PlaceDetail;

import java.util.List;

/**
 * Created by Nhat on 1/6/18.
 */

public class PlacesResponse {
    @SerializedName("results")
    @Expose
    private List<PlaceDetail> results;

    public List<PlaceDetail> getResults() {
        return results;
    }

    public void setResults(List<PlaceDetail> results) {
        this.results = results;
    }
}
