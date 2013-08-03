package com.learn.canmer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.learn.utils.ImageUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    public static final int REQUEST_CODE = 1000;
    private Button btnStartCamera;
    private ImageView imageView;
    private File albumDir;
    private String allImgPath;
    private String thumbImgPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initUi();
        initImagePath();
    }

    private void initUi() {
        btnStartCamera = (Button) findViewById(R.id.btn_start_camera);
        imageView = (ImageView) findViewById(R.id.img_view);

        btnStartCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE && data == null) {
            //返回的是缩略图
            Bitmap bitmap = (Bitmap) ImageUtils.decodeBitmapFromFile(allImgPath, 500, 500);

//            Bundle extras =  data.getExtras();
//            Bitmap bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);

        }

    }


    private void startCamera() {
        createImageFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(allImgPath)));
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void createImageFile() {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMDD_HHmmss").format(new Date());
            String imageFileName = "my_img" + timeStamp;
            File allImg = new File(albumDir, imageFileName + " .jpeg");
            File thumbImg = new File(albumDir, imageFileName + "thumb" + ".jpeg");

            allImgPath = allImg.getAbsolutePath();
            thumbImgPath = thumbImg.getAbsolutePath();

        } catch (Exception e) {

        }
    }

    private void initImagePath() {
        albumDir = new File(Environment.getExternalStorageDirectory() + File.separator + "save_my_img");
        if (!albumDir.exists()) {
            albumDir.mkdirs();
        }

    }
}
