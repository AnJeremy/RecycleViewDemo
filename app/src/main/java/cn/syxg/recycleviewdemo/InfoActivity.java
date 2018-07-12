package cn.syxg.recycleviewdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private InfoAdapter adapter;
    private TextView bt_comment;
    List<MultiItemBean> multiItemBeans = new ArrayList<>();
    BottomSheetDialog dialog;
    MultiItemBean comMultiItemBean;

    SwipeRefreshLayout mRefreshLayout;

    private CommentExpandableListView mExpandableListView;
    CommentExpandAdapter mExpandAdapter;
    List<CommentDetailBean> commentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        bt_comment = (TextView) findViewById(R.id.detail_page_do_comment);
        bt_comment.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.info_view);
        mRefreshLayout = findViewById(R.id.info_refresh);
        multiItemBeans.add(new MultiItemBean(MultiItemBean.WEBVIEWTITLE));

        multiItemBeans.add(new MultiItemBean(MultiItemBean.WEBVIEW));
        multiItemBeans.add(new MultiItemBean(MultiItemBean.INFO));
        multiItemBeans.add(new MultiItemBean(MultiItemBean.RELATED));
        //MultiItemBean comMultiItemBean = new MultiItemBean(MultiItemBean.COMMENT);
        commentList = generateTestData();
        //comMultiItemBean.setCommentList(commentList);
        //multiItemBeans.add(comMultiItemBean);


        adapter = new InfoAdapter(multiItemBeans);

       /* View mTop = getLayoutInflater().inflate(R.layout.item_info_webview, (ViewGroup) mRecyclerView.getParent(), false);

        WebView mWebView = mTop.findViewById(R.id.wv);
        //mWebView.setLayoutParams(lp);
        loadUrl(mWebView,"https://activity.xianjincard.com/register?source_tag=H5-waik2&style=3&download=1&company=1");

        adapter.addHeaderView(mTop);*/
        //可能会出现变白情况
        View mFoot = getLayoutInflater().inflate(R.layout.item_info_comment, (ViewGroup) mRecyclerView.getParent(), false);


        mExpandableListView = mFoot.findViewById(R.id.detail_page_lv_comment);
        mExpandableListView.setGroupIndicator(null);
        mExpandAdapter = new CommentExpandAdapter(this, commentList);
        //initExpandableListView(this,mExpandableListView,mExpandAdapter,commentList);
        mExpandableListView.setAdapter(mExpandAdapter);
        for(int i = 0; i<commentList.size(); i++){
            mExpandableListView.expandGroup(i);
        }

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                // Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }

                Toast.makeText(InfoActivity.this,"点击了回复"+commentList.get(groupPosition).getNickName(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {


                if(commentList.get(groupPosition).getReplyList().size() > 2){
                    Toast.makeText(InfoActivity.this,"点击查看更多",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(InfoActivity.this,"点击了回复"+commentList.get(groupPosition).getReplyList().get(childPosition).getNickName(),Toast.LENGTH_SHORT).show();
                    showReplyDialog(groupPosition);

                }

                return false;
            }
        });
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                Toast.makeText(InfoActivity.this,"点击了展开"+groupPosition,Toast.LENGTH_SHORT).show();
                //toast("展开第"+groupPosition+"个分组");
            }
        });

        adapter.addFooterView(mFoot);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);


        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //adapter.setEnableLoadMore(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                       // commentList.add();

                        mExpandAdapter.addLoadTheCommentData(new CommentDetailBean("小花","今天天气不错","刚刚"));
                        adapter.loadMoreComplete();
                        //adapter.setEnableLoadMore(false);
                    }
                }, 2000);

            }
        });


        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //adapter.setEnableLoadMore(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(InfoActivity.this,"刷新成功！",Toast.LENGTH_SHORT).show();

                      /*  // commentList.add();

                        mExpandAdapter.addLoadTheCommentData(new CommentDetailBean("小花","今天天气不错","刚刚"));
                        adapter.loadMoreComplete();
                        adapter.setEnableLoadMore(false);*/

                      mRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

            }
        });
    }



    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final Context mContext, ExpandableListView expandableListView,  CommentExpandAdapter adapter, final List<CommentDetailBean> commentList){
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(mContext, commentList);
        expandableListView.setAdapter(adapter);
        for(int i = 0; i<commentList.size(); i++){
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
               // Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }

                Toast.makeText(mContext,"点击了回复"+commentList.get(groupPosition).getNickName(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(mContext,"点击了回复"+commentList.get(groupPosition).getReplyList().get(childPosition).getNickName(),Toast.LENGTH_SHORT).show();
                //showReplyDialog(mContext,groupPosition,adapter,expandableListView,commentList);
                return false;
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");
            }
        });
    }


    private void loadUrl(WebView mWebView,String url){

        //Map<String, String > map = new HashMap<String, String>() ;
        //map.put( "x-uuid" , "131415926" ) ;
        WebSettings webSettings = mWebView.getSettings();


        // webSettings.setUserAgentString(webSettings.getUserAgentString()+ " uuid/"+getAndroidId(MainActivity.this));

        webSettings.setJavaScriptEnabled(true);

        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        //webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        //webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        //Logger.d(url);
        mWebView.getSettings().setDomStorageEnabled(true);//DOM Storage
        //加载网络请求的html

        mWebView.loadUrl(url);

        //mWebView.postUrl(url,postData.getBytes());
        mWebView.setWebViewClient(new WebViewClient(){
          /*  @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;

            }*/




            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
               /* if(mProgressDialog != null){

                    mProgressDialog.show();
                }*/

            }



            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

               /* //解决webview 微信支付的问题
                if(url == null) return false;
                try {
                    if (url.startsWith("weixin://") || url.startsWith("alipays://") ||
                            url.startsWith("mailto://") || url.startsWith("tel://")
                        //其他自定义的scheme
                            ) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }*/

                //处理http和https开头的url
                //view.loadUrl(url);
                return false;
            }

          /*  @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG,"onPageStarted");
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.d(TAG,"onLoadResource");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG,"onPageFinished");
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);

                Log.d(TAG,"onReceivedHttpError");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                Log.d(TAG,"onReceivedSslError");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.d(TAG,"onReceivedError");
            }*/


        });


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.detail_page_do_comment){

            showCommentDialog();
        }
    }


    /**
     *
     * func:弹出评论框
     */
    private void showCommentDialog(){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
       /* *//**
         * 解决bsd显示不全的情况
         *//*
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());*/

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    CommentDetailBean detailBean = new CommentDetailBean("小明", commentContent,"刚刚");
                    mExpandAdapter.addTheCommentData(detailBean);//添加数据
                    //commentList.add(detailBean);
                    //adapter.updateComment(detailBean);
                    //comMultiItemBean.setCommentList(commentList);
                    //adapter.addData(MultiItemBean.COMMENT,comMultiItemBean);
                    Toast.makeText(InfoActivity.this,"评论成功",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(InfoActivity.this,"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }



    /**
     *
     * func:弹出回复框
     */
    public void showReplyDialog(final int position){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentList.get(position).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(replyContent)){

                    dialog.dismiss();
                    ReplyDetailBean detailBean = new ReplyDetailBean("小红",replyContent);
                    mExpandAdapter.addTheReplyData(detailBean, position);
                    //expandableListView.expandGroup(position);
                    Toast.makeText(InfoActivity.this,"回复成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(InfoActivity.this,"回复内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }



    /**
     * func:生成测试数据
     * @return 评论数据
     */
    private List<CommentDetailBean> generateTestData(){
        Gson gson = new Gson();
        CommentBean commentBean = gson.fromJson(testJson, CommentBean.class);
        List<CommentDetailBean> commentList = commentBean.getData().getList();
        return commentList;
    }

    private String testJson = "{\n" +
            "\t\"code\": 1000,\n" +
            "\t\"message\": \"查看评论成功\",\n" +
            "\t\"data\": {\n" +
            "\t\t\"total\": 3,\n" +
            "\t\t\"list\": [{\n" +
            "\t\t\t\t\"id\": 42,\n" +
            "\t\t\t\t\"nickName\": \"程序猿\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"时间是一切财富中最宝贵的财富。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"三分钟前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\t\"commentId\": \"42\",\n" +
            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"一个小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 41,\n" +
            "\t\t\t\t\"nickName\": \"设计狗\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"这世界要是没有爱情，它在我们心中还会有什么意义！这就如一盏没有亮光的走马灯。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"一天前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\t\"commentId\": \"41\",\n" +
            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"三小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\"nickName\": \"产品喵\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"笨蛋自以为聪明，聪明人才知道自己是笨蛋。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 0,\n" +
            "\t\t\t\t\"createDate\": \"三天前\",\n" +
            "\t\t\t\t\"replyList\": []\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}";
}
