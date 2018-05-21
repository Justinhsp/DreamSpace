package com.hycf.example.hsp.ui.weight;

import android.text.SpannableString;

import com.jaydenxiao.common.commonutils.ToastUitl;

/**
 * 点赞和评论中人名的点击事件回调
 */
public class NameClickListener implements ISpanClick {
    private SpannableString userName;
    private String userId;

    public NameClickListener(SpannableString name, String userid) {
        this.userName = name;
        this.userId = userid;
    }

    @Override
    public void onClick(int position) {
        ToastUitl.showShort("点击了"+position);
    }
}
