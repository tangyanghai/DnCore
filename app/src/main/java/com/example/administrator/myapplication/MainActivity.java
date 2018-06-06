package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corelibrary.net.Http;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "AppCompatActivity";

    TextView mTv;
    private String path;
    private HashMap<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.tv);
        initData();
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Http.get(path)
                        .setParams(map)
                        .excute(new Http.CallBack<Bean<List<JokeBean>>>() {
                            @Override
                            public void onSucceed(Bean<List<JokeBean>> jokeBeanBaseBean) {
                                List<JokeBean> result = jokeBeanBaseBean.getResult();
                                JokeBean jokeBean = result.get(0);
                                Log.i(TAG, "onSucceed: " + jokeBean.toString());
                            }

                            @Override
                            public void onFailed(int errCode, String reson) {
                                Toast.makeText(MainActivity.this, reson, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void initData() {
        path = "Joke/QueryJokeByTime";
        String key = "75cc56966b9d46f599cc8d94f86241c2";
        int page = 1;
        int rows = 3;
//        String sort = "asc";//请求时间之后的笑话
        String sort = "desc";//请求时间之前的笑话
        long time = System.currentTimeMillis() / 1000;

        map = new HashMap<>();
        map.put("key", key);
        map.put("page", page);
        map.put("rows", rows);
        map.put("sort", sort);
        map.put("time", time);
    }
}
