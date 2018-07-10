package cn.syxg.recycleviewdemo;

/**
 * Created by Administrator on 2018/7/9.
 */

public class Book {

    String title;
    String name;
    String author;
    String image;

    public Book(String title, String name, String author, String image) {
        this.title = title;
        this.name = name;
        this.author = author;
        this.image = image;
    }

    public Book(String name, String author, String image) {
        this.name = name;
        this.author = author;
        this.image = image;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public String getAuter() {
        return author;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuter(String author) {
        this.author = author;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
