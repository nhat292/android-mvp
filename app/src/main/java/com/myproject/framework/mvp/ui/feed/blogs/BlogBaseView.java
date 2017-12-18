
package com.myproject.framework.mvp.ui.feed.blogs;

import com.myproject.framework.mvp.data.network.model.BlogResponse;
import com.myproject.framework.mvp.ui.base.BaseView;

import java.util.List;

/**
 * Created by Nhat on 12/13/17.
 */


public interface BlogBaseView extends BaseView {

    void updateBlog(List<BlogResponse.Blog> blogList);
}
