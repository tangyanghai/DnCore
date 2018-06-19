package com.example.corelibrary.net.dialog;

/**
 * @author : tyh
 * @time : 2018/06/15
 * @for :
 */
public class NetDialogBuilder {
    //是否可以取消
    private boolean mCancleAble;

    //显示的内容
    private String mHintText;

    public NetDialog build(){
       return new NetDialog();
    }
}
