package com.yanhui.qktx.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yanhui.qktx.R;
import com.yanhui.qktx.constants.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by liupanpan on 2017/10/23.
 * APP 更新 弹出框
 */

public class AppUpdateActivity extends BasePopupActivity {
    private ProgressBar mProgressBar;
    private TextView mTv_updateContent;
    private Button mBtn_cancel;
    private Button mBtn_confirm;
    //= "http://oqwq2ee7m.bkt.clouddn.com/qktx_release_1_1.0_201710231235.apk"
    private String mUpdateUrl;
    private String mUpdateContent;
    private AppUpdateAsyncTask mAppUpdateAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_update);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
    }

    @Override
    public void findViews() {
        super.findViews();
        mProgressBar = findViewById(R.id.app_update_progress);
        mTv_updateContent = findViewById(R.id.app_update_content);
        mBtn_cancel = findViewById(R.id.app_update_btn_cancel);
        mBtn_confirm = findViewById(R.id.app_update_btn_confirm);
    }

    @Override
    public void bindListener() {
        super.bindListener();
        mBtn_cancel.setOnClickListener(v -> finish());
        mBtn_confirm.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mUpdateUrl)) {
                mAppUpdateAsyncTask = new AppUpdateAsyncTask();
                mAppUpdateAsyncTask.execute(mUpdateUrl);
            } else {
                finish();
            }
        });
    }

    @Override
    public void bindData() {
        super.bindData();
        mUpdateUrl = getIntent().getStringExtra(Constant.UPDATA_URL);
        mUpdateContent = getIntent().getStringExtra(Constant.UPDATA_CONTEXT);
        mTv_updateContent.setText(mUpdateContent);
        if (TextUtils.isEmpty(mUpdateUrl)) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAppUpdateAsyncTask != null && !mAppUpdateAsyncTask.isCancelled()) {
            mAppUpdateAsyncTask.cancel(true);
        }
    }


    class AppUpdateAsyncTask extends AsyncTask<String, Integer, Integer> {

        private static final String TEMP_APK_NAME = "qktx_temp.apk";
        long mContentLength;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mBtn_cancel.setEnabled(false);
            mBtn_confirm.setEnabled(false);
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                downloadApkFromURL(params[0]);
                return 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return -1;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 0) {
                startInstall();
            } else {
                showDownloadFailed();
            }
        }

        private void showDownloadFailed() {
            mBtn_confirm.setEnabled(true);
            mBtn_cancel.setEnabled(true);
            mBtn_confirm.setText("重试");
        }

        private void startInstall() {
            String cachePath = getExternalCacheDir().getAbsolutePath()
                    + File.separator + TEMP_APK_NAME;
            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                Uri apkUri = FileProvider.getUriForFile(AppUpdateActivity.this, "com.yanhui.qktx.fileprovider", new File(cachePath));
                //添加这一句表示对目标应用临时授权该Uri所代表的文件,在AndroidManifest中的android:authorities值
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.parse("file://" + cachePath),
                        "application/vnd.android.package-archive");
            }
            startActivityForResult(intent, 1);
            finish();
        }

        private void downloadApkFromURL(String url) throws IOException {
            URLConnection connection = (new URL(url)).openConnection();
            if (connection == null) {
                throw new IOException();
            }

            mContentLength = connection.getContentLength();
            int len, length = 0;
            byte[] buf = new byte[1024];
            InputStream is = connection.getInputStream();
            File file = new File(getExternalCacheDir(), TEMP_APK_NAME);
            if (file.exists()) {
                file.delete();
            }
            // if (!file.exists()) {
            file.createNewFile();
            // }

            Runtime.getRuntime().exec("chmod 777 " + file.getAbsolutePath());
            OutputStream os = new FileOutputStream(file);
            try {
                while ((len = is.read(buf, 0, buf.length)) > 0 && !isCancelled()) {
                    os.write(buf, 0, len);
                    length += len;
                    publishProgress(length);
                }
                os.flush();
            } finally {
                is.close();
                os.close();
            }
        }

        private void setProgress(int size) {
            float percent = 0;
            if (mContentLength > 0) {
                percent = ((float) size) / mContentLength;
            }
            if (mProgressBar.getVisibility() == View.GONE) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            mProgressBar.setProgress((int) (percent * 100));
        }

    }

    /**
     * 禁止点击手机返回按钮关闭页面,防止正在下载中关闭页面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}