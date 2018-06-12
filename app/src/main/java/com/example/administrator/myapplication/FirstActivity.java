package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corelibrary.rxbus.RxBus;
import com.example.corelibrary.rxbus.RxEvent;
import com.example.corelibrary.rxbus.RxEventWithTag;

/**
 * @author : tyh
 * @time : 2018/06/12
 * @for :
 */
public class FirstActivity extends AppCompatActivity {
    private static final String TAG = "==FirstActivity==";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this,MainActivity.class));
            }
        });
        RxBus.getInstance().regist(this);
    }

    @RxEvent()
    public void onRequestSuceess(Object s) {
        Toast.makeText(this, s.toString(), Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onRequestSuceess: 没有标记的方法返回了");
    }

    @RxEventWithTag("ceshi")
    public void onRequestSuceessWithTag(Object s) {
        Toast.makeText(this, s.toString(), Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onRequestSuceess: 有标记的方法返回了");
    }



}
