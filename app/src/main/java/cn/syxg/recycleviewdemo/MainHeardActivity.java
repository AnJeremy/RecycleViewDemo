package cn.syxg.recycleviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainHeardActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    List<BookSection> books = new ArrayList<>();

    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_heard);

        mRecyclerView = findViewById(R.id.heard_recycler);

        books.add(new BookSection(true,"java"));
        for (int i = 0; i < 5; i++) {

            //books.add(new Book("java","hello","一天"));

           books.add(new BookSection(new Book("java","hello","一天")));

        }
        books.add(new BookSection(true,"php"));
        for (int i = 0; i < 5; i++) {

            //books.add(new Book("php","world","世界"));
            books.add(new BookSection(new Book("php","1、新增赚钱联盟任务，轻松赚取更多U币。\n 2、新增四级好友邀请，好友更多奖励更厚。\n 3、修复已知BUG。\n","世界")));
        }

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,1));

        adapter = new BookAdapter(R.layout.item_heard_content,R.layout.item_heard_title,books);
        mRecyclerView.setAdapter(adapter);

    }
}
