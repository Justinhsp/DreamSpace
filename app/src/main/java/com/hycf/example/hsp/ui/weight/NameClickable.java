package com.hycf.example.hsp.ui.weight;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.hycf.example.hsp.AppApplication;
import com.hycf.example.hsp.R;


/**
 * 名字点击监听
 */
public class NameClickable extends ClickableSpan implements View.OnClickListener {
    private final ISpanClick mListener;
    private int mPosition;

    public NameClickable(ISpanClick l, int position) {
        mListener = l;
        mPosition = position;
    }

    @Override
    public void onClick(View widget) {
        mListener.onClick(mPosition);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        int colorValue = AppApplication.getAppResources().getColor(
                R.color.main_color);
        ds.setColor(colorValue);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
