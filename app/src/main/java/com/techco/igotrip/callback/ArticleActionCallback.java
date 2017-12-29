package com.techco.igotrip.callback;

/**
 * Created by Nhat on 12/29/17.
 */

public interface ArticleActionCallback {

    void onItemClick(int position);

    void onAddJourneyClick(int position);

    void onShareClick(int position);

    void onCommentClick(int position);

    void onAddFavoriteClick(int position);
}
