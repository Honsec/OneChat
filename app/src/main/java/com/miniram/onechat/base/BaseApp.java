package com.miniram.onechat.base;

import genius.baselib.base.BaseApplication;
import genius.baselib.center.Config;

/**
 * Created by Hongsec on 2016-08-03.
 */
public class BaseApp extends BaseApplication {


    @Override
    public void onCreate() {
        Config.init(true,"I will be back",true);
        super.onCreate();




    }
}
