package com.mfitandroid7;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.libraryforfileprovider.FileProvider7;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ImageView mIvPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIvPhoto = (ImageView) findViewById(R.id.id_iv);
        // 6.0原生系统自行打开SDCard权限!
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
    private static final int REQUEST_CODE_TAKE_PHOTO = 0x110;
    private String mCurrentPhotoPath;
    public void shareFile(View v){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                    .format(new Date()) + ".png";
            File file = new File(getExternalCacheDir(), filename);
            mCurrentPhotoPath = file.getAbsolutePath();

            Uri fileUri = FileProvider7.getUriForFile(this, file);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        if (bitmap != null) {
            mIvPhoto.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("==", "onActivityResult: ");
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_TAKE_PHOTO) {
            mIvPhoto.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
        }
        // else tip?

    }
}
