package com.hycf.example.hsp.utils;

import android.text.TextUtils;

import java.io.File;


/**
 */
public class ImageUtil {
private static String BASE_PHOTO_URL="";
    /**
     * @param url
     * @return
     */
    public static String getImageUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("http")||new File(url).isFile()) {
                return url;
            } else {
                return BASE_PHOTO_URL+url;
            }
        } else {
            return "";
        }
    }

}

