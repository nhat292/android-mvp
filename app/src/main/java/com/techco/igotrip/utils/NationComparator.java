package com.techco.igotrip.utils;


import com.techco.igotrip.data.network.model.object.Nation;

import java.util.Comparator;

/**
 * Created by Nhat on 12/16/17.
 */

public class NationComparator implements Comparator<Nation> {

    @Override
    public int compare(Nation n1, Nation n2) {
        return n1.getSequence() < n2.getSequence() ? -1 : n1.getSequence() > n2.getSequence() ? 1 : 0;
    }
}
