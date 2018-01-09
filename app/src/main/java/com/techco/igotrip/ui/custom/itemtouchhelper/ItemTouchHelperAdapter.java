package com.techco.igotrip.ui.custom.itemtouchhelper;

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);


    void onItemDismiss(int position);
}