package com.hycf.example.hsp.ui.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hycf.example.hsp.adapter.FavortListAdapter;


/**
 * 点赞列表
 */
public class FavortListView extends TextView {
    private ISpanClick mSpanClickListener;

    public void setSpanClickListener(ISpanClick listener){
        mSpanClickListener = listener;
    }
    public ISpanClick getSpanClickListener(){
        return  mSpanClickListener;
    }

    public FavortListView(Context context) {
        super(context);
    }

    public FavortListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FavortListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(FavortListAdapter adapter){
        adapter.bindListView(this);
    }

}
