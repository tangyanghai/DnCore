package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corelibrary.net.RestClient;
import com.example.corelibrary.net.callback.IError;
import com.example.corelibrary.net.callback.IFailure;
import com.example.corelibrary.net.callback.IRequest;
import com.example.corelibrary.net.callback.ISuccess;
import com.example.corelibrary.net.rx.RxObserver;
import com.example.corelibrary.net.rx.RxRestClient;
import com.example.corelibrary.rxbus.RxBus;
import com.example.corelibrary.rxbus.RxEvent;
import com.example.corelibrary.rxbus.RxEventWithTag;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "AppCompatActivity";

    TextView mTv;
    private String path;
    private HashMap<String, Object> map;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.tv);
        initData();
        RxBus.getInstance().regist(this);
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count % 2 == 1) {
                    count++;
//                    requestNormal();
                    testRxBus();
                } else {
                    count++;
                    requestRx();
                }
            }
        });
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

    /**
     * 使用集成了Rxjava的网络框架
     */
    private void requestRx() {
        RxRestClient.post(path)
                .params(map)
                .excute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxObserver<Bean<List<JokeBean>>>() {
                    @Override
                    public void onSucceed(final Bean<List<JokeBean>> s) {
                        //请求成功  事件发送给订阅者
                        RxBus.getInstance().processChain(new Function() {
                            @Override
                            public Object apply(Object o) throws Exception {
                                return s;
                            }
                        },"ceshi");
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.i(TAG, "onError: 请求失败,原因:  " + e.getMessage());
                    }
                });
    }


    /**
     * 使用集成了Rxjava的网络框架
     */
    private void testRxBus() {
        RxBus.getInstance().processChain(new Function() {
            Bean<List<JokeBean>> listBean;

            @Override
            public Object apply(Object o) throws Exception {
                RxRestClient.post(path)
                        .params(map)
                        .excute()
                        .subscribe(new RxObserver<Bean<List<JokeBean>>>() {
                            @Override
                            public void onSucceed(final Bean<List<JokeBean>> s) {
                                //请求成功
                                listBean = s;
                            }

                            @Override
                            public void onFailure(Throwable e) {
                                Log.i(TAG, "onError: 请求失败,原因:  " + e.getMessage());
                            }
                        });

                return listBean;
            }
        });
    }

    /**
     * 没有集成Rxjava的网络框架
     */
    private void requestNormal() {
        RestClient.get(path)
                .params(map)
                .request(new IRequest() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "onStart: 开始请求");
                    }

                    @Override
                    public void onEnd() {
                        Log.i(TAG, "onEnd: 请求结束");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String reason) {
                        Log.i(TAG, String.format("onError: 错误回应  code = %1$d, reason = %2$s", code, reason));
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String string) {
                        Log.i(TAG, "onSuccess: 正确回应 -->" + string);
                    }
                })
                .excute();
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
