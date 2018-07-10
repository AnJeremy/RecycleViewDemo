package cn.syxg.recycleviewdemo;

/**
 * Created by Administrator on 2018/6/21.
 */

public class GridleBean {

    private String title;

    private int imageId;

    public GridleBean(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public GridleBean(String title) {
        this.title = title;
    }

    public GridleBean() {
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
