
package com.techco.igotrip.data.network;

import com.techco.igotrip.data.network.model.response.ExploreDataResponse;
import com.techco.igotrip.data.network.model.response.FirstDataResponse;
import com.techco.igotrip.data.network.model.response.LoginResponse;
import com.techco.igotrip.data.network.model.response.SelectNationResponse;
import com.techco.igotrip.data.network.model.response.SelectTypeResponse;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Nhat on 12/13/17.
 */


public interface ApiHelper {

    ApiHeader getApiHeader();

    Observable<FirstDataResponse> getFirstData();

    Observable<SelectNationResponse> selectNation(Map<String, String> params);

    Observable<ExploreDataResponse> getExploreData();

    Observable<SelectTypeResponse> selectType(int typeId);

    Observable<LoginResponse> login(Map<String, String> params);

    Observable<LoginResponse> signUp(Map<String, String> params);

    Observable<LoginResponse> loginSocial(Map<String, String> params);
}
