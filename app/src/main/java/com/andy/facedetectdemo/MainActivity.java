package com.andy.facedetectdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.andy.facedetectdemo.api.FaceDetectAPI;
import com.andy.facedetectdemo.model.DetectResult;
import com.andy.facedetectdemo.model.FaceAttribute;
import com.andy.facedetectdemo.model.FaceInfo;
import com.andy.facedetectdemo.model.FacePosition;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    static final String API = "https://api.github.com";
    private String pictureUrl ="http://n.sinaimg.cn/transform/20150918/xXlt-fxhytwp5414868.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(FaceDetectAPI.API_URL).build();
        FaceDetectAPI api = adapter.create(FaceDetectAPI.class);

        api.getFaceInfo(FaceDetectAPI.API_KEY, FaceDetectAPI.API_SECRET, pictureUrl, new Callback<DetectResult>() {
            @Override
            public void success(DetectResult result, Response response) {
                Log.d("TAG", "-----------------SUCCESS-------------------");
                Log.d("TAG", "url-->" + result.getUrl());
                Log.d("TAG", "img_id-->" + result.getImgId());
                FaceInfo info  = (FaceInfo)result.getFace().get(0);
                FaceAttribute attribute = info.getFaceAttribute();
                int age = attribute.getAge().getValue();
                double d = attribute.getGender().getConfidence();

                Log.d("TAG", "age-->" + info.getFaceAttribute().getAge().getValue());
                Log.d("TAG", "confidence-->" + d);

                FacePosition.Point point = info.getFacePosition().getCenter();
                Log.d("TAG", "face center pos--> x : "+point.getX() + "; y : "+point.getY());

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("TAG", "ERROR-->"+error);
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
