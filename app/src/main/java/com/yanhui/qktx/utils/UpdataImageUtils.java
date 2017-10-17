package com.yanhui.qktx.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.yanhui.qktx.network.ImageDownLoadCallBack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 下载图片到本地
 * Created by liupanpan on 2017/10/16.
 */

public class UpdataImageUtils implements Runnable {
    private String url;
    private Context context;
    private ImageDownLoadCallBack callBack;
    private File currentFile;

    public UpdataImageUtils(Context context, String url, ImageDownLoadCallBack callBack) {
        this.url = url;
        this.callBack = callBack;
        this.context = context;
    }

    @Override
    public void run() {
        File file = null;
        Bitmap bitmap = null;
        try {
//            file = Glide.with(context)
//                    .load(url)
//                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                    .get();
            bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            if (bitmap != null) {
                // 在这里执行图片保存方法
                saveImageToGallery(context, bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            if (file != null) {
//                callBack.onDownLoadSuccess(file);
//            } else {
//                callBack.onDownLoadFailed();
//            }
            if (bitmap != null && currentFile.exists()) {
                callBack.onDownLoadSuccess(currentFile);
            } else {
                callBack.onDownLoadFailed();
            }
        }
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String file = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile());//注意小米手机必须这样获得public绝对路径
        String fileName = "qktx";
        File appDir = new File(file, fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        fileName = "share.jpg";
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
}
//    //往SD卡写入文件的方法
//    public void savaFileToSD(String filename, byte[] bytes) throws Exception {
//        //如果手机已插入sd卡,且app具有读写sd卡的权限
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/budejie";
//            File dir1 = new File(filePath);
//            if (!dir1.exists()) {
//                dir1.mkdirs();
//            }
//            filename = filePath + "/" + filename;
//            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
//            FileOutputStream output = new FileOutputStream(filename);
//            output.write(bytes);
//            //将bytes写入到输出流中
//            output.close();
//            //关闭输出流
//            Toast.makeText(context, "图片已成功保存到" + filePath, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
//        }
//    }
//}