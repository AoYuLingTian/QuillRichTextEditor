package com.longyu.quillandroid.toolbar.label.pop;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.longyu.qull_android.R;

/**
 * @ClassName: ReportUtils
 * @Description:
 * @Author: com.longyu
 * @CreateDate: 2019/12/24 0024 14:31
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 * <p>
 */
public class ToolbarTextPop {

    private ImageView ivBold;
    private ImageView ivItalic;
    private ImageView ivUnderlined;
    private ImageView ivStrikethrough;

    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isUnderlined = false;
    private boolean isStrikethrough = false;


    private PopupWindow menuPop;

    private Activity mContext;

    private static ToolbarTextPop mInstance;

    private OnMenuPopClickListener mOnMenuPopClickListener;

    public ToolbarTextPop(Activity context) {
        this.mContext = context;
    }

    public void setOnMenuPopClickListener(OnMenuPopClickListener mOnMenuPopClickListener) {
        this.mOnMenuPopClickListener = mOnMenuPopClickListener;
    }

    public interface OnMenuPopClickListener {
        void onMenuClick(boolean boo, int position);

        void onMenuDismiss();
    }

    /**
     * 单例
     *
     * @return
     */
    public static synchronized ToolbarTextPop getInstance(Activity context) {
        if (mInstance == null) {
            mInstance = new ToolbarTextPop(context);
        }
        return mInstance;
    }

    /**
     * 初始化菜单 pop
     */
    public void initMenuPop() {
        View menuPopView = mContext.getLayoutInflater().inflate(R.layout.pop_text_layout, null);
        ivBold = menuPopView.findViewById(R.id.iv_bold);
        ivItalic = menuPopView.findViewById(R.id.iv_italic);
        ivUnderlined = menuPopView.findViewById(R.id.iv_underlined);
        ivStrikethrough = menuPopView.findViewById(R.id.iv_strikethrough);

        ivBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuPopClickListener != null) {
                    if (!isBold) {
                        ivBold.setImageResource(R.drawable.ic_format_bold_default);
                    } else {
                        ivBold.setImageResource(R.drawable.ic_format_bold_white_24dp);
                    }
                    isBold = !isBold;
                    mOnMenuPopClickListener.onMenuClick(isBold, 0);
                }
            }
        });
        ivItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuPopClickListener != null) {
                    if (!isItalic) {
                        ivItalic.setImageResource(R.drawable.ic_format_italic_default);
                    } else {
                        ivItalic.setImageResource(R.drawable.ic_format_italic_white_24dp);
                    }
                    isItalic = !isItalic;
                    mOnMenuPopClickListener.onMenuClick(isItalic, 1);
                }
            }
        });
        ivUnderlined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuPopClickListener != null) {
                    if (!isUnderlined) {
                        ivUnderlined.setImageResource(R.drawable.ic_format_underlined_default);
                    } else {
                        ivUnderlined.setImageResource(R.drawable.ic_format_underlined_white_24dp);
                    }
                    isUnderlined = !isUnderlined;
                    mOnMenuPopClickListener.onMenuClick(isUnderlined, 2);
                }
            }
        });
        ivStrikethrough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuPopClickListener != null) {
                    if (!isStrikethrough) {
                        ivStrikethrough.setImageResource(R.drawable.ic_strikethrough_s_default);
                    } else {
                        ivStrikethrough.setImageResource(R.drawable.ic_strikethrough_s_white_24dp);
                    }
                    isStrikethrough = !isStrikethrough;
                    mOnMenuPopClickListener.onMenuClick(isStrikethrough, 3);
                }
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
                if (mOnMenuPopClickListener != null) {
                    mOnMenuPopClickListener.onMenuDismiss();
                }
                Window window = mContext.getWindow();
                WindowManager.LayoutParams wl = window.getAttributes();
                wl.alpha = 1f;
                window.setAttributes(wl);
            }
        });
    }

//    /**
//     * 重置
//     */
//    private void resetView() {
//        ivBold.setImageResource(R.drawable.ic_format_bold_white_24dp);
//        ivItalic.setImageResource(R.drawable.ic_format_italic_white_24dp);
//        ivUnderlined.setImageResource(R.drawable.ic_format_underlined_white_24dp);
//        ivStrikethrough.setImageResource(R.drawable.ic_strikethrough_s_white_24dp);
//    }


    /**
     * 设置状态
     *
     * @param position
     */
    public void setImageState(int position) {
        switch (position) {
            case 0:
                isBold = true;
                ivBold.setImageResource(R.drawable.ic_format_bold_default);
                break;
            case 1:
                isItalic = true;
                ivItalic.setImageResource(R.drawable.ic_format_italic_default);
                break;
            case 2:
                isUnderlined = true;
                ivUnderlined.setImageResource(R.drawable.ic_format_underlined_default);
                break;
            case 3:
                isStrikethrough = true;
                ivStrikethrough.setImageResource(R.drawable.ic_strikethrough_s_default);
                break;
        }
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
     * 销毁
     */
    public void onDestroy() {
        menuPop = null;
        mInstance = null;
    }

}
