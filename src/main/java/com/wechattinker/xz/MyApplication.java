package com.wechattinker.xz;

import android.app.Application;
import android.util.Log;

import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

/**
 * Created by xiong on 2017/11/14.
 *
 * 比较坑的是从微信demo里面带出来的tinkerpatch.gradle这个目录，这里面，注释在里面写的很清楚了
 *  reflectApplication = true
 *  appKey = "74e65e4805572fd6"
 *   appVersion = "1.0.0"
 *
 *
 *   还有注意相关依赖，！！
 *   （注意在最外层的app中也是要加上依赖的）下面的博客很清楚了
 *   http://blog.csdn.net/u010694658/article/details/76682717


     http://blog.csdn.net/u010381752/article/details/56012405
     下面这两个博客已经很清楚了

     还有这只连接服务器的时间设置也是比较坑的。。

 *
 *
 */

 public class MyApplication extends Application {
    private ApplicationLike tinkerApplicationLike;
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.TINKER_ENABLE) {

            // 我们可以从这里获得Tinker加载过程的信息
            tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

            // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
            TinkerPatch.init(tinkerApplicationLike)
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true);

            // 每隔3个小时去访问后台时候有更新,通过handler实现轮训的效果
            //这里也很操蛋，事件必须是1到24小时之间
            new FetchPatchHandler().fetchPatchWithInterval(1);
            Log.i("TAG", "tinker init");
        }
    }
}