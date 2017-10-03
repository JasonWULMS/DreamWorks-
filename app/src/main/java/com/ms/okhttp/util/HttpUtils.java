package com.ms.okhttp.util;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.HashMap;

/**
 * Created by Jason Wu on 2017/9/10.
 */

public class HttpUtils {
    public static void request(String url, RequestMethod mode, HashMap<String,Object> data, OnResponseListener onResponseListener){
        Request<String> request = NoHttp.createStringRequest(url, mode);
        request.add(data);
        CallServer.getInstance().add((int) System.currentTimeMillis(),request,onResponseListener);
    }
    public static Response<String> requestSycn(String url, RequestMethod mode, HashMap<String,Object> data){
        Request<String> request = NoHttp.createStringRequest(url, mode);
        request.add(data);
        Response<String> response = NoHttp.startRequestSync(request);
        return response;
    }


}
