package cn.syxg.recycleviewdemo;





import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * Created by Administrator on 2018/7/9.
 */

public class BookAdapter extends BaseSectionQuickAdapter<BookSection,BaseViewHolder>{

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public BookAdapter(int layoutResId, int sectionHeadResId, List<BookSection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, BookSection item) {

        helper.setText(R.id.tv_heard,item.header);

    }

    @Override
    protected void convert(BaseViewHolder helper, BookSection item) {

        helper.setText(R.id.content_author,item.t.getAuter());
        helper.setText(R.id.content_name,item.t.getName());

    }
}
