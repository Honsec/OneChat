package com.miniram.onechat.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by Hongsec on 2016-08-05.
 */
public class DialogBuilder {


    /**
     * 후에 다이얼로그 모양을 쉽게 변환시키기 위함.
     * @param context
     * @return
     */
    public static AlertDialog.Builder show(Context context ){
        return  new AlertDialog.Builder(context);
    }


}
