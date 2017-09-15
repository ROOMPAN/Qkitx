package com.yanhui.qktx.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yanhui.qktx.R;
import com.yanhui.qktx.models.BaseEntity;
import com.yanhui.qktx.network.HttpClient;
import com.yanhui.qktx.network.ImageLoad;
import com.yanhui.qktx.network.NetworkSubscriber;
import com.yanhui.qktx.utils.StringUtils;
import com.yanhui.qktx.utils.ToastUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 用户信息修改
 * Created by liupanpan on 2017/8/30.
 */

public class UserInforActivity extends BaseActivity implements View.OnClickListener, ImageLoader {
    private CircleImageView img_user_photo;
    private Button bt_dismiss, bt_camera, bt_album;
    private int IMAGE_PICKER = 1;
    private int REQUEST_CODE_SELECT = 2;
    private PopupWindow popupWindow;
    private RelativeLayout bt_submit;
    private EditText et_name, et_age;
    private String handurl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);
        setTitleText("完善资料");

    }

    @Override
    public void findViews() {
        super.findViews();
        img_user_photo = (CircleImageView) findViewById(R.id.activity_userinfo_photo);
        bt_submit = (RelativeLayout) findViewById(R.id.activity_userinfo_modify_relay);
        et_name = (EditText) findViewById(R.id.activity_userinfo_et_name);
        et_age = (EditText) findViewById(R.id.activity_userinfo_et_age);

    }

    @Override
    public void bindListener() {
        super.bindListener();
        img_user_photo.setOnClickListener(this);
        bt_submit.setOnClickListener(this);

    }

    @Override
    public void bindData() {
        super.bindData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_userinfo_photo:
                hideSoftInputFromWindow();
                showPopwindow();
                break;
            case R.id.btn_take_photo:
                Intent intent = new Intent(this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.btn_pick_photo:
                Intent pickintent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(pickintent, IMAGE_PICKER);
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.activity_userinfo_modify_relay:
                //提交
                if (!StringUtils.isEmpty(et_name.getText().toString()) && !StringUtils.isEmpty(et_age.getText().toString()) && !StringUtils.isEmpty(handurl)) {
                    HttpClient.getInstance().getUpdateInfo(et_name.getText().toString(), handurl, et_age.getText().toString(), new NetworkSubscriber<BaseEntity>(this) {
                        @Override
                        public void onNext(BaseEntity data) {
                            super.onNext(data);
                            if (data.isOKResult()) {
                                ToastUtils.showToast(data.mes);
                                finish();
                            } else {
                                ToastUtils.showToast(data.mes);
                            }
                        }
                    });
                } else {
                    ToastUtils.showToast("信息不完善");
                }
                break;
        }

    }

    private void showPopwindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_person_check_photo, null);
        bt_dismiss = contentView.findViewById(R.id.btn_cancel);
        bt_camera = contentView.findViewById(R.id.btn_take_photo);
        bt_album = contentView.findViewById(R.id.btn_pick_photo);
        bt_album.setOnClickListener(this);
        bt_camera.setOnClickListener(this);
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popupWindow.setBackgroundDrawable(dw);
        //点击外部消失
        popupWindow.setFocusable(true);// 取得焦点
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        bt_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);//
                Log.e("imagepath", images.get(0).path + "");
                handurl = images.get(0).path;
                ImageLoad.into(this, images.get(0).path, img_user_photo);
            } else if (requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);//
                ToastUtils.showToast(images.get(0).path + "");
                handurl = images.get(0).path;
                ImageLoad.into(this, images.get(0).path, img_user_photo);
                Log.e("imagepath", images.get(0).path + "");
            }
        }
    }

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        ImageLoad.into(activity, path, imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        ImageLoad.into(activity, path, imageView);
    }

    @Override
    public void clearMemoryCache() {

    }

    //关闭软键盘
    public void hideSoftInputFromWindow() {
        final View v = getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
