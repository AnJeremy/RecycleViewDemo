package cn.syxg.recycleviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GridleActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<GridleBean> gridleBeans;

    private GridleAdapter adapter;

    List<ElePriceTableModel> mData;
    ElePriceStaggeredAdapter mStaggeredAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridle);

        mRecyclerView = findViewById(R.id.gridle_recycle);

        /*mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        *//*gridleBeans = new ArrayList<>();

        GridleBean gridleBean = new GridleBean("dd",1);
        gridleBeans.add(gridleBean);

        for (int i = 0; i <9 ; i++) {

            gridleBeans.add(new GridleBean());

        }

        gridleBeans.add(new GridleBean("",2));

        for (int i = 0; i <7 ; i++) {

            gridleBeans.add(new GridleBean());

        }

        adapter = new GridleAdapter(this,gridleBeans);
        //adapter.setColumn(4);
        //mRecyclerView.addItemDecoration(new MyItemDecoration());
       *//**//* mRecyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL_LIST));*//**//*
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);*//*
        mData = new ArrayList<>();
        mData.add(new ElePriceTableModel("1~6月", 4));
        //mData.add(new ElePriceTableModel("0~12", 2));
        mData.add(new ElePriceTableModel("0~10", 1));
        mData.add(new ElePriceTableModel("0.92", 1, true));
        mData.add(new ElePriceTableModel("10~100", 1));
        mData.add(new ElePriceTableModel("0.83", 1, true));
        //mData.add(new ElePriceTableModel("12~24", 2));
        mData.add(new ElePriceTableModel("0~10", 1));
        mData.add(new ElePriceTableModel("0.99", 1, true));
        mData.add(new ElePriceTableModel("10~100", 1));
        mData.add(new ElePriceTableModel("1.12", 1, true));
        mData.add(new ElePriceTableModel("7~12月", 4));
        //mData.add(new ElePriceTableModel("0~12", 2));
        mData.add(new ElePriceTableModel("0~10", 1));
        mData.add(new ElePriceTableModel("0.93", 1, true));
        mData.add(new ElePriceTableModel("10~100", 1));
        mData.add(new ElePriceTableModel("0.83", 1, true));
        //mData.add(new ElePriceTableModel("12~24", 2));
        mData.add(new ElePriceTableModel("0~100", 1));
        mData.add(new ElePriceTableModel("1.01", 1, true));
        mData.add(new ElePriceTableModel("10~100", 1));
        mData.add(new ElePriceTableModel("1.17", 1, true));
        mStaggeredAdapter = new ElePriceStaggeredAdapter(this, mData);
        //mTableContent = (RecyclerView) findViewById(R.id.price_table_content);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mStaggeredAdapter.setColumn(3);
        mRecyclerView.setAdapter(mStaggeredAdapter);
        mRecyclerView.setHasFixedSize(true);
*/

        Date mstart = new Date(System.currentTimeMillis()-(2*60*60*1000));
        Date mend = new Date(System.currentTimeMillis());

        Log.d("timeSHow",DateDistance(mstart,mend)+"");

        Toast.makeText(this,DateDistance(mstart,mend)+"",Toast.LENGTH_SHORT).show();
    }


    public static  String DateDistance(Date startDate, Date endDate){
        if(startDate == null ||endDate == null){
            return null;
        }
        long timeLong = endDate.getTime() - startDate.getTime();
        if(timeLong<0){
            timeLong=0;
        }
        if (timeLong<60*1000)
            return timeLong/1000 + "秒前";
        else if (timeLong<60*60*1000){
            timeLong = timeLong/1000 /60;
            return timeLong + "分钟前";
        }
        else if (timeLong<60*60*24*1000){
            timeLong = timeLong/60/60/1000;
            return timeLong+"小时前";
        }
        else if ((timeLong/1000/60/60/24)<7){
            timeLong = timeLong/1000/ 60 / 60 / 24;
            return timeLong + "天前";
        }else{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(startDate);
        }
    }

}
