package com.example.corelibrary.net.rx;

import android.widget.TextView;

import com.example.corelibrary.net.dialog.INetDialog;

import io.reactivex.disposables.Disposable;

/**
 * @author : tyh
 * @time : 2018/06/15
 * @for :
 */
public abstract class RxDialogObserver<T> extends RxObserver<T> {
    private INetDialog mDialog;

    public RxDialogObserver(boolean cancleAble, String hintText) {
        if (mDialog != null) {
            mDialog.setCancleAble(cancleAble);
            mDialog.setHintText(hintText);
        }
    }

    public RxDialogObserver(boolean cancleAble) {
        this(cancleAble, null);
    }

    public RxDialogObserver(String hintText) {
        this(true, hintText);
    }

    public RxDialogObserver() {
        this(true, null);
    }


    @Override
    public void onSubscribe(Disposable d) {
        showDialog();
        super.onSubscribe(d);
    }

    @Override
    public void onComplete() {
        super.onComplete();
        dismissDialog();
    }

    /**
     * 关闭网络请求
     */
    public void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }


    /**
     * 关闭网络请求
     */
    public void showDialog() {
        if (mDialog != null) {
            mDialog.show();
        }
    }
}
