package com.andy.facedetectdemo;

import android.graphics.Bitmap;
import android.telecom.Call;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lxn on 2015/10/17.
 */
public class FaceDetect {

    String picture = "http://n.sinaimg.cn/transform/20150918/xXlt-fxhytwp5414868.jpg";

    /**
     * 识别图片
     * @param bitmap 要识别的图片
     */
    public void detect(final Bitmap bitmap, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                detectSync(bitmap, callback);
            }
        }).start();
    }

    private void detectSync(Bitmap bitmap, Callback callback) {

    }


    /**
     * 请求图片识别的回调函数
     */
    interface Callback {
        void onDetectSuccess(JSONObject json);
        void onDetectFailed();
    }

}
