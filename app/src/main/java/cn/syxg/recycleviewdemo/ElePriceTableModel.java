package cn.syxg.recycleviewdemo;

/**
 * Created by Administrator on 2018/6/21.
 */

public class ElePriceTableModel {

    private String mContent;
    private int mHeightMultiple; //高度的倍数，-1代表表头
    private boolean mIsHighlight = false; //是否高亮显示

    public ElePriceTableModel(String content, int heightMultiple, boolean isHighlight) {
        mContent = content;
        mHeightMultiple = heightMultiple;
        mIsHighlight = isHighlight;
    }

    public ElePriceTableModel(String content, int heightMultiple) {
        mContent = content;
        mHeightMultiple = heightMultiple;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int getHeightMultiple() {
        return mHeightMultiple;
    }

    public void setHeightMultiple(int heightMultiple) {
        mHeightMultiple = heightMultiple;
    }

    public boolean isHighlight() {
        return mIsHighlight;
    }

    public void setHighlight(boolean highlight) {
        mIsHighlight = highlight;
    }

}
