package cn.syxg.recycleviewdemo;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Administrator on 2018/7/9.
 */

public class BookSection extends SectionEntity<Book>{

    public BookSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public BookSection(Book book) {
        super(book);
    }
}
