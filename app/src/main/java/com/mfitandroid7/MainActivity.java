package com.mfitandroid7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.libraryforfileprovider.FileProvider7;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void installApk(View v){
        // 需要自己修改安装包路径
        String localApkFile = getExternalFilesDir("APK").toString();
        final String apkFileName = "MyPrintShare.apk";
        File apkFile = new File(localApkFile + File.separator + apkFileName);
        try {
            InputStream inputStream = getAssets().open("PrintShare.apk");
            FileOutputStream fileOutputStream = new FileOutputStream(apkFile);
            byte[] temp=new byte[1024];
            int i=0;
            while ((i=inputStream.read(temp))!=-1){
                fileOutputStream.write(temp,0,i);
            }
            inputStream.close();
            fileOutputStream.close();
            FileProvider7.installApk(this,new File(localApkFile,apkFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shareFile(View v){

    }
}
