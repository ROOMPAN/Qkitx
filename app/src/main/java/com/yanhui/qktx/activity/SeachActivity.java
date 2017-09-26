package com.yanhui.qktx.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaychan.uikit.TipView;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.yanhui.qktx.R;
import com.yanhui.qktx.adapter.SeaChArticleAdapter;
import com.yanhui.qktx.adapter.SeachKeyWordAdapter;
import com.yanhui.qktx.adapter.SeachVideoAdapter;
import com.yanhui.qktx.constants.Constant;
import com.yanhui.qktx.models.ArticleListBean;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupanpan on 2017/9/11.
 */

public class SeachActivity extends BaseActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, AdapterView.OnItemClickListener, TextWatcher {
    public static final String KEY_SEARCH_HISTORY_KEYWORD = "key_search_history_keyword";
    private PowerfulRecyclerView rv_view;
    private TipView mTipView;
    private BGARefreshLayout mRefreshLayout;
    private ImageView iv_back;
    private EditText et_seach;
    private TextView tv_seach_go;
    private int seach_type;//搜索类型
    private ListView lv_key_word;
    private TextView tv_close_all;
    private LinearLayout seach_key_word_add_linner, activity_seach_recy_linner;
    private List<String> str_key_word = new ArrayList<>();
    private SeachKeyWordAdapter madapter;
    private List<ArticleListBean.DataBean> mData = new ArrayList<>();
    private SeaChArticleAdapter articleAdapter;
    private SeachVideoAdapter seachVideoAdapter;
    private View recy_empty_view;
    private String search_key_word;
    private int aiticlepageNo = 2, videopageNo = 2;
    private View key_word_null_empty;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private List<String> mHistoryKeywords = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
        seach_type = getIntent().getIntExtra(Constant.SEACH_TYPE, 0);
        Log.e("seach", "" + seach_type);
        setGoneTopBar();
        mPref = getSharedPreferences("test", Activity.MODE_PRIVATE);
        mEditor = mPref.edit();
        initSearchHistory();
    }

    @Override
    public void findViews() {
        super.findViews();
        rv_view = (PowerfulRecyclerView) findViewById(R.id.activity_seach_rv_news);
        mTipView = (TipView) findViewById(R.id.fragment_seach_tip_view);
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.activity_seach_refresh_layout);
        iv_back = (ImageView) findViewById(R.id.activity_seach_topbar_left_back_img);
        et_seach = (EditText) findViewById(R.id.activity_seach_edit);
        tv_seach_go = (TextView) findViewById(R.id.activity_seach_seach_go);
        seach_key_word_add_linner = (LinearLayout) findViewById(R.id.activity_seach_add_linner);
        activity_seach_recy_linner = (LinearLayout) findViewById(R.id.activity_seach_recy_linner);
        lv_key_word = (ListView) findViewById(R.id.activity_seach_key_word_lv);
        recy_empty_view = findViewById(R.id.activity_search_empty_layout);
        tv_close_all = (TextView) findViewById(R.id.activity_seach_close);
        key_word_null_empty = findViewById(R.id.key_word_null_empty);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        mRefreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.pull_refresh_bg);//背景色
        refreshViewHolder.setPullDownRefreshText(UIUtils.getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));//刷新中的提示文字
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(rv_view);
    }

    @Override
    public void bindData() {
        super.bindData();

    }

    @Override
    public void bindListener() {
        super.bindListener();
        iv_back.setOnClickListener(this);
        tv_seach_go.setOnClickListener(this);
        tv_close_all.setOnClickListener(this);
        lv_key_word.setOnItemClickListener(this);
        et_seach.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_seach_topbar_left_back_img:
                finish();
                break;
            case R.id.activity_seach_seach_go:
                if (!StringUtils.isEmpty(et_seach.getText().toString())) {
                    search_key_word = et_seach.getText().toString();
                    commintkeyWord(search_key_word);
                    //setSeachKey();//加载搜索历史记录
                    if (seach_type == Constant.SEACH_AIRTS) {
                        getSeachData(seach_type, search_key_word, 1, false);
                        seach_key_word_add_linner.setVisibility(View.GONE);
                        activity_seach_recy_linner.setVisibility(View.VISIBLE);
                        articleAdapter = new SeaChArticleAdapter(this);
                        rv_view.setAdapter(articleAdapter);
                        rv_view.setEmptyView(recy_empty_view);
                    } else if (seach_type == Constant.SEACH_VIDEO) {
                        getSeachData(seach_type, search_key_word, 1, false);
                        seach_key_word_add_linner.setVisibility(View.GONE);
                        activity_seach_recy_linner.setVisibility(View.VISIBLE);
                        seachVideoAdapter = new SeachVideoAdapter(this);
                        rv_view.setAdapter(seachVideoAdapter);
                        rv_view.setEmptyView(recy_empty_view);
                    }
                }
                break;
            case R.id.activity_seach_close:
                cleanHistory();
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //下拉刷新
        getSeachData(seach_type, search_key_word, 1, false);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //上拉加载
        if (seach_type == Constant.SEACH_AIRTS) {
            getSeachData(seach_type, search_key_word, aiticlepageNo, true);
        } else {
            getSeachData(seach_type, search_key_word, videopageNo, true);
        }
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        ToastUtils.showToast(str_key_word.get(position));
    }

    public void getSeachData(int seach_type, String search_context, int pagerNo, boolean isloadmore) {
        HttpClient.getInstance().getSearchTask(seach_type, search_context, pagerNo, Constant.PAGER_SIZE, new NetworkSubscriber<ArticleListBean>(this) {
            @Override
            public void onNext(ArticleListBean data) {
                super.onNext(data);
                if (data.isOKResult()) {
                    if (seach_type == Constant.SEACH_AIRTS) {
                        if (isloadmore) {
                            articleAdapter.addData(data.getData());
                            aiticlepageNo++;
                            mRefreshLayout.endLoadingMore();
                        } else {
                            aiticlepageNo = 2;
                            articleAdapter.setData(data.getData());
                            mRefreshLayout.endRefreshing();
                        }
                    } else {
                        if (isloadmore) {
                            seachVideoAdapter.addData(data.getData());
                            videopageNo++;
                            mRefreshLayout.endLoadingMore();
                        } else {
                            videopageNo = 2;
                            seachVideoAdapter.setData(data.getData());
                            mRefreshLayout.endRefreshing();
                        }
                    }
                }
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (StringUtils.isEmpty(et_seach.getText().toString())) {
            initSearchHistory();
        }
    }

    public void commintkeyWord(String et_seach_key_word) {
        String oldText = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
        if (!TextUtils.isEmpty(et_seach_key_word) && !oldText.contains(et_seach_key_word)) {
            if (mHistoryKeywords.size() > 4) {
                Toast.makeText(this, "最多保存5条历史", Toast.LENGTH_SHORT).show();
                return;
            }
            mEditor.putString(KEY_SEARCH_HISTORY_KEYWORD, et_seach_key_word + "," + oldText);
            mEditor.commit();
            mHistoryKeywords.add(0, et_seach_key_word);
        }
    }

    public void initSearchHistory() {
        String history = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
        if (!TextUtils.isEmpty(history)) {
            List<String> list = new ArrayList<String>();
            for (Object o : history.split(",")) {
                list.add((String) o);
            }
            mHistoryKeywords = list;
        }

        seach_key_word_add_linner.setVisibility(View.VISIBLE);
        activity_seach_recy_linner.setVisibility(View.GONE);
        madapter = new SeachKeyWordAdapter(this, seach_key_word_add_linner, activity_seach_recy_linner);
        lv_key_word.setAdapter(madapter);
        madapter.setAdd(mHistoryKeywords);
        lv_key_word.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                et_seach.setText(mHistoryKeywords.get(i));
                seach_key_word_add_linner.setVisibility(View.GONE);
            }
        });
        madapter.notifyDataSetChanged();
    }

    public void cleanHistory() {
        mEditor.clear();
        mHistoryKeywords.clear();
        mEditor.commit();
        madapter.notifyDataSetChanged();
        seach_key_word_add_linner.setVisibility(View.GONE);
        activity_seach_recy_linner.setVisibility(View.VISIBLE);
        key_word_null_empty.setVisibility(View.VISIBLE);
        Toast.makeText(this, "清除搜索历史记录成功", Toast.LENGTH_SHORT).show();
    }
}
