package com.hycf.example.hsp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hycf.example.hsp.R;
import com.jaydenxiao.common.base.BaseActivity;

import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 放微信发朋友圈
 * 主Activity
 */
public class WeChatActivity extends BaseActivity {


    private static final int REQUEST_IMAGE = 1001;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wechat;
    }


    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.icon_back, R.id.icon_send})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.icon_back:
                finish();
                break;
            case R.id.icon_send:
                MultiImageSelector.create()
                        .showCamera(true) // show camera or not. true by default
                        .count(9) // max select image size, 9 by default. used width #.multi()
                        .multi() // multi mode, default mode;
                        .start(WeChatActivity.this, REQUEST_IMAGE);
                break;
                  default:
                      break;
        }
    }

/*    @OnLongClick(R.id.icon_send)
    public void OnLongClick(View view){

    }*/


    /**
     * 使用onActivityResult
     *  拿到回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {//文章图片
            PostImagesActivity.startPostActivity(WeChatActivity.this,
                    data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT));
        }
    }


    /**
     * Activity 启动入口
     *
     * @param context
     */
    public static void startAction(Context context) {
        Intent intent = new Intent(context, WeChatActivity.class);
        context.startActivity(intent);
    }

}
