
package com.techco.igotrip.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Nhat on 12/13/17.
 */


public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private int mCurrentPosition;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind(int position) {
        mCurrentPosition = position;
        clear();
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }
}
