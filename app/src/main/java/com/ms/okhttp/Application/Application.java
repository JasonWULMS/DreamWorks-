package com.ms.okhttp.Application;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;

import io.realm.Realm;

/**
 * Created by Jason Wu on 2017/9/10.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InitNoHttp();
        InitRealm();

    }

    private void InitRealm() {
        Realm.init(this);
    }

    private void InitNoHttp() {
        NoHttp.initialize(this, new NoHttp.Config()
                .setConnectTimeout(30 * 1000) // 全局连接超时时间，单位毫秒。
                .setReadTimeout(30 * 1000) // 全局服务器响应超时时间，单位毫秒。
                .setCookieStore(
                        new DBCookieStore(this)
                                .setEnable(false) // true启用自动维护Cookie，fasle禁用自动维护Cookie。
                )
                .setCacheStore(
                        new DBCacheStore(this) // 配置缓存到数据库。
                                .setEnable(true) // true启用缓存，fasle禁用缓存。
                )
                .setNetworkExecutor(new OkHttpNetworkExecutor())  // 使用OkHttp做网络层
        );
        Logger.setDebug(true); // 开启NoHttp调试模式。
        Logger.setTag("NoHttpSample"); // 设置NoHttp打印Log的TAG。
    }
}
