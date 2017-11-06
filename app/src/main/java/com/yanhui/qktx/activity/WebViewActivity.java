package com.yanhui.qktx.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.agentwebX5.AgentWeb;
import com.just.agentwebX5.ChromeClientCallbackManager;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.UMShareAPI;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;
import com.yanhui.qktx.MyApplication;
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
import com.yanhui.qktx.utils.CommonUtil;
import com.yanhui.qktx.utils.MobileUtils;
import com.yanhui.qktx.utils.SharedPreferencesMgr;
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
import static com.yanhui.qktx.constants.Constant.IS_FIRST_OPEN_WEBVIEW;
import static com.yanhui.qktx.constants.Constant.SEEK_POSITION_KEY;
import static com.yanhui.qktx.constants.Constant.SHOW_BUTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_CLEAR;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_BUTTOM;
import static com.yanhui.qktx.constants.Constant.SHOW_WEB_VIEW_CLEAR;
import static com.yanhui.qktx.constants.Constant.TASKID;
import static com.yanhui.qktx.constants.Constant.VIDEO_URL;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;

/**
 * Created by liupanpan on 2017/9/4.
 * webview页面
 */

public class WebViewActivity extends BaseActivity implements View.OnClickListener, UniversalVideoView.VideoViewCallback {
    private AgentWeb agentWeb;
    private LinearLayout mLinearlayout;
    private RelativeLayout rela_datails, rela_collection, rela_share, rela_more, rela_et_mess;
    private boolean iscollection = true;
    private ImageView mIv_collection, mIv_left_back, mIv_video_left_back;
    private LinearLayout webview_et_news_send_mess_linner;
    private EditText et_news_messgae;//编辑评论
    private Button bt_send_message;
    private RelativeLayout web_view_buttom_rela;
    private String Load_url, video_url;
    private int show_buttom, articleType, taskId, isconn, commentnum, show_clear;
    private TextView tv_clean, tv_title, tv_comment_num;
    private IntentFilter intentfilter;
    private NetBroadcastReceiver mnetReceiver;
    private int isNewbieTask = 0;//判断是否是新手任务页面 切换清空方法的调用
    private View top_bar_artile, top_bar_video;
    private TextView top_bar_close_all, top_bar_video_close_all;
    //视频播放
    private UniversalVideoView mVideoView;
    private UniversalMediaController mMediaController;
    private View mVideoLayout;
    private TextView mStart;
    private boolean isFullscreen, is_fiest_open_web = true;
    private int mSeekPosition;
    private int cachedHeight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        is_fiest_open_web = getIntent().getBooleanExtra(IS_FIRST_OPEN_WEBVIEW, false);
        taskId = getIntent().getIntExtra(TASKID, 0);
        commentnum = getIntent().getIntExtra(COMMENTS_NUM, 0);
        articleType = getIntent().getIntExtra(ARTICLETYPE, 0);
        Load_url = getIntent().getStringExtra(WEB_VIEW_LOAD_URL);
        show_buttom = getIntent().getIntExtra(SHOW_WEB_VIEW_BUTTOM, 0);
        show_clear = getIntent().getIntExtra(SHOW_WEB_VIEW_CLEAR, 0);
        isNewbieTask = getIntent().getIntExtra(ISNEWBIETASK, 0);
        video_url = getIntent().getStringExtra(VIDEO_URL);
        setContentView(R.layout.activity_webview);
        setGoneTopBar();
        //是否是从列表打开
        if (is_fiest_open_web) {
            top_bar_close_all.setVisibility(View.VISIBLE);
            top_bar_video_close_all.setVisibility(View.VISIBLE);
        } else {
            MyApplication.clearActivity();
        }
        MyApplication.addActivity(this);
        if (articleType == 2) {
            top_bar_video.setVisibility(View.VISIBLE);
            top_bar_artile.setVisibility(View.GONE);
            mVideoLayout.setVisibility(View.VISIBLE);
            //判断视频 URL 不为null 网络状态可用
            if (!StringUtils.isEmpty(video_url) && CommonUtil.isWifi(this) != EventConstants.EVEN_NETWORK_NONE) {
                setVideoAreaSize();
                if (mSeekPosition > 0) {
                    mVideoView.seekTo(mSeekPosition);
                }
                if (CommonUtil.isWifi(this) == EventConstants.EVENT_NETWORK_WIFI) {
                    mVideoView.start();
                }
            }
        } else {
            top_bar_artile.setVisibility(View.VISIBLE);
            top_bar_video.setVisibility(View.GONE);
            mVideoLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void findViews() {
        super.findViews();
        top_bar_artile = findViewById(R.id.activity_webview_aritle_top_bar);
        top_bar_video = findViewById(R.id.activity_webview_video_top_bar);
        top_bar_close_all = (TextView) findViewById(R.id.activity_webview_topbar_left_close_all);
        top_bar_video_close_all = (TextView) findViewById(R.id.activity_webview_video_topbar_left_close_all);
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
        mIv_video_left_back = (ImageView) findViewById(R.id.activity_webview_video_topbar_left_back_img);
        //视频控件
        mVideoLayout = findViewById(R.id.video_layout);
        mVideoView = (UniversalVideoView) findViewById(R.id.videoView);
        mMediaController = (UniversalMediaController) findViewById(R.id.media_controller);
        mVideoView.setMediaController(mMediaController);

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
                .setWebViewClient(mWebViewClient)
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
        mVideoView.setVideoViewCallback(this);
        mIv_video_left_back.setOnClickListener(this);
        top_bar_close_all.setOnClickListener(this);
        top_bar_video_close_all.setOnClickListener(this);
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
        public void onLoadResource(WebView webView, String s) {
            super.onLoadResource(webView, s);
//            ToastUtils.showToast("onLoadResource");
        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            //页面开始加载
            showLoadingView();
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            //页面加载完成
            hideLoadingView();
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
                if (Integer.parseInt(tv_comment_num.getText().toString()) != 0) {
                    startActivity(new Intent(this, CommentActivity.class).putExtra(TASKID, taskId).putExtra(ISCONN, isconn).putExtra(ARTICLETYPE, articleType));
                } else {
                    ToastUtils.showToast("暂无评论");
                }
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
                if (BusinessManager.getInstance().isLogin()) {
                    webview_et_news_send_mess_linner.setVisibility(View.GONE);
                    HttpClient.getInstance().getAddComment(taskId, et_news_messgae.getText().toString(), SharedPreferencesMgr.getString("address", ""), new NetworkSubscriber<BaseEntity>(this) {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                ToastUtils.showToast(data.mes);
                                et_news_messgae.setText("");
                                showSoftInputFromWindow(WebViewActivity.this, et_news_messgae, false);
                            } else if (data.isNotResult()) {
                                startActivity(new Intent(WebViewActivity.this, LoginActivity.class));
                            }
                        }
                    });
                } else {
                    startActivity(new Intent(WebViewActivity.this, LoginActivity.class));
                }
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
            case R.id.activity_webview_video_topbar_left_back_img:
                //视频 返回按钮
                finish();
                break;
            case R.id.activity_webview_topbar_right_clean:
                //清空数据 <--刷新页面-->
                if (isNewbieTask == 1) {
                    //消息页面
                    agentWeb.getJsEntraceAccess().quickCallJs("clearMes");
                } else if (isNewbieTask == 2) {
                    //我的评论页面
                    agentWeb.getJsEntraceAccess().quickCallJs("clearMyComment");
                }
                break;
            case R.id.activity_webview_topbar_left_close_all:
                MyApplication.clearActivity();
                break;
            case R.id.activity_webview_video_topbar_left_close_all:
                MyApplication.clearActivity();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //友盟分享回调
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
                if (articleType == 2) {
                    if (mVideoView != null && mVideoView.isPlaying()) {
                        mSeekPosition = mVideoView.getCurrentPosition();
                        mVideoView.pause();
                    }
                }
                break;
            case EventConstants.EVENT_NETWORK_WIFI:
                //Toast.makeText(getApplicationContext(), "WIFI已连接", Toast.LENGTH_SHORT).show();
                break;
            case EventConstants.EVENT_NETWORK_MOBILE:
                Toast.makeText(getApplicationContext(), "您当前的网络为4G", Toast.LENGTH_SHORT).show();
                if (articleType == 2) {
                    if (mVideoView != null && mVideoView.isPlaying()) {
                        mSeekPosition = mVideoView.getCurrentPosition();
                        mVideoView.pause();
                    }
                    new DialogView(this).show();
                }
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
        if (mVideoView != null && mVideoView.isPlaying()) {
            mSeekPosition = mVideoView.getCurrentPosition();
            mVideoView.pause();
        }
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

    /**
     * 评论个数,是否收藏接口
     *
     * @param taskId 文章 id
     */
    public void getArticleIsConn(int taskId) {
        if (taskId != 0) {
            HttpClient.getInstance().getArticleInfo(taskId, new NetworkSubscriber<IsConnBean>(this) {
                @Override
                public void onNext(IsConnBean data) {
                    super.onNext(data);
                    if (data.isOKResult()) {
                        if (data.getData().getComments() != 0) {
                            tv_comment_num.setVisibility(View.VISIBLE);
                            tv_comment_num.setText(data.getData().getComments() + "");
                        } else {
                            tv_comment_num.setVisibility(View.INVISIBLE);
                        }
                        if (data.getData().getIsConn() == 1) {
                            isconn = 1;
                            mIv_collection.setImageResource(R.drawable.icon_news_detail_star_selected);
                        } else {
                            isconn = 0;
                            mIv_collection.setImageResource(R.drawable.icon_news_detail_star_normal);
                        }
                    }

                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    tv_comment_num.setVisibility(View.INVISIBLE);
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

    /**
     * 视频控件置视频区域大小
     */
    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                cachedHeight = (int) (width * 405f / 720f);
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = cachedHeight;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mVideoView.setVideoPath(video_url);
                mVideoView.requestFocus();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Log.d(TAG, "onSaveInstanceState Position=" + mVideoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
//        Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
    }

    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);
            web_view_buttom_rela.setVisibility(View.GONE);
            mIv_video_left_back.setVisibility(View.GONE);
        } else {
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = this.cachedHeight;
            mVideoLayout.setLayoutParams(layoutParams);
            web_view_buttom_rela.setVisibility(View.VISIBLE);
            mIv_video_left_back.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBackPressed() {
        if (this.isFullscreen) {
            mVideoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }
}
