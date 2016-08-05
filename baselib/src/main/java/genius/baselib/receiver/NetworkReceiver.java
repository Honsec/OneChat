package genius.baselib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import genius.baselib.PreferenceUtil;
import genius.baselib.bus.BusMessage;
import genius.baselib.bus.BusTool;
import genius.utils.TempData;
import genius.utils.UtilsNetwork;

/*  <receiver android:name=".receiver.NetworkReceiver">

            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>
            </intent-filter>

        </receiver>
*
* */

/**
 * Created by Hongsec on 2016-07-21.
 */
public class NetworkReceiver  extends BroadcastReceiver {
    public static final int UPDATE_NET = 1000;

    public static final String NetworkStatusKey= "network_key" ;

    /**
     * 是否在每个界面检测网络状态并提示
     */
    public static  boolean checkEnable = false;



    @Override
    public void onReceive(Context context, Intent intent) {

        noSetCheck(context);

    }

    /**
     * 인터넷 상태 검증밑 스위치에따라 UI전환시키기
     * @param context
     */
    public static void checkNetWork(Context context){
        if(NetworkReceiver.checkEnable) return;

        switch (TempData.getWeak(PreferenceUtil.getValue(context, NetworkReceiver.NetworkStatusKey,-1))){

            case -1://noset

                noSetCheck(context);

                break;
            case 0:

                if(NetworkReceiver.checkEnable){
                    BusTool.sendBus(new BusMessage(BusMessage.BUSTYPE.onEventMainThread,true, UtilsNetwork.TYPE.NOT_CONNECTED,NetworkReceiver.UPDATE_NET));
                }

                break;
            case 1:
                if(NetworkReceiver.checkEnable){
                    BusTool.sendBus(new BusMessage(BusMessage.BUSTYPE.onEventMainThread,true,UtilsNetwork.TYPE.WIFI,NetworkReceiver.UPDATE_NET));
                }
                break;
            case 2:
                if(NetworkReceiver.checkEnable){
                    BusTool.sendBus(new BusMessage(BusMessage.BUSTYPE.onEventMainThread,true,UtilsNetwork.TYPE.MOBILE,NetworkReceiver.UPDATE_NET));
                }
                break;
        }
    }

    private static void noSetCheck(Context context) {
        UtilsNetwork.TYPE connectivityStatus = UtilsNetwork.getConnectivityStatus(context);

        if(connectivityStatus == UtilsNetwork.TYPE.NOT_CONNECTED){
            //no network
            PreferenceUtil.setValue(context, NetworkReceiver.NetworkStatusKey,0);
        }else if(connectivityStatus == UtilsNetwork.TYPE.WIFI){
            //wifi
            PreferenceUtil.setValue(context,NetworkReceiver.NetworkStatusKey,1);
        }else if(connectivityStatus == UtilsNetwork.TYPE.MOBILE){
            //mobile
            PreferenceUtil.setValue(context,NetworkReceiver.NetworkStatusKey,2);
        }

        if(NetworkReceiver.checkEnable){
            BusTool.sendBus(new BusMessage(BusMessage.BUSTYPE.onEventMainThread,true,connectivityStatus,NetworkReceiver.UPDATE_NET));
        }
    }


}
