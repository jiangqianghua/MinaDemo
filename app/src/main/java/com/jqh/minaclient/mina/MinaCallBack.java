package com.jqh.minaclient.mina;

import java.io.Serializable;

/**
 * Created by jiangqianghua on 18/1/7.
 */

public abstract class MinaCallBack implements Serializable {

    public abstract  void messageRec(Object message);
}
