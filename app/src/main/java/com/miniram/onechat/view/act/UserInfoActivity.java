package com.miniram.onechat.view.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miniram.onechat.R;
import com.miniram.onechat.base.BaseTitleBackActivity;

import genius.baselib.inter.FilterdOnClickListener;

/**
 * Created by Hongsec on 2016-08-03.
 */
public class UserInfoActivity extends BaseTitleBackActivity {
    public static final String ICON = "userinfo_icon";
    public static final String NICKNAME = "userinfo_nickname";
    private ImageView act_userinfo_icon;
    private TextView act_userinfo_nickname;
    private TextView act_userinfo_detail;
    private TextView act_userinfo_ment;

    @Override
    protected int setContentLayoutResID() {
        return R.layout.activty_userinfo;
    }

    @Override
    protected void viewLoadFinished() {

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            act_userinfo_nickname.setText(extras.getString(UserInfoActivity.NICKNAME,""));

        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
//            getWindow().setEnterTransition(new Slide());
//            getWindow().setExitTransition(new Slide());
//        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public String toolbarTitle() {
        return getString(R.string.userinfo_title);
    }

    @Override
    public void initView() {
        act_userinfo_icon =    findViewBId(R.id.act_userinfo_icon);
        act_userinfo_nickname =    findViewBId(R.id.act_userinfo_nickname);
        act_userinfo_detail =    findViewBId(R.id.act_userinfo_detail);
        act_userinfo_ment =    findViewBId(R.id.act_userinfo_ment);
        findViewBId(R.id.act_userinfo_callchat).setOnClickListener(filterdOnClickListener);
        findViewBId(R.id.act_userinfo_sendball).setOnClickListener(filterdOnClickListener);
    }


    FilterdOnClickListener filterdOnClickListener = new FilterdOnClickListener() {
        @Override
        public void onFilterdClick(View v) {
            switch (v.getId()) {

                case R.id.act_userinfo_callchat:

                    break;
                case R.id.act_userinfo_sendball:

                    break;
            }
        }
    };

}
