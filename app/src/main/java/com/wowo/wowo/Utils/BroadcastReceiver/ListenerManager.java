package com.wowo.wowo.Utils.BroadcastReceiver;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * author: ZhangXiaoWei
 * email : cherno@126.com
 * blog  : http://www.jianshu.com/users/0f532bf88454/latest_articles
 * time  : 2017/8/2
 * desc  : 广播的管理类
 */

public class ListenerManager {
    /**
     * 单例模式
     */
    public static ListenerManager listenerManager;

    /**
     * 注册的接口集合，发送广播的时候都能收到
     */
    private List<IListener> iListenerList = new CopyOnWriteArrayList<IListener>();

    /**
     * 获得单例对象
     */
    public static ListenerManager getInstance(){
        if(listenerManager == null){
            listenerManager = new ListenerManager();
        }
        return listenerManager;
    }

    /**
     * 注册监听
     */
    public void registerListener(IListener iListener){
        iListenerList.add(iListener);
    }

    /**
     * 注销监听
     */
    public void unRegisterListener(IListener iListener){
        if(iListenerList.contains(iListener)){
            iListenerList.remove(iListener);
        }
    }

    /**
     * 发送广播
     */
    public void sendBroadCast(String type,String str){
        for (IListener iListener : iListenerList){
            iListener.notifyAllActivity(type,str);
        }
    }
}
