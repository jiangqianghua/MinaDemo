package com.jqh.minaclient.mina;

import android.content.Context;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;

/**
 * Created by jiangqianghua on 18/1/6.
 */

public class ConnectionManager {

    private ConnectionConfig mConfig ;

    private NioSocketConnector mConnection ;

    private IoSession mSession ;

    private InetSocketAddress mAddress ;

    private MinaCallBack minaCallBack ;

    public ConnectionManager(ConnectionConfig config){
        this.mConfig = config;
        init();
    }

    private void init(){
        mAddress = new InetSocketAddress(mConfig.getIp(),mConfig.getPort());
        mConnection = new NioSocketConnector();
        mConnection.getSessionConfig().setReadBufferSize(mConfig.getReadBufferSize());
        mConnection.getFilterChain().addLast("logging",new LoggingFilter());
        mConnection.getFilterChain().addLast("codec",new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        mConnection.setHandler(new DefaultHandler());
        mConnection.setDefaultRemoteAddress(mAddress);
    }

    public void addMessageListener(MinaCallBack minaCallBack){
        this.minaCallBack = minaCallBack ;
    }

    /**
     * 外层调用取得连接
     * @return
     */
    public boolean connect(){
        try {
            ConnectFuture future = mConnection.connect();
            future.awaitUninterruptibly();
            mSession = future.getSession();
        }catch (Exception e){
            e.printStackTrace();
        }
        SessionManager.getInstatnce().setmSession(mSession);
        return mSession == null?false:true ;
    }

    /**
     * 外层调用，断开连接
     * @return
     */
    public void disConnect(){
        if(mConnection != null )
            mConnection.dispose();
        mConnection = null;
        mSession = null ;
        mAddress = null ;
    }

    private class DefaultHandler extends IoHandlerAdapter{

        DefaultHandler(){
        }

        @Override
        public void sessionOpened(IoSession session) throws Exception {
            super.sessionOpened(session);
        }

        @Override
        public void messageReceived(IoSession session, Object message) throws Exception {
            super.messageReceived(session, message);
            minaCallBack.messageRec(message);
        }
    }



}
