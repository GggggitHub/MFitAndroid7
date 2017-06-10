package com.libraryforfileprovider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

/**
 * Created by ${张英杰} on 2017/6/10.
 */

public class FileProvider7 {
    public static void installApk(Context context,File file){
        if (context == null || (!file.exists())) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >=24) {
            setIntentDataAndType(context,intent,"application/vnd.android.package-archive",file,false);
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置启动模式NEW_TASK 或者不设置都OK
        }
        context.startActivity(intent);
    }

    public static void setIntentDataAndType(Context context,
                                            Intent intent,
                                            String type,
                                            File file,
                                            boolean writeAble){
        if (Build.VERSION.SDK_INT>=24){
            intent.setDataAndType(getUriForFile(context,file),type);
            if (writeAble){
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }else {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }else {
            intent.setDataAndType(Uri.fromFile(file),type);
        }
    }
    public static Uri getUriForFile(Context context,File file){
        Uri fileUri=null;
        if (Build.VERSION.SDK_INT>=24){
            fileUri=getUriForFile24(context,file);
        }else {
            fileUri=Uri.fromFile(file);
        }
        return fileUri;
    }

    private static Uri getUriForFile24(Context context, File file) {
        return android.support.v4.content.FileProvider.getUriForFile(context,
                context.getPackageName()+".fileprovider",file);
    }
}
