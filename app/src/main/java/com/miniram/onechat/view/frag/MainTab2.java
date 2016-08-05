package com.miniram.onechat.view.frag;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.miniram.onechat.R;
import com.miniram.onechat.model.beans.GroupRoomMessage;

import java.util.ArrayList;
import java.util.List;

import genius.baselib.fragment.LazyBaseFragment;
import genius.rv.ViewHolder;
import genius.rv.adapter.CommonAdapter;

/**
 * Created by Hongsec on 2016-08-03.
 */
public class MainTab2 extends LazyBaseFragment {

    public static final int SENCER_ACTION = 12;
    private RecyclerView tab1_recyclerview = null;

    private MyAdapter myAdapter = null;
    private SpringView tab1_refreshSpringView;
    private View emptyData;



    protected void onCreateViewLazy(Bundle savedInstanceState) {

        setContentView(R.layout.frag_main_tab1);

        tab1_refreshSpringView =   findViewBId(R.id.tab1_refreshSpringView);
        tab1_refreshSpringView.setType(SpringView.Type.FOLLOW);
        tab1_refreshSpringView.setFooter(new DefaultFooter(getBaseParent()));
        tab1_refreshSpringView.setHeader(new DefaultHeader(getBaseParent()));
        tab1_refreshSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                tab1_refreshSpringView.onFinishFreshAndLoad();
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


        myAdapter = new MyAdapter(getBaseParent(),new ArrayList<GroupRoomMessage>());
        myAdapter.setEmptyView(emptyData);
        tab1_recyclerview.setAdapter(myAdapter);
        testDatas();
    }



    private void testDatas() {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                for(int i=0;i<100;i++){
                    GroupRoomMessage groupRoomMessage = new GroupRoomMessage();
                    groupRoomMessage.groupName = "웃음사망꾼";
                    groupRoomMessage.cur_user = i;
                    groupRoomMessage.max_user = i+10;
                    groupRoomMessage.recent_partin = "최근대화 "+i+"초전";

                    myAdapter.getmDatas().add(groupRoomMessage);
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


    private class MyAdapter extends CommonAdapter<GroupRoomMessage>{

        public MyAdapter(Context context, List<GroupRoomMessage> datas) {
            super(context, R.layout.item_grouproom_card, datas);
        }

        @Override
        public void convert(final ViewHolder holder, int position, final GroupRoomMessage groupRoomMessage) {

                holder.setImageResource(R.id.item_grouproom_img,R.drawable.default_nor_avatar);

                holder.setText(R.id.item_grouproom_nickname,groupRoomMessage.groupName);

                holder.setText(R.id.item_grouproom_detail,groupRoomMessage.cur_user+"/"+groupRoomMessage.max_user);

                holder.setText(R.id.item_grouproom_recent,groupRoomMessage.recent_partin);


        }
    }

}
