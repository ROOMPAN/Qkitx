package com.yanhui.qktx.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import com.just.agentwebX5.AgentWeb;
import com.just.agentwebX5.ChromeClientCallbackManager;
import com.umeng.socialize.UMShareAPI;
import com.yanhui.qktx.R;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.business.BusinessManager;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.models.IsConnBean;
import com.yanhui.qktx.models.TaskShareBean;
import com.yanhui.qktx.network.AndroidWebInterface;
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
import static com.yanhui.qktx.constants.Constant.ISNEWBIETASK;
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
    private TextView tv_clean, tv_title, tv_comment_num;
    private IntentFilter intentfilter;
    private NetBroadcastReceiver mnetReceiver;
    private int isNewbieTask = 0;//判断是否是新手任务页面 切换清空方法的调用

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskId = getIntent().getIntExtra(TASKID, 0);
        commentnum = getIntent().getIntExtra(COMMENTS_NUM, 0);
        articleType = getIntent().getIntExtra(ARTICLETYPE, 0);
        Load_url = getIntent().getStringExtra(WEB_VIEW_LOAD_URL);
        show_buttom = getIntent().getIntExtra(SHOW_WEB_VIEW_BUTTOM, 0);
        show_clear = getIntent().getIntExtra(SHOW_WEB_VIEW_CLEAR, 0);
        isNewbieTask = getIntent().getIntExtra(ISNEWBIETASK, 0);
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
        getArticleIsConn(taskId);
    }

    @Override
    public void bindData() {
        super.bindData();
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearlayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
//                .setWebViewClient(mWebViewClient)
                .createAgentWeb()//
                .ready()
                .go(addToken(Load_url));//http://wxn.qq.com/cmsid/NEW2017090402705503
        agentWeb.getJsInterfaceHolder().addJavaObject("qktxforandroid", new AndroidWebInterface(agentWeb, WebViewActivity.this));

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
        public void onReceivedTitle(com.tencent.smtt.sdk.WebView view, String title) {
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

    //键盘返回
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
                startActivity(new Intent(this, CommentActivity.class).putExtra(TASKID, taskId).putExtra(ISCONN, isconn).putExtra(ARTICLETYPE, articleType));
                break;
            case R.id.webview_et_relayout:
                //编辑评论,
                webview_et_news_send_mess_linner.setVisibility(View.VISIBLE);
                showSoftInputFromWindow(this, et_news_messgae, true);
                break;
            case R.id.webview_et_news_collection:
                if (isconn != 1) {
                    HttpClient.getInstance().getAddConnection(taskId, articleType, new NetworkSubscriber<BaseEntity>(this) {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                ToastUtils.showToast(data.mes);
                                iscollection = true;
                                mIv_collection.setImageResource(R.drawable.icon_news_detail_star_selected);
                                isconn = 1;
                            } else if (data.isNotResult()) {
                                startActivity(new Intent(WebViewActivity.this, LoginActivity.class));
                            }
                        }
                    });
                } else {
                    HttpClient.getInstance().getDeleteConnection(taskId, new NetworkSubscriber<BaseEntity>(this) {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                ToastUtils.showToast(data.mes);
                                mIv_collection.setImageResource(R.drawable.icon_news_detail_star_normal);
                                iscollection = false;
                                isconn = 0;
                            } else if (data.isNotResult()) {
                                startActivity(new Intent(WebViewActivity.this, LoginActivity.class));
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
                HttpClient.getInstance().getTaskShareInfo(taskId, new NetworkSubscriber<TaskShareBean>(this) {
                    @Override
                    public void onNext(TaskShareBean data) {
                        super.onNext(data);
                        if (data.isOKResult()) {
                            RewritePopwindow mPopwindow = new RewritePopwindow(WebViewActivity.this, data.getData().getShareTitle(), data.getData().getShareDesc(), data.getData().getShareImg(), data.getData().getShareUrl());
                            mPopwindow.show(view);
                        } else {
                            ToastUtils.showToast(data.mes);
                        }
                    }
                });
                break;
            case R.id.webview_et_news_more:
                //更多
                TextSizePopwindow TextSizePopwindow = new TextSizePopwindow(this, agentWeb);
                TextSizePopwindow.show(view);
                break;
            case R.id.activity_webview_topbar_left_back_img:
                finish();
                break;
            case R.id.activity_webview_topbar_right_clean:
                //清空数据 <--刷新页面-->
                if (isNewbieTask == 1) {
                    //消息页面
                    agentWeb.getJsEntraceAccess().quickCallJs("clearMes()");
                } else if (isNewbieTask == 2) {
                    //我的评论页面
                    agentWeb.getJsEntraceAccess().quickCallJs("clearMyComment()");
                }
                break;
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
                agentWeb.getJsEntraceAccess().quickCallJs("playVideo(" + 0 + ")");
                Toast.makeText(getApplicationContext(), "网络不可用请检测网络", Toast.LENGTH_SHORT).show();
                break;
            case EventConstants.EVENT_NETWORK_WIFI:
                agentWeb.getJsEntraceAccess().quickCallJs("playVideo(" + 1 + ")");
                //Toast.makeText(getApplicationContext(), "WIFI已连接", Toast.LENGTH_SHORT).show();
                break;
            case EventConstants.EVENT_NETWORK_MOBILE:
                agentWeb.getJsEntraceAccess().quickCallJs("playVideo(" + 0 + ")");
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
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();
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
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
        unregisterReceiver(mnetReceiver);

    }


    private String addToken(String url) {
        if (!TextUtils.isEmpty(url)) {
//            拼接 token+随机数
            String userToken = BusinessManager.getInstance().getUserToken();
            if (!TextUtils.isEmpty(userToken)) {
                if (url.contains("?")) {
                    url += ("&token=" + userToken + "&os=1&r=" + Math.random());
                } else {
                    url += ("?token=" + userToken + "&os=1&r=" + Math.random());
                }
            } else {
                if (url.contains("?")) {
                    url += ("&token=" + MobileUtils.getIMEI() + "&os=1&r=" + Math.random());
                } else {
                    url += ("?token=" + MobileUtils.getIMEI() + "&os=1&r=" + Math.random());
                }
            }
            return url;
        }

        return "";

    }

    public void getArticleIsConn(int taskId) {
        if (taskId != 0) {
            HttpClient.getInstance().getArticleInfo(taskId, new NetworkSubscriber<IsConnBean>(this) {
                @Override
                public void onNext(IsConnBean data) {
                    super.onNext(data);
                    if (data.isOKResult()) {
                        tv_comment_num.setText(data.getData().getComments() + "");
                        if (data.getData().getIsConn() == 1) {
                            isconn = 1;
                            mIv_collection.setImageResource(R.drawable.icon_news_detail_star_selected);
                        } else {
                            isconn = 0;
                            mIv_collection.setImageResource(R.drawable.icon_news_detail_star_normal);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限申请成功
                ToastUtils.showToast("获取联系人的权限申请成功");
            } else {
                ToastUtils.showToast("获取联系人的权限申请失败");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
