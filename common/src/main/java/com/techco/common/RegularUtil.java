package com.techco.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nhat on 4/12/17.
 */

public class RegularUtil {

    public static boolean isValidPhoneNumber(String number) {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }

    public static boolean isValidEmail(String string) {
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(string);
        boolean matchFound = m.matches();
        return matchFound;
    }
}
