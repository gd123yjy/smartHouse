package com.example.yjy.smarthouse_android.toolkit.protocol;

import android.text.TextUtils;

/**
 * Created by yjy on 17-5-12.
 */
public class StringHelper {
    public static String winString2Linux(final String content) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        final char[] chars = content.toCharArray();
        char curChar;
        for (int i =0 ; i < chars.length; i++) {
            curChar = chars[i];
            if ('\r' != curChar) {
                buffer.append(curChar);
            }
        }
        return buffer.toString();
    }
}
