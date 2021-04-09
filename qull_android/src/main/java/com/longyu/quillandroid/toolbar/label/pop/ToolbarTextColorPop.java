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

import com.longyu.quillandroid.toolbar.label.pop.adapter.TextColorAdapter;
import com.longyu.quillandroid.toolbar.label.pop.bean.TextColorBean;

/**
 * @ClassName: ReportUtils
 * @Description:
 * @Author: com.longyu
 * @CreateDate: 2019/12/24 0024 14:31
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 * <p>  设置字体颜色
 */
public class ToolbarTextColorPop {

    private RecyclerView rlvLayout;

    private TextColorAdapter mColorAdapter;

    private PopupWindow menuPop;

    private Activity mContext;

    private static ToolbarTextColorPop mInstance;

    private OnTextColorClickListener mPopClickListener;

    private String[] mTextName = new String[]{"黑色", "红色", "橙色", "黄色", "绿色", "蓝色", "紫色"};
    private String[] mTextColorName = new String[]{"black", "red", "orange", "yellow", "green", "blue", "purple"};

    public ToolbarTextColorPop(Activity context) {
        this.mContext = context;
    }

    public void setOnTextColorClickListener(OnTextColorClickListener mOnMenuPopClickListener) {
        this.mPopClickListener = mOnMenuPopClickListener;
    }

    public interface OnTextColorClickListener {
        void onTextColorClick(String textColor);

        void onTextSizeDismiss();
    }

    /**
     * 单例
     *
     * @return
     */
    public static synchronized ToolbarTextColorPop getInstance(Activity context) {
        if (mInstance == null) {
            mInstance = new ToolbarTextColorPop(context);
        }
        return mInstance;
    }

    /**
     * 初始化菜单 pop
     */
    @SuppressLint("WrongConstant")
    public void initMenuPop() {
        View menuPopView = mContext.getLayoutInflater().inflate(R.layout.pop_text_color_layout, null);
        rlvLayout = menuPopView.findViewById(R.id.rlv_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayout.HORIZONTAL);
        rlvLayout.setLayoutManager(layoutManager);
        List<TextColorBean> beanList = new ArrayList<>();
        for (int i = 0; i < mTextName.length; i++) {
            TextColorBean colorBean = new TextColorBean();
            colorBean.setName(mTextName[i]);
            colorBean.setColorName(mTextColorName[i]);
            colorBean.setSelect(false);
            beanList.add(colorBean);
        }
        mColorAdapter = new TextColorAdapter(mContext, beanList);
        rlvLayout.setAdapter(mColorAdapter);
        mColorAdapter.setOnTextColorClickListener(new TextColorAdapter.OnTextColorClickListener() {
            @Override
            public void onTextSizeClick(int position, TextColorBean bean) {
                if (mPopClickListener != null) {
                    mPopClickListener.onTextColorClick(bean.getColorName());
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
                mColorAdapter.notifyDataSetChanged();
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
        List<TextColorBean> textSizes = mColorAdapter.getTextColors();
        for (int i = 0; i < textSizes.size(); i++) {
            if (textSizes.get(i).getColorName().equals(value)) {
                textSizes.get(i).setSelect(true);
            } else {
                textSizes.get(i).setSelect(false);
            }
        }
        mColorAdapter.notifyDataSetChanged();
    }

    /**
     * 销毁
     */
    public void onDestroy() {
        menuPop = null;
        mInstance = null;
    }

}
