package com.wowo.wowo.Utils.BroadcastReceiver;

/**
 * author: ZhangXiaoWei
 * email : cherno@126.com
 * blog  : http://www.jianshu.com/users/0f532bf88454/latest_articles
 * time  : 2017/8/2
 * desc  : 发送广播接口
 */

public interface IListener {
    void notifyAllActivity(String type, String value);
}
