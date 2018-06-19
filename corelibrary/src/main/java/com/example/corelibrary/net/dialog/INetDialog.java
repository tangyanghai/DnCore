package com.example.corelibrary.net.dialog;

/**
 * @author : tyh
 * @time : 2018/06/15
 * @for : 网络请求的Dialog
 */
public interface INetDialog {

    /**
     * 打开
     */
    void show();

    /**
     * 关闭
     */
    void dismiss();

    /**
     * @param cancleAble 是否可以取消
     */
    void setCancleAble(boolean cancleAble);

    /**
     * @param hintText 提示语
     */
    void setHintText(String hintText);
}
