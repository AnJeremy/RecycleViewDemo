package cn.syxg.recycleviewdemo;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private InfoAdapter adapter;
    private TextView bt_comment;
    List<MultiItemBean> multiItemBeans = new ArrayList<>();
    BottomSheetDialog dialog;

    private List<CommentDetailBean> commentsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        bt_comment = (TextView) findViewById(R.id.detail_page_do_comment);
        bt_comment.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.info_view);
        multiItemBeans.add(new MultiItemBean(MultiItemBean.WEBVIEWTITLE));

        multiItemBeans.add(new MultiItemBean(MultiItemBean.WEBVIEW));
        multiItemBeans.add(new MultiItemBean(MultiItemBean.INFO));
        multiItemBeans.add(new MultiItemBean(MultiItemBean.RELATED));
        multiItemBeans.add(new MultiItemBean(MultiItemBean.COMMENT));

        adapter = new InfoAdapter(multiItemBeans);

       /* View mTop = getLayoutInflater().inflate(R.layout.item_info_webview, (ViewGroup) mRecyclerView.getParent(), false);

        WebView mWebView = mTop.findViewById(R.id.wv);
        //mWebView.setLayoutParams(lp);
        loadUrl(mWebView,"https://activity.xianjincard.com/register?source_tag=H5-waik2&style=3&download=1&company=1");

        adapter.addHeaderView(mTop);*/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
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
                    //adapter.addTheCommentData(detailBean);//添加数据
                    adapter.updateComment(detailBean);
                    //adapter.addData(MultiItemBean.COMMENT,detailBean);
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
        commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(replyContent)){

                    dialog.dismiss();
                    ReplyDetailBean detailBean = new ReplyDetailBean("小红",replyContent);
                    //adapter.addTheReplyData(detailBean, position);
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
}
