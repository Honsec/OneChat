package com.miniram.onechat.view.frag;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.miniram.onechat.R;
import com.miniram.onechat.control.ShakeSound;
import com.miniram.onechat.model.beans.UserInfoMessage;
import com.miniram.onechat.view.act.UserInfoActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import genius.baselib.bus.BusMessage;
import genius.baselib.fragment.LazyBaseFragment;
import genius.baselib.inter.FilterdOnClickListener;
import genius.rv.ViewHolder;
import genius.rv.adapter.CommonAdapter;
import genius.utils.UtilsVersionMC;

/**
 * Created by Hongsec on 2016-08-03.
 */
public class MainTab1 extends LazyBaseFragment {

    public static final int SENCER_ACTION = 12;
    private RecyclerView tab1_recyclerview = null;

    private MyAdapter myAdapter = null;
    private SpringView tab1_refreshSpringView;
    private View emptyData;
    private ShakeSound shakeSound;

    boolean is_from_shake = false;

    @Override
    protected void Bus_onEventMainThread(BusMessage myBus) {
        super.Bus_onEventMainThread(myBus);

        is_from_shake = true;
        tab1_refreshSpringView.callFresh();
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        shakeSound.registerSound();

    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        shakeSound.unregisterSound();
    }

    protected void onCreateViewLazy(Bundle savedInstanceState) {

        setContentView(R.layout.frag_main_tab1);


        shakeSound = new ShakeSound(getBaseParent());

        tab1_refreshSpringView =   findViewBId(R.id.tab1_refreshSpringView);
        tab1_refreshSpringView.setType(SpringView.Type.FOLLOW);
        tab1_refreshSpringView.setFooter(new DefaultFooter(getBaseParent()));
        tab1_refreshSpringView.setHeader(new DefaultHeader(getBaseParent()));
        tab1_refreshSpringView.setListener(new SpringView.OnFreshListener() {

            boolean ing = false;
            @Override
            public void onRefresh() {
                tab1_refreshSpringView.onFinishFreshAndLoad();
                if(is_from_shake){
                    ing = true;
                    shakeSound.playMatchingShake();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(new Random().nextInt(10)/2 == 0){
                                myAdapter.getmDatas().clear();
                                myAdapter.notifyWithEmptyDatasetChanged();
                                shakeSound.playNoMatchShake();
                            }else{
                                shakeSound.playMatchShake();
                                testDatas();
                            }

                            is_from_shake =false;



                            ing =false;
                        }
                    },2000);

                    return;
                }

                testDatas();
            }

            @Override
            public void onLoadmore() {
                tab1_refreshSpringView.onFinishFreshAndLoad();
            }

        });


        emptyData = findViewBId(R.id.emptyData);


        tab1_recyclerview =  findViewBId(R.id.tab1_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseParent());
        tab1_recyclerview.setLayoutManager(linearLayoutManager);
        tab1_recyclerview.setHasFixedSize(true);


        myAdapter = new MyAdapter(getBaseParent(),new ArrayList<UserInfoMessage>());
        myAdapter.setEmptyView(emptyData);
        tab1_recyclerview.setAdapter(myAdapter);
        myAdapter.notifyWithEmptyDatasetChanged();
    }



    private void testDatas() {
        ImageView fds;
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                for(int i=0;i<100;i++){
                    UserInfoMessage userInfoMessage = new UserInfoMessage();
                    userInfoMessage.distance=i+"00m";
                    userInfoMessage.ment="내가 오늘의 기분이다~";
                    userInfoMessage.icon_url="";
                    userInfoMessage.user_age= "1990";
                    userInfoMessage.user_sex= i%2;
                    userInfoMessage.username = "웃음사망꾼";
                    myAdapter.getmDatas().add(userInfoMessage);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);


                myAdapter.notifyWithEmptyDatasetChanged();
            }
        }.execute();
    }


    private class MyAdapter extends CommonAdapter<UserInfoMessage>{

        public MyAdapter(Context context, List<UserInfoMessage> datas) {
            super(context, R.layout.item_userinfo_card, datas);
        }

        @Override
        public void convert(final ViewHolder holder, int position, final UserInfoMessage userInfoMessage) {

            // icon images
            holder.setImageResource(R.id.item_userinfo_img,R.drawable.default_nor_avatar);
            //nick name
            holder.setText(R.id.item_userinfo_nickname,userInfoMessage.username);

            //detail  age + year +distance
            holder.setText(R.id.item_userinfo_detail,(userInfoMessage.user_sex==0?getString(R.string.sex_women):getString(R.string.sex_man))+","+userInfoMessage.user_age+" "+userInfoMessage.distance);

            if(userInfoMessage.user_sex == 0){
                // women
                holder.setTextColor(R.id.item_userinfo_detail, UtilsVersionMC.getColor(getResources(),R.color.user_card_sex_w));
            }else{
                // man
                holder.setTextColor(R.id.item_userinfo_detail,UtilsVersionMC.getColor(getResources(),R.color.user_card_sex_m));
            }

            //User ment , max 2line
            holder.setText(R.id.item_userinfo_ment,userInfoMessage.ment);



            holder.getView(R.id.item_userinfo_img).setOnClickListener(new FilterdOnClickListener() {
                @Override
                public void onFilterdClick(View v) {
                    Intent intent = new Intent(getBaseParent(), UserInfoActivity.class);
//                    startActivity(intent);
                    intent.putExtra(UserInfoActivity.NICKNAME,userInfoMessage.username);
                    intent.putExtra(UserInfoActivity.ICON,userInfoMessage.icon_url);
                    ActivityOptions activityOptions= null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activityOptions = ActivityOptions.makeSceneTransitionAnimation(getBaseParent(), android.util.Pair.create(holder.getView(R.id.item_userinfo_img),"icon"), android.util.Pair.create(holder.getView(R.id.item_userinfo_nickname),"nickname"));
                        startActivity(intent,activityOptions.toBundle());
                    }else{
                        startActivity(intent);
                    }
                }
            });





        }
    }

}
