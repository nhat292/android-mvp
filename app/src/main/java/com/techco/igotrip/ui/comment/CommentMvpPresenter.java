
package com.techco.igotrip.ui.comment;


import com.techco.igotrip.dagger.PerActivity;
import com.techco.igotrip.ui.base.MvpPresenter;

/**
 * Created by Nhat on 12/13/17.
 */


@PerActivity
public interface CommentMvpPresenter<V extends CommentBaseView> extends MvpPresenter<V> {
    void getComment(int articleId);
    void checkComment(int userId);
    void deleteComment(int commentId);
    void updateComment(int commentId, String newContent);
    void checkLogin();
    void createComment(String content, int articleId);
}
