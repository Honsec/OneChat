package com.miniram.onechat.view.act;

import android.content.Context;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.miniram.onechat.R;
import com.miniram.onechat.control.ShakeSensor;
import com.miniram.onechat.view.frag.MainTab1;
import com.miniram.onechat.view.frag.MainTab2;
import com.miniram.onechat.view.frag.MainTab3;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import genius.baselib.base.BaseActivity;
import genius.baselib.bus.BusMessage;
import genius.baselib.bus.BusTool;
import genius.utils.UtilsLog;

/**
 * Created by Hongsec on 2016-08-03.
 */
public class MainActivity extends BaseActivity {
    private ViewPager act_main_viewpager;
    private MagicIndicator act_main_indicator;

    private String[] tabnames= null ;
    private PagerAdapter mAdapter;
    private ShakeSensor mShakeSensor;

    @Override
    protected int setContentLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void viewLoadFinished() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
//            getWindow().setEnterTransition(new Explode());
//            getWindow().setExitTransition(new Explode());
//        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {

        act_main_viewpager =   findViewBId(R.id.act_main_viewpager);
        act_main_indicator =   findViewBId(R.id.act_main_indicator);


        //Main tabnames
        tabnames = new String[]{getString(R.string.main_tabname_0),getString(R.string.main_tabname_1),getString(R.string.main_tabname_2)};
        mAdapter  =new  AppSectionsPagerAdapter(getSupportFragmentManager());
        act_main_viewpager.setAdapter(mAdapter);

        //set Indicator
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setFollowTouch(false);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabnames.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setText(tabnames[index]);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        act_main_viewpager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
//                indicator.setLineHeight(UIUtil.dip2px(context, 6));
//                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                List<String> colorList = new ArrayList<String>();
                colorList.add(getString(R.string.tab_indicator_undercolor));
                indicator.setColorList(colorList);
                return indicator;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
//                if (index == 2) {
//                    return 1.5f;
//                } else {
                    return 1;
//                }
            }
        });

        act_main_indicator.setNavigator(commonNavigator);

        act_main_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                act_main_indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                act_main_indicator.onPageSelected(position);
                switch (position){

                    case 0:
                        sensorRegister();
                        break;
                    default:
                        if(mShakeSensor!=null){
                            mShakeSensor.unregister();
                        }
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                act_main_indicator.onPageScrollStateChanged(state);
            }
        });


        //shake
        mShakeSensor = new ShakeSensor(this, 2000);
        sensorRegister();

    }

    private void sensorRegister() {

        if(mShakeSensor!=null){
            mShakeSensor.setShakeListener(new ShakeSensor.OnShakeListener() {
                long time =0;
                @Override
                public void onShakeComplete(SensorEvent event) {
                    if(System.currentTimeMillis()- time> 1000L){
                        UtilsLog.i();
                        time = System.currentTimeMillis();
                        BusTool.sendBus(new BusMessage(BusMessage.BUSTYPE.onEventMainThread,false,null, MainTab1.SENCER_ACTION,MainTab1.class.getSimpleName()));
                    }
                }
            });
            mShakeSensor.register();
        }
    }


    private MainTab1 mainTab1;
    private MainTab2 mainTab2;
    private MainTab3 mainTab3;

    public MainTab1 getMainTab1() {
        if(mainTab1 == null){
            mainTab1 = new MainTab1();
        }
        return mainTab1;
    }

    public MainTab2 getMainTab2() {
        if(mainTab2 == null){
            mainTab2 = new MainTab2();
        }
        return mainTab2;
    }

    public MainTab3 getMainTab3() {
        if(mainTab3 == null){
            mainTab3 = new MainTab3();
        }
        return mainTab3;
    }

    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            switch (i){
                case 0:

                    return getMainTab1();
                case 1:

                    return getMainTab2();

                case 2:

                    return getMainTab3();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Section " + (position + 1);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            // super.destroyItem(container, position, object);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorRegister();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mShakeSensor!=null){
            mShakeSensor.unregister();
        }
    }
}
