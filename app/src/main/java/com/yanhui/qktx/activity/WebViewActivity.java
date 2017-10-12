package com.yanhui.qktx.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import android.widget.TextView;
import android.widget.Toast;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.umeng.socialize.UMShareAPI;
import com.yanhui.qktx.R;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.business.BusinessManager;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.IsConnBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.receiver.NetBroadcastReceiver;
import com.yanhui.qktx.utils.MobileUtils;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.view.DialogView;
import com.yanhui.qktx.view.RewritePopwindow;
import com.yanhui.qktx.view.TextSizePopwindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.yanhui.qktx.constants.Constant.ARTICLETYPE;
import static com.yanhui.qktx.constants.Constant.COMMENTS_NUM;
import static com.yanhui.qktx.constants.Constant.ISCONN;
import static com.yanhui.qktx.constants.Constant.SHARE_CONTEXT;
import static com.yanhui.qktx.constants.Constant.SHARE_IMG_URL;
import static com.yanhui.qktx.constants.Constant.SHARE_TITLE;
import static com.yanhui.qktx.constants.Constant.SHARE_URL;
import static com.yanhui.qktx.constants.Constant.SHOW_BUTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_CLEAR;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_CLEAR;
import static com.yanhui.qktx.constants.Constant.TASKID;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/9/4.
 * webview页面
 */

public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    private AgentWeb agentWeb;
    private LinearLayout mLinearlayout;
    private RelativeLayout rela_datails, rela_collection, rela_share, rela_more, rela_et_mess;
    private boolean iscollection = true;
    private ImageView mIv_collection, mIv_left_back;
    private LinearLayout webview_et_news_send_mess_linner;
    private EditText et_news_messgae;//编辑评论
    private Button bt_send_message;
    private RelativeLayout web_view_buttom_rela;
    private String Load_url;
    private int show_buttom, articleType, taskId, isconn, commentnum, show_clear;
    private String shareurl, sharecontext, sharetitle, shareimgurl;
    private TextView tv_clean, tv_title, tv_comment_num;
    private IntentFilter intentfilter;
    private NetBroadcastReceiver mnetReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskId = getIntent().getIntExtra(TASKID, 0);
        commentnum = getIntent().getIntExtra(COMMENTS_NUM, 0);
        articleType = getIntent().getIntExtra(ARTICLETYPE, 0);
        Load_url = getIntent().getStringExtra(WEB_VIEW_LOAD_URL);
        show_buttom = getIntent().getIntExtra(SHOW_WEB_VIEW_BUTTOM, 0);
        show_clear = getIntent().getIntExtra(SHOW_WEB_VIEW_CLEAR, 0);
        shareurl = getIntent().getStringExtra(SHARE_URL);
        sharetitle = getIntent().getStringExtra(SHARE_TITLE);
        sharecontext = getIntent().getStringExtra(SHARE_CONTEXT);
        shareimgurl = getIntent().getStringExtra(SHARE_IMG_URL);
        setContentView(R.layout.activity_webview);
        setGoneTopBar();
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
        tv_title = (TextView) findViewById(R.id.activity_webview_title_text);
        tv_clean = (TextView) findViewById(R.id.activity_webview_topbar_right_clean);
        mIv_left_back = (ImageView) findViewById(R.id.activity_webview_topbar_left_back_img);
        webview_et_news_send_mess_linner = (LinearLayout) findViewById(R.id.webview_et_news_send_mess_linner);
        web_view_buttom_rela = (RelativeLayout) findViewById(R.id.web_view_buttom_rela);
        tv_comment_num = (TextView) findViewById(R.id.web_view_comment_num);
        tv_comment_num.setText(commentnum + "");
        intentfilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        mnetReceiver = new NetBroadcastReceiver();
        registerReceiver(mnetReceiver, intentfilter);
        if (show_buttom == SHOW_BUTOM) {
            web_view_buttom_rela.setVisibility(View.VISIBLE);
        } else {
            web_view_buttom_rela.setVisibility(View.GONE);
        }
        if (show_clear == SHOW_CLEAR) {
            tv_clean.setVisibility(View.VISIBLE);
        } else {
            tv_clean.setVisibility(View.GONE);
        }
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
                .go(addToken(Load_url));//http://wxn.qq.com/cmsid/NEW2017090402705503
        agentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(agentWeb, this));
        getArticleIsConn(taskId);

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
        mIv_left_back.setOnClickListener(this);
        tv_clean.setOnClickListener(this);
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (!StringUtils.isEmpty(title))
                tv_title.setText(title + "");
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
        if (keyCode == KeyEvent.KEYCODE_BACK && webview_et_news_send_mess_linner.getVisibility() == View.VISIBLE) {
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
                startActivity(new Intent(this, CommentActivity.class).putExtra(TASKID, taskId).putExtra(SHARE_TITLE, sharetitle).putExtra(SHARE_CONTEXT, sharecontext).putExtra(SHARE_URL, shareurl).putExtra(SHARE_IMG_URL, shareimgurl).putExtra(ISCONN, isconn).putExtra(ARTICLETYPE, articleType));
                break;
            case R.id.webview_et_relayout:
                //编辑评论,
                webview_et_news_send_mess_linner.setVisibility(View.VISIBLE);
                showSoftInputFromWindow(this, et_news_messgae, true);
                break;
            case R.id.webview_et_news_collection:
                if (isconn != 1) {
                    mIv_collection.setImageResource(R.drawable.icon_news_detail_star_selected);
                    iscollection = true;
                    HttpClient.getInstance().getAddConnection(taskId, articleType, new NetworkSubscriber<BaseEntity>(this) {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                ToastUtils.showToast(data.mes);
                                isconn = 1;
                            }
                        }
                    });
                } else {
                    mIv_collection.setImageResource(R.drawable.icon_news_detail_star_normal);
                    iscollection = false;
                    HttpClient.getInstance().getDeleteConnection(taskId, new NetworkSubscriber<BaseEntity>(this) {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                ToastUtils.showToast(data.mes);
                                isconn = 0;
                            }
                        }
                    });
                }
                //收藏
                break;
            case R.id.webview_bt_news_message_send:
                //发送评论信息
                webview_et_news_send_mess_linner.setVisibility(View.GONE);
                HttpClient.getInstance().getAddComment(taskId, et_news_messgae.getText().toString(), new NetworkSubscriber<BaseEntity>(this) {
                    @Override
                    public void onNext(BaseEntity data) {
                        super.onNext(data);
                        if (data.isOKResult()) {
                            ToastUtils.showToast(data.mes);
                            et_news_messgae.setText("");
                            showSoftInputFromWindow(WebViewActivity.this, et_news_messgae, false);
                        }
                    }
                });

                break;
            case R.id.webview_et_news_share:
                //分享
                RewritePopwindow mPopwindow = new RewritePopwindow(this, sharetitle, sharecontext, shareimgurl, shareurl);
                mPopwindow.show(view);
                break;
            case R.id.webview_et_news_more:
                //更多
                TextSizePopwindow TextSizePopwindow = new TextSizePopwindow(this, agentWeb);
                TextSizePopwindow.show(view);
                //agentWeb.getJsEntraceAccess().quickCallJs("aaaa");
                break;
            case R.id.activity_webview_topbar_left_back_img:
                finish();
                break;
            case R.id.activity_webview_topbar_right_clean:
                //清空数据 <--刷新页面-->
                WebView mWebView = agentWeb.getWebCreator().get();
                mWebView.reload();
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
                    Toast.makeText(context.getApplicationContext(), "js成功" + msg, Toast.LENGTH_LONG).show();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChange(BusEvent busEvent) {
        switch (busEvent.what) {
            case EventConstants.EVEN_NETWORK_NONE:
                Toast.makeText(getApplicationContext(), "网络不可用请检测网络", Toast.LENGTH_SHORT).show();
                break;
            case EventConstants.EVENT_NETWORK_WIFI:
                //Toast.makeText(getApplicationContext(), "WIFI已连接", Toast.LENGTH_SHORT).show();
                break;
            case EventConstants.EVENT_NETWORK_MOBILE:
                Toast.makeText(getApplicationContext(), "您当前的网络为4G", Toast.LENGTH_SHORT).show();
                new DialogView(this).show();
                break;
            case EventConstants.EVEN_ISCONN:
                if (busEvent.arg1 == 1) {
                    mIv_collection.setImageResource(R.drawable.icon_news_detail_star_selected);
                    isconn = 1;
                } else {
                    mIv_collection.setImageResource(R.drawable.icon_news_detail_star_normal);
                    isconn = 0;
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        agentWeb.getWebLifeCycle().onPause();
    }

    @Override
    protected void onResume() {
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerEventBus(WebViewActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterEventBus(WebViewActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mnetReceiver);
        agentWeb.getWebLifeCycle().onDestroy();

    }

    private String addToken(String url) {
        if (!TextUtils.isEmpty(url)) {
            String userToken = BusinessManager.getInstance().getUserToken();
            if (!TextUtils.isEmpty(userToken)) {
                if (url.contains("?")) {
                    url += ("&token=" + userToken + "&os=1");
                } else {
                    url += ("?token=" + userToken + "&os=1");
                }
            } else {
                if (url.contains("?")) {
                    url += ("&token=" + MobileUtils.getIMEI() + "&os=1");
                } else {
                    url += ("?token=" + MobileUtils.getIMEI() + "&os=1");
                }
            }
            return url;
        }

        return "";

    }

    public void getArticleIsConn(int taskId) {
        HttpClient.getInstance().getArticleIsConn(taskId, new NetworkSubscriber<IsConnBean>(this) {
            @Override
            public void onNext(IsConnBean data) {
                super.onNext(data);
                if (data.getData().getIsConn() == 1) {
                    isconn = 1;
                    mIv_collection.setImageResource(R.drawable.icon_news_detail_star_selected);
                } else {
                    isconn = 0;
                    mIv_collection.setImageResource(R.drawable.icon_news_detail_star_normal);
                }
            }

        });
    }
}
