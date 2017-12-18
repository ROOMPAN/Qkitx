package com.yanhui.qktx.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yanhui.qktx.R;
import com.yanhui.qktx.business.BusEvent;
import com.yanhui.qktx.business.BusinessManager;
import com.yanhui.qktx.constants.EventConstants;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.DataCleanManagerUtils;
import com.yanhui.qktx.utils.SharedPreferencesMgr;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;
import com.yanhui.qktx.utils.UIUtils;
import com.yanhui.statusbar_lib.flyn.Eyes;

import org.greenrobot.eventbus.EventBus;

import static com.yanhui.qktx.constants.Constant.ABOUT;
import static com.yanhui.qktx.constants.Constant.PROTOCOL;
import static com.yanhui.qktx.constants.Constant.WEB_VIEW_LOAD_URL;


/**
 * 设置页面
 * Created by liupanpan on 2017/8/30.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private View include_change_pwd, include_change_userinfo, include_feedback, include_agreement, include_check_update, include_about_ars;
    private TextView tv_change_pwd, tv_change_info, tv_feedback, tv_agreement, tv_update, tv_about;
    private TextView tv_clean_context;
    private ImageView iv_change_pwd, iv_change_info, iv_clean, iv_agreement, iv_update, iv_about;
    private RelativeLayout layout_setting_clean, logout_rela;
    private String protocol_url, about_url;//协议链接,关于链接.

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        protocol_url = getIntent().getStringExtra(PROTOCOL);
        about_url = getIntent().getStringExtra(ABOUT);
        setContentView(R.layout.activity_setting);
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.status_bar));//设置状态栏颜色
        setTitleText("设置");
        setBackLeft(R.drawable.icon_back_white);
        setTopBarColor(R.color.status_bar);
    }

    @Override
    public void findViews() {
        super.findViews();
        include_change_pwd = findViewById(R.id.include_setting_change_pwd);
        include_change_userinfo = findViewById(R.id.include_setting_change_userinfo);
        include_feedback = findViewById(R.id.include_setting_feedback);
        include_agreement = findViewById(R.id.include_setting_agreement);
        include_check_update = findViewById(R.id.include_setting_check_updata);
        include_about_ars = findViewById(R.id.include_setting_about_ars);
        layout_setting_clean = (RelativeLayout) findViewById(R.id.layout_setting_clean);
        logout_rela = (RelativeLayout) findViewById(R.id.activity_setting_logout_relay);
        tv_change_pwd = include_change_pwd.findViewById(R.id.txt_person_page_title);
        tv_change_info = include_change_userinfo.findViewById(R.id.txt_person_page_title);
        tv_feedback = include_feedback.findViewById(R.id.txt_person_page_title);
        tv_agreement = include_agreement.findViewById(R.id.txt_person_page_title);
        tv_update = include_check_update.findViewById(R.id.txt_person_page_title);
        tv_about = include_about_ars.findViewById(R.id.txt_person_page_title);
        tv_clean_context = (TextView) findViewById(R.id.txt_setting_title_clean_nmb);
        iv_change_pwd = include_change_pwd.findViewById(R.id.img_key_persom_setting_left);
        iv_change_info = include_change_userinfo.findViewById(R.id.img_key_persom_setting_left);
        iv_clean = (ImageView) findViewById(R.id.img_clean_setting_left);
        iv_agreement = include_agreement.findViewById(R.id.img_key_persom_setting_left);
        iv_update = include_check_update.findViewById(R.id.img_key_persom_setting_left);
        iv_about = include_about_ars.findViewById(R.id.img_key_persom_setting_left);


    }

    @Override
    public void bindData() {
        super.bindData();
        tv_change_pwd.setText(R.string.change_pwd);
        tv_change_info.setText(R.string.change_info);
        tv_feedback.setText(R.string.feedback);
        tv_agreement.setText(R.string.agreement);
        tv_update.setText(R.string.update);
        tv_about.setText(R.string.about);
        iv_change_pwd.setBackgroundResource(R.drawable.setting_change_pwd);
        iv_change_info.setBackgroundResource(R.drawable.icon_setting_userinfo);
        iv_clean.setBackgroundResource(R.drawable.icon_setting_clean);
        iv_agreement.setBackgroundResource(R.drawable.icon_user_setting_agreement);
        iv_about.setBackgroundResource(R.drawable.icon_setting_about);
        try {
            tv_clean_context.setText(DataCleanManagerUtils.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void bindListener() {
        super.bindListener();
        include_change_pwd.setOnClickListener(this);
        include_change_userinfo.setOnClickListener(this);
        include_feedback.setOnClickListener(this);
        include_agreement.setOnClickListener(this);
        include_check_update.setOnClickListener(this);
        include_about_ars.setOnClickListener(this);
        layout_setting_clean.setOnClickListener(this);
        logout_rela.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.include_setting_change_pwd:
                startActivity(new Intent(this, RegesterPwdActivity.class));
                break;
            case R.id.include_setting_change_userinfo:
                startActivity(new Intent(this, UserInforActivity.class));
                break;
            case R.id.include_setting_feedback:
                ToastUtils.showToast("反馈");
                break;
            case R.id.include_setting_agreement:
                if (!StringUtils.isEmpty(protocol_url)) {
                    startActivity(new Intent(this, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, protocol_url));
                }
                break;
            case R.id.include_setting_check_updata:
                ToastUtils.showToast("检查更新");
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                break;
            case R.id.include_setting_about_ars:
                if (!StringUtils.isEmpty(about_url)) {
                    startActivity(new Intent(this, WebViewActivity.class).putExtra(WEB_VIEW_LOAD_URL, about_url));
                }
                break;
            case R.id.layout_setting_clean:
                showNormalDialog();
                break;
            case R.id.activity_setting_logout_relay:
                HttpClient.getInstance().getLogOut(new NetworkSubscriber<BaseEntity>(this) {
                    @Override
                    public void onNext(BaseEntity data) {
                        super.onNext(data);
                        if (data.isOKResult()) {
                            BusinessManager.getInstance().logout();
                            SharedPreferencesMgr.removeKey("token");
                            SharedPreferencesMgr.removeKey("userid");
                            SharedPreferencesMgr.removeKey("username");
                            EventBus.getDefault().post(new BusEvent(EventConstants.EVENT_SWITCH_TO_HOME));//切换到首页
                            ToastUtils.showToast(data.mes);
                            finish();
                        } else {
                            ToastUtils.showToast(data.mes);
                        }
                    }
                });

                break;
        }

    }

    private void showNormalDialog() {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        normalDialog.setTitle("通知!!");
        normalDialog.setMessage("是否清空缓存?");
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataCleanManagerUtils.clearAllCache(SettingActivity.this);
                tv_clean_context.setText("0KB");
            }
        });
        normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // 显示
        normalDialog.show();
    }
}
