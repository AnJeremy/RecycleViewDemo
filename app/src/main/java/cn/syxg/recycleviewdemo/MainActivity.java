package cn.syxg.recycleviewdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanzhenjie.sofia.Sofia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
            @BindView(R.id.hot_recycler)
    public RecyclerView mHotView;

    private List<HotBean> hotBeans;

    private View mTop;

    //private Banner mBanner;
    HotViewAdapter homeAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    WebView mWebView;
    List<Integer> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        // 这里不使用Sofia设置StatusBar颜色，用自己在布局中的，用Sofia设置会覆盖在DrawerLayout之上。
        Sofia.with(this)
                .navigationBarBackground(ContextCompat.getDrawable(this, R.color.colorNavigation))
                .invasionStatusBar()
                .statusBarBackground(Color.TRANSPARENT);
       // setAnyBarAlpha(0);


        Random random = new Random();
        int i1 = random.nextInt(20);
        Log.d("hhh",i1+"");

        //mHotView = findViewById(R.id.hot_recycler);
        mRefreshLayout = findViewById(R.id.hot_refresh);
        mHotView.setLayoutManager(new LinearLayoutManager(this));
        //添加Android自带的分割线
        mHotView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        hotBeans = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            hotBeans.add(new HotBean());
        }


         list=new ArrayList<>();
       /* list.add("http://img5.imgtn.bdimg.com/it/u=3287998705,4210783580&fm=27&gp=0.jpg");
        list.add("http://img5.imgtn.bdimg.com/it/u=3287998705,4210783580&fm=27&gp=0.jpg");
        list.add("http://img5.imgtn.bdimg.com/it/u=3287998705,4210783580&fm=27&gp=0.jpg");*/
        list.add(R.mipmap.ss);
        list.add(R.mipmap.ss);
        list.add(R.mipmap.ss);

        final List<String> title = new ArrayList<>();

        title.add("哈哈哈的");
        title.add("阿大使达");
        title.add("啊啊司法所");


        homeAdapter = new HotViewAdapter(R.layout.item_hot, hotBeans,list,title);
        homeAdapter.openLoadAnimation();
        mTop = getLayoutInflater().inflate(R.layout.item_top_hot, (ViewGroup) mHotView.getParent(), false);
       /* mTop = View.inflate(this,R.layout.item_top_hot,null);
        mBanner = mTop.findViewById(R.id.hot_banner);
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);


        mBanner.setImages(list);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setBannerTitles(title);
        mBanner.setDelayTime(5000);

        mBanner.start();
        mBanner.startAutoPlay();
*/


        View mTitle = getLayoutInflater().inflate(R.layout.item_title, (ViewGroup) mHotView.getParent(), false);
        homeAdapter.addHeaderView(mTop,0);

        View mWeb = getLayoutInflater().inflate(R.layout.item_webview, (ViewGroup) mHotView.getParent(), false);
        mWebView = mWeb.findViewById(R.id.wv);

        loadUrl();

        homeAdapter.addHeaderView(mWeb,1);

/*
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

                Toast.makeText(MainActivity.this,title.get(position)+position,Toast.LENGTH_SHORT).show();

            }
        });*/

       /* homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(HomeActivity.this, ACTIVITY[position]);
                startActivity(intent);
            }
        });*/
        homeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN );
        homeAdapter.isFirstOnly(false);

        mHotView.setAdapter(homeAdapter);


/*
        homeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(hotBeans != null && hotBeans.size()<=12){
                            hotBeans.add(new HotBean());
                            homeAdapter.loadMoreComplete();
                        }else {
                            homeAdapter.loadMoreEnd();
                            //homeAdapter.setEnableLoadMore(false);
                        }

                    }
                }, 2000);

            }
        },mHotView);*/

        final List<String> images = new ArrayList<>();

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHotView.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        hotBeans.add(new HotBean());
                        images.clear();
                        images.add("https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=f560166a52df8db1a32e7a643922dddb/0ff41bd5ad6eddc4f8daa30935dbb6fd52663306.jpg");
                        images.add("https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=f560166a52df8db1a32e7a643922dddb/0ff41bd5ad6eddc4f8daa30935dbb6fd52663306.jpg");
                        images.add("https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=f560166a52df8db1a32e7a643922dddb/0ff41bd5ad6eddc4f8daa30935dbb6fd52663306.jpg");
                        //images.add(R.mipmap.aa);
                        //images.add(R.mipmap.aa);

                        //mHotView.notify();
                        //mHotView.notifySubtreeAccessibilityStateChanged(mTop,mHotView,1);
                        //mHotView.notifyAll();
                        //mRefreshLayout.setRefreshing();

                        //homeAdapter.notifyDataSetChanged();

                        homeAdapter.setBanner(images,title);
                        mRefreshLayout.setRefreshing(false);

                    }
                }, 1500);
            }


        });

    }


    private void loadUrl(){

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        //webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        //webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        //Logger.d(url);
        mWebView.getSettings().setDomStorageEnabled(true);//DOM Storage
        //加载网络请求的html

        mWebView.loadUrl("http://api.51credit.com/app/loan/product/redirect?pid=166&loanId=134&os=3&si=m&t=M_LOAN_ALL");

        //mWebView.postUrl(url,postData.getBytes());
        mWebView.setWebViewClient(new WebViewClient(){
          /*  @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;

            }*/

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                //Log.i(TAG, "onReceivedError: ");
                //showErrorPage();//显示错误页面


            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                //6.0以上执行
                //Log.i(TAG, "onReceivedError: ");
                //showErrorPage();//显示错误页面
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
               /* if(mProgressDialog != null){

                    mProgressDialog.show();
                }*/

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                //解决webview 微信支付的问题
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
                }

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

}
