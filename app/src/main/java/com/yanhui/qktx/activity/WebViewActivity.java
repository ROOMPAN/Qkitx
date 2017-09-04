package com.yanhui.qktx.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.yanhui.qktx.R;
import com.yanhui.qktx.utils.StringUtils;

/**
 * Created by liupanpan on 2017/9/4.
 * webview页面
 */

public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    private AgentWeb agentWeb;
    private LinearLayout mLinearlayout;
    private RelativeLayout rela_datails, rela_collection, rela_share, rela_more;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
    }

    @Override
    public void findViews() {
        super.findViews();
        mLinearlayout = (LinearLayout) findViewById(R.id.activity_webview_linner);
        rela_datails = (RelativeLayout) findViewById(R.id.webview_et_news_detail);
        rela_collection = (RelativeLayout) findViewById(R.id.webview_et_news_collection);
        rela_share = (RelativeLayout) findViewById(R.id.webview_et_news_share);
        rela_more = (RelativeLayout) findViewById(R.id.webview_et_news_more);
    }

    @Override
    public void bindData() {
        super.bindData();
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearlayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .setWebViewClient(mWebViewClient)
                .createAgentWeb()//
                .ready()
                .go("http://wxn.qq.com/cmsid/NEW2017090402705503");
        agentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(agentWeb, this));
    }

    @Override
    public void bindListener() {
        super.bindListener();
        rela_datails.setOnClickListener(this);
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (!StringUtils.isEmpty(title))
                setTitleText(title);
        }
    };
    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            //Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (agentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.webview_et_news_detail:
                //评论
                break;
            case R.id.webview_et_news_collection:
                //收藏
                break;
            case R.id.webview_et_news_share:
                //分享
                break;
            case R.id.webview_et_news_more:
                //更多
                break;
        }
    }

    public class AndroidInterface {
        private Handler deliver = new Handler(Looper.getMainLooper());
        private AgentWeb agent;
        private Context context;

        public AndroidInterface(AgentWeb agent, Context context) {
            this.agent = agent;
            this.context = context;
        }

        @JavascriptInterface
        public void callAndroid(final String msg) {
            deliver.post(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(context.getApplicationContext(), "" + msg, Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}
