package com.yanhui.qktx.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.umeng.socialize.UMShareAPI;
import com.yanhui.qktx.R;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.view.RewritePopwindow;

/**
 * Created by liupanpan on 2017/9/4.
 * webview页面
 */

public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    private AgentWeb agentWeb;
    private LinearLayout mLinearlayout;
    private RelativeLayout rela_datails, rela_collection, rela_share, rela_more, rela_et_mess;
    private boolean iscollection = true;
    private ImageView mIv_collection;
    private LinearLayout webview_et_news_send_mess_linner;
    private EditText et_news_messgae;//编辑评论
    private Button bt_send_message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
    }

    @Override
    public void findViews() {
        super.findViews();
        mLinearlayout = (LinearLayout) findViewById(R.id.activity_webview_linner);
        rela_et_mess = (RelativeLayout) findViewById(R.id.webview_et_relayout);
        rela_datails = (RelativeLayout) findViewById(R.id.webview_et_news_detail);
        rela_collection = (RelativeLayout) findViewById(R.id.webview_et_news_collection);
        rela_share = (RelativeLayout) findViewById(R.id.webview_et_news_share);
        rela_more = (RelativeLayout) findViewById(R.id.webview_et_news_more);
        mIv_collection = (ImageView) findViewById(R.id.webview_image_collection);
        et_news_messgae = (EditText) findViewById(R.id.webview_et_news_message);
        bt_send_message = (Button) findViewById(R.id.webview_bt_news_message_send);
        webview_et_news_send_mess_linner = (LinearLayout) findViewById(R.id.webview_et_news_send_mess_linner);
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
        if (iscollection) {
            mIv_collection.setImageResource(R.drawable.icon_news_detail_star_selected);
        } else {
            mIv_collection.setImageResource(R.drawable.icon_news_detail_star_normal);
        }

    }

    @Override
    public void bindListener() {
        super.bindListener();
        rela_datails.setOnClickListener(this);
        rela_collection.setOnClickListener(this);
        rela_more.setOnClickListener(this);
        rela_share.setOnClickListener(this);
        et_news_messgae.setOnClickListener(this);
        rela_et_mess.setOnClickListener(this);
        bt_send_message.setOnClickListener(this);
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
        if (webview_et_news_send_mess_linner.getVisibility() == View.VISIBLE) {
            webview_et_news_send_mess_linner.setVisibility(View.GONE);
            return false;
        }

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
            case R.id.webview_et_relayout:
                //编辑评论,
                webview_et_news_send_mess_linner.setVisibility(View.VISIBLE);
                showSoftInputFromWindow(this, et_news_messgae, true);
                break;
            case R.id.webview_et_news_collection:
                if (!iscollection) {
                    mIv_collection.setImageResource(R.drawable.icon_news_detail_star_selected);
                    iscollection = true;
                    ToastUtils.showToast("已收藏");
                } else {
                    mIv_collection.setImageResource(R.drawable.icon_news_detail_star_normal);
                    iscollection = false;
                    ToastUtils.showToast("取消收藏");
                }
                //收藏
                break;
            case R.id.webview_bt_news_message_send:
                //发送评论信息
                webview_et_news_send_mess_linner.setVisibility(View.GONE);
                et_news_messgae.setText("");
                showSoftInputFromWindow(this, et_news_messgae, false);
                break;
            case R.id.webview_et_news_share:
                //分享
                RewritePopwindow mPopwindow = new RewritePopwindow(this);
                mPopwindow.show(view);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText, boolean open) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (open) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            //关闭软键盘
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }

    }

}
