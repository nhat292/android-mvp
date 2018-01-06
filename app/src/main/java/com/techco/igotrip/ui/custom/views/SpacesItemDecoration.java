package com.techco.igotrip.ui.custom.views;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Nhat on 12/27/17.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private boolean grid;

    public SpacesItemDecoration(int space, boolean grid) {
        this.space = space;
        this.grid = grid;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        } else {
            outRect.top = grid ? space : 0;
        }
    }
}