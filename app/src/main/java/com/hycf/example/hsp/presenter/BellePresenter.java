package com.hycf.example.hsp.presenter;

import com.hycf.example.hsp.R;
import com.hycf.example.hsp.bean.PhotoGirl;
import com.hycf.example.hsp.contract.BelleContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

/**
 * 美女Presenter
 */
public class BellePresenter  extends BelleContract.Presenter{

    @Override
    public void getPhotoListDataRequest(int size, int page) {
         mRxManage.add(mModel.getPhotoListData(size,page).subscribe(new RxSubscriber<List<PhotoGirl>>(mContext,false) {
             @Override
             public void onStart() {
                 super.onStart();
                 mView.showLoading(mContext.getString(R.string.loading));
             }

             @Override
             protected void _onNext(List<PhotoGirl> photoGirls) {
                 mView.returnPhotoListData(photoGirls);
                 mView.stopLoading();
             }

             @Override
             protected void _onError(String message) {
                 mView.showErrorTip(message);
             }
         }));
    }
}
