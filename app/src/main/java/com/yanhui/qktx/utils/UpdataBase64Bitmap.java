package com.yanhui.qktx.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import com.yanhui.qktx.network.ImageDownLoadCallBack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by liupanpan on 2017/10/30.
 */

public class UpdataBase64Bitmap implements Runnable {
    private String base64str;
    private Context context;
    private ImageDownLoadCallBack callBack;
    private File file = null;
    private File currentFile;
    //声明进度条对话框
    private ProgressDialog pdDialog = null;

    public UpdataBase64Bitmap(Context context, String base64str, ImageDownLoadCallBack callBack) {
        this.base64str = base64str;
        this.callBack = callBack;
        this.context = context;
        ShowProgress();
    }


    @Override
    public void run() {
// 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        byte[] bitmapArray;
        bitmapArray = Base64.decode(base64str, Base64.NO_WRAP);
        bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        if (bitmap != null) {
            // 在这里执行图片保存方法
            saveImageToGallery(context, bitmap);
        }
        if (bitmap != null && currentFile.exists()) {
            callBack.onDownLoadSuccess(currentFile);
            pdDialog.cancel();
        } else {
            callBack.onDownLoadFailed();
            pdDialog.cancel();
        }
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String file = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile());//注意小米手机必须这样获得public绝对路径
        String fileName = "qktxs";
        File appDir = new File(file, fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        fileName = "share_money.jpg";
        currentFile = new File(appDir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    currentFile.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));
    }

    public void ShowProgress() {
        //创建ProgressDialog对象
        pdDialog = new ProgressDialog(context);
        //设置进度条风格，风格为圆形，旋转的
        pdDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置ProgressDialog 标题
        pdDialog.setTitle("加载数据");
        // 设置ProgressDialog 提示信息
        pdDialog.setMessage("正在处理,请稍后……");
        // 设置ProgressDialog 是否可以按退回按键取消
        pdDialog.setCancelable(true);
        pdDialog.show();
    }
// String base="iVBORw0KGgoAAAANSUhEUgAAAB8AAAAcCAMAAACu5JSlAAABPlBMVEUAAACdnZ2cnJycnJybm5ucnJycnJycnJycnJyenp6cnJycnJydnZ2cnJycnJycnJyjo6Ofn5+bm5ucnJycnJz///+dnZ2bm5ubm5uenp6cnJycnJyfn5+bm5uioqKbm5ufn5+bm5ufn5+dnZ2bm5ucnJyenp6fn5+bm5uenp6cnJycnJyqqqqenp6bm5ucnJydnZ2bm5uenp6kpKScnJyenp6goKCqqqqcnJycnJydnZ3///+bm5ubm5ubm5ucnJyzs7OcnJycnJyenp6cnJycnJycnJyenp6bm5ubm5ubm5ucnJycnJy/v7+cnJycnJybm5udnZ2bm5ucnJybm5ucnJycnJympqabm5ucnJycnJybm5ucnJyqqqqcnJycnJyfn5+cnJycnJydnZ2cnJybm5ucnJydnZ22trabm5vfW9uPAAAAaXRSTlMADYHQ8/falR9C4PJl/sFNGRChdoQBU/34Ko63GPoL5CjKNVaXO1Q9Uj829A8y9RIv9hUcSEQjA5rbdQJ43HvWCvygWdUs+ToX61x8bARL5bBGpqq77LkU1+hnRdgGhlUw3cRR4sfnngczRVX8AAABJUlEQVQoz53S6U7CQBQF4CMghSoUF0BRcGvdEAVcARXBDRdU3BUX3D3v/wJO0wQt6STq+XMm+Zo7k5sCQJvL7WFL2r2KD1b8Kh3T0WlxgEEthJZ0+bvJHnO4yt4wnBKJ9vUDLgadGYhxAHBTkzAGGU/Aw5DMMcRhkFLGCEd/62O6Yda4PmHWpD5l92kyOQOkyNk5IB1gJmtzTexrHlgQJda6KGrJ5ssrubyogp5bFRVW1tb/+L5/e5wJqReZhkpDxhslZlFmTOab3AK2d0q7zlzZ476oA2YOqw58dMwTs6uKWGowIk6107Nmzi/Iyyvry9o16QXyUdvveXP7PavOu/syAw+PzWiNwo+7Unx6ZvJFughDzHttyBdZJ98qcobv/ePTWb4AJfJXcQ8bOXMAAAAASUVORK5CYII=";

//    String base = "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";
//    UpdataBase64Bitmap updataImageUtils = new UpdataBase64Bitmap(getActivity(), base, new ImageDownLoadCallBack() {
//        @Override
//        public void onDownLoadSuccess(File file) {
//            Log.d("image_base64", "" + file.getPath());
//        }
//
//        @Override
//        public void onDownLoadSuccess(Bitmap bitmap) {
//
//        }
//
//        @Override
//        public void onDownLoadFailed() {
//
//        }
//    });
//                        new Thread(updataImageUtils).start();
}
