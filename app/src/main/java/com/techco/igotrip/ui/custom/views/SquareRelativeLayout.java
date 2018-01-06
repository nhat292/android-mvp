package com.techco.igotrip.ui.custom.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


/**
 * Created by Nhat on 12/27/17.
 */
public class SquareRelativeLayout extends RelativeLayout {


    public SquareRelativeLayout(Context context) {
        super(context);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int width, int height) {
        // note we are applying the width value as the height
        super.onMeasure(width, width);
    }

}