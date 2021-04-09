package com.longyu.quillandroid;

import android.view.View;

/**
 * @Author: com.longyu
 * @CreateDate: 2021/4/7 15:17
 * @Description:  状态监听
 */
public interface OnToolbarClickListener {

    void onToolbarClick(Format format, String toolbar);

    void onToolbarTxtClick(View view);

    void onToolbarTxtSizeClick(View view);

    void onToolbarTxtColorClick(View view);

    void onToolbarState(int position, boolean isSelect, String value);
}
