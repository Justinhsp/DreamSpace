package com.hycf.example.hsp.model;

import com.hycf.example.hsp.api.Api;
import com.hycf.example.hsp.api.HostType;
import com.hycf.example.hsp.bean.GirlData;
import com.hycf.example.hsp.bean.PhotoGirl;
import com.hycf.example.hsp.contract.BelleContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * 美女Model
 */
public class BelleModel implements BelleContract.Model {
    @Override
    public Observable<List<PhotoGirl>> getPhotoListData(int size, int page) {
        return Api.getDefault(HostType.GANK_GIRL_PHOTO)
                .getPhotoList(Api.getCacheControl(),size,page)
                .map(new Func1<GirlData, List<PhotoGirl>>() {
                    @Override
                    public List<PhotoGirl> call(GirlData girlData) {
                        return girlData.getResults();
                    }
                }).compose(RxSchedulers.<List<PhotoGirl>>io_main());
    }
}
