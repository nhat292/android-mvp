package com.techco.igotrip.enums;

/**
 * Created by Nhat on 1/4/18.
 */

public enum  ArticleMarkerType {
    PLACES(1), ROOM(2), RESTAURANT(3), SHOPPING(4);
    private final int value;
    ArticleMarkerType(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
