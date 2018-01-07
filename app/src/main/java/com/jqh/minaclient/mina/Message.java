package com.jqh.minaclient.mina;

/**
 * Created by jiangqianghua on 18/1/7.
 */

public class Message implements Runnable {

    private Object message ;

    private MinaCallBack minaCallBack ;

    private Message(Object message,MinaCallBack minaCallBack)
    {
        this.message = message ;
        this.minaCallBack = minaCallBack ;
    }

    @Override
    public void run() {
        // 主线程运行
        minaCallBack.messageRec(message);
    }
}
