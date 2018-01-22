

package com.techco.igotrip.ui.post;

import com.techco.igotrip.data.network.model.object.SubType;
import com.techco.igotrip.data.network.model.object.Type;
import com.techco.igotrip.data.network.model.response.PlacesResponse;
import com.techco.igotrip.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


public interface PostBaseView extends BaseView {
    void onSearchPlacesSuccess(PlacesResponse response);
    void onGetTypesSuccess(List<Type> types);
    void onSelectTypeSuccess(List<SubType> subTypes);
    void onPostArticleSuccess();
    void onUpdateArticleSuccess();
}
