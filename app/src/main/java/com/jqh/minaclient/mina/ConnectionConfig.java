package com.jqh.minaclient.mina;

import android.content.Context;

/**
 * Created by jiangqianghua on 18/1/6.
 */

public class ConnectionConfig {

    private String ip;
    private int port;
    private int readBufferSize;
    private long connectionTimeout;

    public static MinaCallBack minaCallBack;


    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public static class Builder{
        private String ip = "192.168.168.20";
        private int port = 9226;
        private int readBufferSize = 10240;
        private long connectionTimeout = 10000;

        public Builder(){

        }

        public Builder setIp(String ip){
            this.ip = ip;
            return this;
        }

        public Builder setPort(int port){
            this.port = port;
            return this;
        }

        public Builder setReadBufferSize(int readBufferSize){
            this.readBufferSize = readBufferSize;
            return this;
        }

        public Builder setConnectionTimeout(long connectionTimeout){
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        private void applyConfig(ConnectionConfig config){

            config.ip = this.ip;
            config.port = this.port;
            config.readBufferSize = this.readBufferSize;
            config.connectionTimeout = this.connectionTimeout;
        }

        public ConnectionConfig builder(){
            ConnectionConfig config = new ConnectionConfig();
            applyConfig(config);
            return config;
        }

    }
}

