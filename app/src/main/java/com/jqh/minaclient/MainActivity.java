package com.jqh.minaclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jqh.minaclient.mina.ConnectionConfig;
import com.jqh.minaclient.mina.MinaCallBack;
import com.jqh.minaclient.mina.MinaService;
import com.jqh.minaclient.mina.SessionManager;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable{

    private EditText sendmsg ;

    private TextView recmsg ;

    private Button sendbtn;

    private Button startserverbtn ;

    private MinaCallBack minaCallBack = new MyMinaCallBack();

    private class MyMinaCallBack extends MinaCallBack
    {
        @Override
        public void messageRec(Object message) {
            recmsg.setText(String.valueOf(message));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sendmsg = (EditText)findViewById(R.id.sendmsg);
        recmsg = (TextView)findViewById(R.id.recmsg);
        sendbtn = (Button)findViewById(R.id.sendbtn);
        startserverbtn = (Button)findViewById(R.id.startserverbtn);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.getInstatnce().writeToServer(sendmsg.getText());
            }
        });

        ConnectionConfig.minaCallBack = minaCallBack;

        startserverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MinaService.class);
                //intent.putExtra("listener", minaCallBack);
                startService(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this,MinaService.class));
    }

}
