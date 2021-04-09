package com.longyu.quillandroid.toolbar.label.pop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longyu.qull_android.R;

import java.util.ArrayList;
import java.util.List;

import com.longyu.quillandroid.toolbar.label.pop.adapter.TextSizeAdapter;
import com.longyu.quillandroid.toolbar.label.pop.bean.TextSizeBean;

/**
 * @ClassName: ReportUtils
 * @Description:
 * @Author: com.longyu
 * @CreateDate: 2019/12/24 0024 14:31
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 * <p>  设置字体大小
 */
public class ToolbarTextSizePop {

    private RecyclerView rlvLayout;

    private TextSizeAdapter mSizeAdapter;

    private PopupWindow menuPop;

    private Activity mContext;

    private static ToolbarTextSizePop mInstance;

    private OnTextSizeClickListener mPopClickListener;

    private String[] mTextSize = new String[]{"small", "normal", "large", "huge"};

    public ToolbarTextSizePop(Activity context) {
        this.mContext = context;
    }

    public void setOnTextSizeClickListener(OnTextSizeClickListener mOnMenuPopClickListener) {
        this.mPopClickListener = mOnMenuPopClickListener;
    }

    public interface OnTextSizeClickListener {
        void onTextSizeClick(String textSize);

        void onTextSizeDismiss();
    }

    /**
     * 单例
     *
     * @return
     */
    public static synchronized ToolbarTextSizePop getInstance(Activity context) {
        if (mInstance == null) {
            mInstance = new ToolbarTextSizePop(context);
        }
        return mInstance;
    }

    /**
     * 初始化菜单 pop
     */
    @SuppressLint("WrongConstant")
    public void initMenuPop() {
        View menuPopView = mContext.getLayoutInflater().inflate(R.layout.pop_text_size_layout, null);
        rlvLayout = menuPopView.findViewById(R.id.rlv_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.HORIZONTAL);
        rlvLayout.setLayoutManager(layoutManager);
        List<TextSizeBean> beanList = new ArrayList<>();
        for (int i = 0; i < mTextSize.length; i++) {
            TextSizeBean sizeBean = new TextSizeBean();
            sizeBean.setTxtSize(mTextSize[i]);
            sizeBean.setSelect(false);
            beanList.add(sizeBean);
        }
        mSizeAdapter = new TextSizeAdapter(mContext, beanList);
        rlvLayout.setAdapter(mSizeAdapter);
        mSizeAdapter.setOnTextSizeClickListener(new TextSizeAdapter.OnTextSizeClickListener() {
            @Override
            public void onTextSizeClick(int position, TextSizeBean bean) {
                if (mPopClickListener != null) {
                    mPopClickListener.onTextSizeClick(bean.getTxtSize());
                }
                for (int i = 0; i < beanList.size(); i++) {
                    if (i == position) {
                        if (beanList.get(i).isSelect()) {
                            beanList.get(i).setSelect(false);
                        } else {
                            beanList.get(i).setSelect(true);
                        }
                    } else {
                        beanList.get(i).setSelect(false);
                    }
                }
                mSizeAdapter.notifyDataSetChanged();
            }
        });

        menuPop = new PopupWindow(menuPopView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuPop.setBackgroundDrawable(new BitmapDrawable());
        menuPop.setFocusable(true);
        menuPop.setOutsideTouchable(true);
        menuPop.update();
        menuPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mPopClickListener != null) {
                    mPopClickListener.onTextSizeDismiss();
                }
                Window window = mContext.getWindow();
                WindowManager.LayoutParams wl = window.getAttributes();
                wl.alpha = 1f;
                window.setAttributes(wl);
            }
        });
    }

    /**
     * 显示菜单 pop
     */
    public void showMenuPop(View view) {
        Window window = mContext.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.alpha = 0.6f;
        window.setAttributes(wl);
        menuPop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 设置显示状态
     *
     * @param value
     */
    public void setShowState(String value) {
        List<TextSizeBean> textSizes = mSizeAdapter.getTextSizes();
        for (int i = 0; i < textSizes.size(); i++) {
            if (textSizes.get(i).getTxtSize().equals(value)) {
                textSizes.get(i).setSelect(true);
            } else {
                textSizes.get(i).setSelect(false);
            }
        }
        mSizeAdapter.notifyDataSetChanged();
    }

    /**
     * 销毁
     */
    public void onDestroy() {
        menuPop = null;
        mInstance = null;
    }

}
