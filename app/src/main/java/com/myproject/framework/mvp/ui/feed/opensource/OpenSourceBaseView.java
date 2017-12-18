
package com.myproject.framework.mvp.ui.feed.opensource;

import com.myproject.framework.mvp.data.network.model.OpenSourceResponse;
import com.myproject.framework.mvp.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


public interface OpenSourceBaseView extends BaseView {

    void updateRepo(List<OpenSourceResponse.Repo> repoList);
}
