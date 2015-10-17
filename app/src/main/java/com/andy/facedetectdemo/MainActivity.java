package com.andy.facedetectdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.andy.facedetectdemo.model.DetectResult;
import com.andy.facedetectdemo.model.FaceAttribute;
import com.andy.facedetectdemo.model.FaceInfo;
import com.andy.facedetectdemo.model.FacePosition;
import com.andy.facedetectdemo.view.FaceView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    FaceView faceView;
    ProgressBar progressBar;

    private FaceDetect.Callback detectCallback = new FaceDetect.Callback() {
        @Override
        public void onDetectCompleted(DetectResult result, Response response) {
            List<FaceInfo> faceList = result.getFace();
            if (faceList == null) return;

            FaceInfo faceInfo = faceList.get(0);
            FaceAttribute attribute =faceInfo.getFaceAttribute();
            FacePosition position = faceInfo.getFacePosition();

            String sex =attribute.getGender().getValue();
            int age = attribute.getAge().getValue();

            int imgWidth = result.getImgWidth();
            int imgHeight = result.getImgHeight();
            float profileWidth = position.getWidth();
            float profileHeight = position.getHeight();
            FacePosition.Point center = position.getCenter();

            // 此处传的值均为相对图片的比例
            RectF rectF = new RectF();
            rectF.left = center.getX() - profileWidth/2;
            rectF.top = center.getY() - profileHeight/2;
            rectF.right = center.getX() + profileWidth/2;
            rectF.bottom = center.getY() + profileHeight/2;

            faceView.setInfo(sex + " " + age + "岁");
            faceView.setProfile(rectF);
            faceView.refresh();
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onDetectFailed(RetrofitError error) {
            faceView.setInfo("识别失败！");
            faceView.refresh();
            progressBar.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faceView = (FaceView) findViewById(R.id.face_view);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String path = getExternalCacheDir().getPath();
                File file = new File(path,"image");
                try {
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                FaceDetect faceDetect = new FaceDetect();
                faceDetect.detect(file, detectCallback);
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
