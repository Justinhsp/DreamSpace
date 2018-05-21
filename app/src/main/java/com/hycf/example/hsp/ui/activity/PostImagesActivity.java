package com.hycf.example.hsp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hycf.example.hsp.AppApplication;
import com.hycf.example.hsp.R;
import com.hycf.example.hsp.adapter.PostImageAdapter;
import com.hycf.example.hsp.ui.weight.MyCallBack;
import com.hycf.example.hsp.ui.weight.OnRecyclerItemClickListener;
import com.hys.utils.AppUtils;
import com.hys.utils.DensityUtils;
import com.hys.utils.ImageUtils;
import com.hys.utils.InitCacheFileUtils;
import com.hys.utils.SdcardUtils;
import com.hys.utils.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 图片拖拽Activity
 */
public class PostImagesActivity extends AppCompatActivity {

    @Bind(R.id.tv_tips)
    TextView tvTips;
    @Bind(R.id.rv_post_image)
    RecyclerView rvPostImage;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;

    public static final String FILE_DIR_NAME = "com.hycf.example.hsp";//应用缓存地址
    public static final String FILE_IMG_NAME = "images";//放置图片缓存
    public static final int IMAGE_SIZE = 9;//可添加图片最大数
    private static final int REQUEST_IMAGE = 1002;
    private ArrayList<String> originImages;//原始图片
    private ArrayList<String> dragImages;//压缩长宽后图片
    private Context mContext;
    private PostImageAdapter mAdapter;
    private ItemTouchHelper itemTouchHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_images);
        ButterKnife.bind(this);
        initData();
        //初始化RecycleView
        initRecycleView();
    }

    public static void startPostActivity(Context context, ArrayList<String> images) {
        Intent intent = new Intent(context, PostImagesActivity.class);
        intent.putStringArrayListExtra("img", images);
        context.startActivity(intent);
    }


    public void initData() {
        originImages = getIntent().getStringArrayListExtra("img");
        mContext = getApplicationContext();
        InitCacheFileUtils.initImgDir(FILE_DIR_NAME, FILE_IMG_NAME);//清除图片缓存
        //添加按钮图片资源
        String plusPath = getString(R.string.glide_plus_icon_string) + AppUtils.getPackageInfo(mContext).packageName + "/mipmap/" + R.mipmap.mine_btn_plus;
        dragImages = new ArrayList<>();
        originImages.add(plusPath);//添加按键，超过9张时在adapter中隐藏
        dragImages.addAll(originImages);
        //开启线程，在新线程中去压缩图片
        new Thread(new MyRunnable(dragImages, originImages, dragImages, myHandler, false)).start();
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        mAdapter = new PostImageAdapter(mContext, dragImages);
        rvPostImage.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        rvPostImage.setAdapter(mAdapter);
        MyCallBack myCallBack = new MyCallBack(mAdapter, dragImages, originImages);
        itemTouchHelper = new ItemTouchHelper(myCallBack);
        itemTouchHelper.attachToRecyclerView(rvPostImage);//绑定RecyclerView
        //事件监听
        rvPostImage.addOnItemTouchListener(new OnRecyclerItemClickListener(rvPostImage) {

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                if (originImages.get(vh.getAdapterPosition()).contains(getString(R.string.glide_plus_icon_string))) {//打开相册
                    MultiImageSelector.create()
                            .showCamera(true)
                            .count(IMAGE_SIZE - originImages.size() + 1)
                            .multi()
                            .start(PostImagesActivity.this, REQUEST_IMAGE);
                } else {
                    ToastUtils.getInstance().show(AppApplication.getInstance().getContext(), "预览图片");
                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //如果item不是最后一个，则执行拖拽
                if (vh.getLayoutPosition() != dragImages.size() - 1) {
                    itemTouchHelper.startDrag(vh);
                }
            }
        });
        myCallBack.setDragListener(new MyCallBack.DragListener() {
            @Override
            public void deleteState(boolean delete) {
                if (delete) {
                    tvTips.setBackgroundResource(R.color.holo_red_dark);
                    tvTips.setText(getResources().getString(R.string.post_delete_tv_s));
                } else {
                    tvTips.setText(getResources().getString(R.string.post_delete_tv_d));
                    tvTips.setBackgroundResource(R.color.holo_red_light);
                }
            }

            @Override
            public void dragState(boolean start) {
                if (start) {
                    tvTips.setVisibility(View.VISIBLE);
                } else {
                    tvTips.setVisibility(View.GONE);
                }
            }

            @Override
            public void clearView() {
                refreshLayout();
            }
        });
    }

    //=====================================================图片相关============================================================//

    /**
     * 使用OnActivity拿到回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {//从相册选择完图片
            //压缩图片
            new Thread(new MyRunnable(data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT),
                    originImages, dragImages, myHandler, true)).start();
        }
    }


    /**
     * 另起线程压缩图片
     */
    static class MyRunnable implements Runnable {
        ArrayList<String> images;
        ArrayList<String> originImages;
        ArrayList<String> dragImages;
        Handler handler;
        boolean add;//是否为添加图片

        public MyRunnable(ArrayList<String> images, ArrayList<String> originImages, ArrayList<String> dragImages, Handler handler, boolean add) {
            this.images = images;
            this.originImages = originImages;
            this.dragImages = dragImages;
            this.handler = handler;
            this.add = add;
        }

        @Override
        public void run() {
            SdcardUtils sdcardUtils = new SdcardUtils();
            String filePath;
            Bitmap newBitmap;
            int addIndex = originImages.size() - 1;
            for (int i = 0; i < images.size(); i++) {
                if (images.get(i).contains(AppApplication.getInstance().getString(R.string.glide_plus_icon_string))) {//说明是添加图片按钮
                    continue;
                }
                //压缩
                newBitmap = ImageUtils.compressScaleByWH(images.get(i),
                        DensityUtils.dp2px(AppApplication.getInstance().getContext(), 100),
                        DensityUtils.dp2px(AppApplication.getInstance().getContext(), 100));
                //文件地址
                filePath = sdcardUtils.getSDPATH() + FILE_DIR_NAME + "/"
                        + FILE_IMG_NAME + "/" + String.format("img_%d.jpg", System.currentTimeMillis());
                //保存图片
                ImageUtils.save(newBitmap, filePath, Bitmap.CompressFormat.JPEG, true);
                //设置值
                if (!add) {
                    images.set(i, filePath);
                } else {//添加图片，要更新
                    dragImages.add(addIndex, filePath);
                    originImages.add(addIndex++, filePath);
                }
            }
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    /**
     * 使用Handler通知UI更新
     */
    private MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private WeakReference<Activity> reference;

        public MyHandler(Activity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PostImagesActivity activity = (PostImagesActivity) reference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        activity.mAdapter.notifyDataSetChanged();
                        activity.refreshLayout();
                        break;
                }
            }
        }
    }


    /**
     * 刷新地理位置等布局
     */
    private void refreshLayout() {
        //判断提醒谁布局看是否需要下移
        int row = mAdapter.getItemCount() / 3;
        row = 0 == mAdapter.getItemCount() % 3 ? row : row + 1;
        row = 4 == row ? 3 : row;//row最多为三行
        int marginTop = (getResources().getDimensionPixelSize(R.dimen.article_img_margin_top)
                + getResources().getDimensionPixelSize(R.dimen.article_img_dimens))
                * row
                + getResources().getDimensionPixelSize(R.dimen.article_post_et_h)
                + 10;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llBottom.getLayoutParams();
        params.setMargins(0, marginTop, 0, 0);
        llBottom.setLayoutParams(params);
    }


    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
    }

}
