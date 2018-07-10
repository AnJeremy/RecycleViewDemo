package cn.syxg.recycleviewdemo;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/10.
 */

public class RelatedAdapter extends BaseQuickAdapter<Book,BaseViewHolder>{

    public RelatedAdapter(int layoutResId, @Nullable List<Book> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Book item) {

    }
}
