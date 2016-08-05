package com.miniram.onechat.view.frag;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.miniram.onechat.R;
import com.miniram.onechat.model.beans.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import genius.baselib.fragment.LazyBaseFragment;
import genius.rv.ViewHolder;
import genius.rv.adapter.MultiItemCommonAdapter;
import genius.rv.support.MultiItemTypeSupport;
import genius.utils.UtilsVersionMC;

/**
 * Created by Hongsec on 2016-08-03.
 */
public class MainTab3 extends LazyBaseFragment {

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


        myAdapter = new MyAdapter(getBaseParent(), new ArrayList<ChatMessage>(), new MultiItemTypeSupport<ChatMessage>() {
            @Override
            public int getLayoutId(int itemType) {
                switch (itemType){

                    case 0:

                        return R.layout.item_mychat_card;

                    case 1:

                        return R.layout.item_mychat_group_card;
                }

                return 0;
            }

            @Override
            public int getItemViewType(int position, ChatMessage chatMessage) {
                if(chatMessage.isgroup){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
        myAdapter.setEmptyView(emptyData);
        tab1_recyclerview.setAdapter(myAdapter);
        testDatas();

    }



    private void testDatas() {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                for(int i=0;i<100;i++){
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.name = "강호동이";
                    if(i%2==0){
                        chatMessage.groupname = "쇼미더 풍선";
                        chatMessage.cur_user = 10+i;
                        chatMessage.max_user = 20+i;
                        chatMessage.isgroup = true;
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
                    chatMessage.lasttime = simpleDateFormat.format(new Date(System.currentTimeMillis()*1000));
                    chatMessage.recentchat="니들이 게맛을 알아~ ";
                    myAdapter.getmDatas().add(chatMessage);
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


    private class MyAdapter extends MultiItemCommonAdapter<ChatMessage> {


        public MyAdapter(Context context, List<ChatMessage> datas, MultiItemTypeSupport<ChatMessage> multiItemTypeSupport) {
            super(context, datas, multiItemTypeSupport);
        }

        @Override
        public void convert(ViewHolder holder, int position, ChatMessage chatMessage) {

            switch (getItemViewType(position)){

                case 0:// normal chat

                    holder.setText(R.id.item_mychat_nickname,chatMessage.name);

                    holder.setText(R.id.item_mychat_recent,chatMessage.recentchat);

                    holder.setText(R.id.item_mychat_group_time,chatMessage.lasttime);



                    break;

                case 1: // group chat

                    holder.setText(R.id.item_grouproom_name,chatMessage.groupname);


                    holder.setText(R.id.item_grouproom_detail,chatMessage.cur_user+"/"+chatMessage.max_user);

                    Link link = new Link(chatMessage.name);
                    link.setBold(true);
                    link.setUnderlined(false);
                    link.setTextColor(UtilsVersionMC.getColor(getResources(),R.color.bootstrap_gray_dark));
                    link.setAppendedText(":");
                    LinkBuilder.on((TextView) holder.getView(R.id.item_grouproom_recent)).setText(chatMessage.name+chatMessage.recentchat).addLink(link).build();

                    holder.setText(R.id.item_mychat_group_time,chatMessage.lasttime);

                    break;
            }


        }




    }

}
