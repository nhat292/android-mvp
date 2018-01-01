

package com.techco.igotrip.ui.mytrip;

import com.techco.igotrip.data.network.model.object.Journey;
import com.techco.igotrip.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


public interface MyTripBaseView extends BaseView {
    void onShowRefreshLoading();
    void onHideRefreshLoading();
    void onGetJourneysSuccess(List<Journey> journeys);
    void onDeleteJourneySuccess();
}
