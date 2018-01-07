package com.jqh.minaclient.mina;

import org.apache.mina.core.session.IoSession;

/**
 * Created by jiangqianghua on 18/1/7.
 */

public class SessionManager {

    private static SessionManager mInstatnce = null ;

    private IoSession mSession = null ;

    public static SessionManager getInstatnce(){
        if(mInstatnce == null){
            synchronized (SessionManager.class){
                if(mInstatnce == null)
                    mInstatnce = new SessionManager();
            }
        }
        return mInstatnce ;
    }

    private SessionManager(){

    }

    public void setmSession(IoSession mSession) {
        this.mSession = mSession;
    }

    public void writeToServer(Object msg){
        if(mSession != null)
        {
            mSession.write(msg.toString());
        }
    }

    public void closeSession(){
        if(mSession != null){
            mSession.close();
        }
    }

    public void removeSession(){
        this.mSession = null ;
    }
}
