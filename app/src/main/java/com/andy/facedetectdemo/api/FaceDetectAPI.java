package com.andy.facedetectdemo.api;

import com.andy.facedetectdemo.model.DetectResult;
import com.andy.facedetectdemo.model.FaceInfo;

import org.bouncycastle.asn1.cms.PasswordRecipientInfo;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by lxn on 2015/10/17.
 */
public interface FaceDetectAPI {

    String API_URL = "http://api.cn.faceplusplus.com";
    String API_KEY = "d91b4f39e0e021d5bf20e1e373e03b66";
    String API_SECRET = "p2y6wflq5lFLad58_wiFFw1bIdms3a7t";

    @GET("/detection/detect")
    public void getFaceInfo(
            @Query("api_key") String apiKey,
            @Query("api_secret") String apkSecret,
            @Query("url") String url,
            Callback<DetectResult> callback
            );
}
