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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.learn.db.DbOperator;
import com.learn.utils.ImageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    public static final int REQUEST_CODE = 1000;
    private Button btnStartCamera;
    private Button btnSaveCamera;
    private EditText description;

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
        btnSaveCamera = (Button) findViewById(R.id.btn_save_camera);
        btnSaveCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        description = (EditText) findViewById(R.id.description_edit_text);
    }


    private void save() {
        DbOperator dbOperator = new DbOperator(this);
        Long state = dbOperator.saveItem(getDescription(), allImgPath, thumbImgPath);
        if (-1 == state) {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        }


    }

    private String getDescription() {
        String str = null;
        try {
            str = description.getText().toString();
        } catch (NullPointerException e) {
            str = "您没有输入任何数据";
        }
        return str;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE && data == null) {
            //返回的是缩略图
            Bitmap bitmap = (Bitmap) ImageUtils.decodeBitmapFromFile(allImgPath, 500, 500);
            saveImg(bitmap, thumbImgPath);

//            Bundle extras =  data.getExtras();
//            Bitmap bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);

        }

    }

    private void saveImg(Bitmap img, String path) {
        File file = new File(path);

        try {
            FileOutputStream out = new FileOutputStream(file);
            img.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
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
            File allImg = new File(albumDir, imageFileName + ".jpeg");
            File thumbImg = new File(albumDir, imageFileName + "thumb" + ".jpeg");

            allImgPath = allImg.getAbsolutePath();
            thumbImgPath = thumbImg.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initImagePath() {
        albumDir = new File(Environment.getExternalStorageDirectory() + File.separator + "save_my_img");
        if (!albumDir.exists()) {
            albumDir.mkdirs();
        }

    }
}
