package com.jqh.minaclient.mina;

import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;

public class MinaService extends Service {

    private ConnectThread thread;
    public MinaService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        thread = new ConnectThread("mina",ConnectionConfig.minaCallBack);
        thread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.disConnection();
        thread = null ;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class ConnectThread extends HandlerThread{
        boolean isConnection;
        ConnectionManager mManager ;

        ConnectThread(String name,MinaCallBack calback){
            super(name);
            ConnectionConfig config = new ConnectionConfig.Builder()
                    .setIp("192.168.1.102")
                    .setPort(9125)
                    .setConnectionTimeout(10000)
                    .setReadBufferSize(1024).builder();
            mManager = new ConnectionManager(config);
            mManager.addMessageListener(calback);
        }

        // 相当于线程的run方法,在子线程执行
        @Override
        protected void onLooperPrepared() {
            for(;;){
                isConnection = mManager.connect();
                if(isConnection)
                    break;
                try {
                    Thread.sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        // 断开服务器
        public void disConnection(){
            mManager.disConnect();
        }
    }
}
