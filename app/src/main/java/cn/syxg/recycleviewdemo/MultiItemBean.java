package cn.syxg.recycleviewdemo;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/10.
 */

public class MultiItemBean implements MultiItemEntity {

    public static final int WEBVIEWTITLE = 1;
    public static final int WEBVIEW = 2;
    public static final int INFO = 3;
    public static final int RELATED = 4;
    public static final int COMMENT = 5;

    List<CommentDetailBean> commentList;

    public List<CommentDetailBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentDetailBean> commentList) {
        this.commentList = commentList;
    }

    private int itemType;

    public MultiItemBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
