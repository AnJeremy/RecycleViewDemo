package cn.syxg.recycleviewdemo;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class HotViewAdapter extends BaseQuickAdapter<HotBean,BaseViewHolder>{

    private List<Integer> list;
    private List<String> title;

    private Banner mBanner;


    public HotViewAdapter(int layoutResId, @Nullable List<HotBean> data,List<Integer> list,List<String> title) {
        super(layoutResId, data);
        this.list = list;
        this.title = title;

    }

    @Override
    protected void convert(BaseViewHolder helper, HotBean item) {

        //helper.getLayoutPosition();

        int layoutPosition = helper.getLayoutPosition();

        LinearLayout headerLayout = getHeaderLayout();
         mBanner = headerLayout.findViewById(R.id.hot_banner);

        //Banner mBanner = helper.getView(R.id.hot_banner);


        //View itemView = helper.itemView;
       // Banner mBanner = itemView.findViewById(R.id.hot_banner);

        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);


        mBanner.setImages(list);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerTitles(title);
        mBanner.setDelayTime(5000);

        mBanner.start();
        mBanner.startAutoPlay();

        ////View view = helper.getView(R.id.hot_icon);
      /*  Glide.with(mContext)
                .load("http://img5.imgtn.bdimg.com/it/u=3287998705,4210783580&fm=27&gp=0.jpg")
                .into((ImageView) helper.getView(R.id.hot_icon));*/
   // }

      /*  Glide.with(mContext)
                .load("")
                .transition(new GlideCircleTransform(mContext))
                .into(ivAvator);


*/
       // Glide.with(m).load(url).bitmapTransform(new CropCircleTransformation(this)).crossFade(1000).into(image2);

        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(mContext).load("http://img5.imgtn.bdimg.com/it/u=3287998705,4210783580&fm=27&gp=0.jpg").apply(requestOptions).into((ImageView) helper.getView(R.id.hot_icon));

    }


    public void setBanner(List<String> list,List<String> title){

      //mBanner.update(list,title);

        mBanner.clearFocus();

        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);


        mBanner.setImages(list);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerTitles(title);
        mBanner.setDelayTime(5000);

        mBanner.start();
        mBanner.startAutoPlay();
    }
}
