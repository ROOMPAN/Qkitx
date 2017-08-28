package com.yanhui.qktx.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yanhui.qktx.R;
import com.yanhui.qktx.onkeyshare.ShareContext;
import com.yanhui.qktx.utils.CommonUtil;
import com.yanhui.qktx.utils.UIUtils;
import com.yanhui.statusbar_lib.flyn.Eyes;

/**
 * Created by liupanpan on 2017/8/25.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_mobile;
    private ImageButton image_login_clean;
    private EditText et_pwd;
    private Button bt_show_pwd;
    private Button activity_login;
    private RelativeLayout bt_login_regester;
    private boolean eyeOpen = false;
    public static final int ACTIVITY_GET_IMAGE = 0;

    private static String shareTitle = "有问题吗？真的有问题吗？";
    private static String shareContent = "请点击查看答案";
    private static String shareImageUrl = "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg";
    private static String sharejumpUrl = "https://www.baidu.com/index.php?tn=monline_3_dg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.status_color_red));
        setTitleText("登录");
        setGoneRight();
    }

    @Override
    public void findViews() {
        super.findViews();
        et_mobile = (EditText) findViewById(R.id.activity_login_et_mobile);
        image_login_clean = (ImageButton) findViewById(R.id.image_login_clean);
        et_pwd = (EditText) findViewById(R.id.activity_login_et_pwd);
        bt_show_pwd = (Button) findViewById(R.id.ctivity_login_show_pwd);
        activity_login = (Button) findViewById(R.id.activity_login);
        bt_login_regester = (RelativeLayout) findViewById(R.id.activity_login_regester_relay);

    }

    @Override
    public void bindListener() {
        super.bindListener();
        image_login_clean.setOnClickListener(this);
        bt_show_pwd.setOnClickListener(this);
        activity_login.setOnClickListener(this);
        bt_login_regester.setOnClickListener(this);
        img_topbar_back_left.setOnClickListener(this);
    }

    @Override
    public void bindData() {
        super.bindData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            清楚手机号
            case R.id.image_login_clean:
                et_mobile.setText("");
                break;
            //显示密码
            case R.id.ctivity_login_show_pwd:
                if (eyeOpen) {
                    et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    bt_show_pwd.setText(R.string.gone_pwd);
                    eyeOpen = false;
                } else {
                    et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    bt_show_pwd.setText(R.string.show_pwd);
                    eyeOpen = true;
                }
                break;
            //登录
            case R.id.activity_login:
//                submit();

                //调用相册
                Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                getImage.addCategory(Intent.CATEGORY_OPENABLE);
                getImage.setType("image/*");
                startActivityForResult(getImage, ACTIVITY_GET_IMAGE);
                break;
            //注册
            case R.id.activity_login_regester_relay:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.activity_base_topbar_left_back_img:
                finish();
                break;
        }
    }

    private void submit() {
        String mobile = et_mobile.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        if (CommonUtil.setTextEmpty(mobile)) {
            Toast.makeText(this, "手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (CommonUtil.setTextEmpty(pwd)) {
            Toast.makeText(this, "密码", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data.getData() != null) {
            Uri originalUri = data.getData(); // 获得图片的uri
//          ShareContext.setShareWxCircleFriendsbyBitmap("", originalUri);
            ShareContext.setShareWxFriends(this, shareTitle, shareContent, shareImageUrl, sharejumpUrl);
//            ShareContext.setShareWxCirclefriends(this, shareTitle, shareContent, shareImageUrl, sharejumpUrl);
        }
    }
}
