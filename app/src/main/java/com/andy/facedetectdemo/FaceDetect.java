package com.andy.facedetectdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.telecom.Call;
import android.util.Log;

import com.andy.facedetectdemo.api.FaceDetectAPI;
import com.andy.facedetectdemo.model.DetectResult;
import com.andy.facedetectdemo.model.FaceAttribute;
import com.andy.facedetectdemo.model.FaceInfo;
import com.andy.facedetectdemo.model.FacePosition;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 *
 * Created by lxn on 2015/10/17.
 */
public class FaceDetect {


    /**
     * 识别图片
     * @param file 要识别的图片的二进制文件
     */
    public void detect(final File file, final Callback callback) {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(FaceDetectAPI.API_URL).build();
        FaceDetectAPI api = adapter.create(FaceDetectAPI.class);

        TypedFile typedFile = new TypedFile("image/*", file );
        api.getFaceInfo(FaceDetectAPI.API_KEY, FaceDetectAPI.API_SECRET, typedFile, new retrofit.Callback<DetectResult>() {
            @Override
            public void success(DetectResult result, Response response) {
                callback.onDetectCompleted(result, response);
            }

            @Override
            public void failure(RetrofitError error) {
                callback.onDetectFailed(error);
            }
        });
    }


    /**
     * 请求图片识别的回调函数
     */
    interface Callback {
        void onDetectCompleted(DetectResult result, Response response);
        void onDetectFailed(RetrofitError error);
    }

}
