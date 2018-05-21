package com.hycf.example.hsp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.aspsine.irecyclerview.baseadapter.BaseReclyerViewAdapter;
import com.hycf.example.hsp.bean.CircleItem;
import com.hycf.example.hsp.presenter.CircleFriendPresenter;

/**
 * 朋友圈适配器
 */
public class CircleFriendAdapter extends BaseReclyerViewAdapter<CircleItem> {

    public static final int ITEM_VIEW_TYPE_DEFAULT = 0;
    public static final int ITEM_VIEW_TYPE_IMAGE = 1;
    public static final int ITEM_VIEW_TYPE_URL = 2;

    private Context mContext;
    private CircleFriendPresenter mPresenter;

    public CircleFriendAdapter(Context context,CircleFriendPresenter mPresenter) {
        super(context);
        this.mContext=context;
        this.mPresenter=mPresenter;
    }

    @Override
    public int getItemViewType(int position) {
        return getData().get(position).getType();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CircleFriendHolder.create(mContext,viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if(holder instanceof CircleFriendHolder) {
            ((CircleFriendHolder) holder).setData(mPresenter, get(position), position);
        }
    }
}
