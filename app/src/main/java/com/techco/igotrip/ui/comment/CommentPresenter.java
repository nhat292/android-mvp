
package com.techco.igotrip.ui.comment;

import com.androidnetworking.error.ANError;
import com.techco.igotrip.data.DataManager;
import com.techco.igotrip.ui.base.BasePresenter;
import com.techco.igotrip.utils.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Nhat on 12/13/17.
 */


public class CommentPresenter<V extends CommentBaseView> extends BasePresenter<V>
        implements CommentMvpPresenter<V> {


    @Inject
    public CommentPresenter(DataManager dataManager,
                            SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

    }

    @Override
    public void getComment(int articleId) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("article_id", String.valueOf(articleId));
        getCompositeDisposable().add(getDataManager()
                .getComments(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onGetCommentsSuccess(response);
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().hideLoading();
                    // handle the error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));
    }

    @Override
    public void checkComment(int userId) {
        if (getDataManager().getUserInfo() != null && getDataManager().getUserInfo().getId() == userId) {
            getMvpView().onShowOptions();
        }
    }

    @Override
    public void deleteComment(int commentId) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(commentId));
        getCompositeDisposable().add(getDataManager()
                .deleteComment(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onDeleteCommentSuccess();
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().hideLoading();
                    // handle the error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));
    }

    @Override
    public void updateComment(int commentId, String newContent) {
        if (!newContent.isEmpty()) {
            getMvpView().showLoading();
            Map<String, String> params = new HashMap<>();
            params.put("id", String.valueOf(commentId));
            params.put("new_content", newContent);
            getCompositeDisposable().add(getDataManager()
                    .updateComment(params)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        getMvpView().hideLoading();
                        getMvpView().onUpdateCommentSuccess(response);
                    }, throwable -> {
                        if (!isViewAttached()) {
                            return;
                        }
                        getMvpView().hideLoading();
                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }));
        }
    }

    @Override
    public void checkLogin() {
        if (getDataManager().getUserInfo() != null) {
            getMvpView().openWriteComment();
        } else {
            getMvpView().openLogin();
        }
    }

    @Override
    public void createComment(String content, int articleId) {
        getMvpView().showLoading();
        Map<String, String> params = new HashMap<>();
        params.put("user_id", String.valueOf(getDataManager().getUserInfo().getId()));
        params.put("comment_content", content);
        params.put("article_id", String.valueOf(articleId));
        getCompositeDisposable().add(getDataManager()
                .createComment(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    getMvpView().hideLoading();
                    getMvpView().onCreateCommentSuccess(response.getComment());
                }, throwable -> {
                    if (!isViewAttached()) {
                        return;
                    }
                    getMvpView().hideLoading();
                    // handle the error here
                    if (throwable instanceof ANError) {
                        ANError anError = (ANError) throwable;
                        handleApiError(anError);
                    }
                }));
    }
}
