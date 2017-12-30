

package com.techco.igotrip.ui.comment;

import com.techco.igotrip.data.network.model.object.Comment;
import com.techco.igotrip.data.network.model.response.CommentListResponse;
import com.techco.igotrip.data.network.model.response.CommentResponse;
import com.techco.igotrip.ui.base.BaseView;

/**
 * Created by Nhat on 12/13/17.
 */


public interface CommentBaseView extends BaseView {
    void onGetCommentsSuccess(CommentListResponse response);
    void onShowOptions();
    void onDeleteCommentSuccess();
    void onUpdateCommentSuccess(CommentResponse response);
    void openLogin();
    void openWriteComment();
    void onCreateCommentSuccess(Comment comment);
}
