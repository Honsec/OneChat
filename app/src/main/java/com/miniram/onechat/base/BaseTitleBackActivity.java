package com.miniram.onechat.base;

import android.view.View;
import android.widget.TextView;

import com.miniram.onechat.R;

import genius.baselib.base.BaseActivity;
import genius.baselib.inter.FilterdOnClickListener;

/**
 * Created by Hongsec on 2016-08-04.
 */
public abstract  class BaseTitleBackActivity extends BaseActivity {


    public abstract String toolbarTitle();
    public  abstract void initView() ;
    @Override
    protected void initViews() {


        findViewBId(R.id.common_titlebar_onback).setOnClickListener(new FilterdOnClickListener() {
            @Override
            public void onFilterdClick(View v) {
                try {
                    onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((TextView)findViewBId(R.id.common_titlebar_title)).setText(toolbarTitle());
        initView();
    }


}
