package com.hycf.example.hsp.utils;

import com.hycf.example.hsp.AppApplication;

/**
 * 公共工具类
 **/
public class CommonUtils {

    /**
     * 获取dimens定义的大小
     *
     * @param dimensionId
     * @return
     */
    public static int getPixelById(int dimensionId) {
        return AppApplication.getInstance().getResources().getDimensionPixelSize(dimensionId);
    }

}
