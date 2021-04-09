package com.longyu.quillandroid.toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.longyu.quillandroid.Format;

/**
 * @Author: com.longyu
 * @CreateDate: 2021/4/6 14:23
 * @Description: 按钮图标
 */
@SuppressLint("AppCompatCustomView")
public abstract class ToolbarToggleImageView extends ImageView implements ToolbarElement {

    protected Format format;
    protected Object value;
    protected Object[] whitelistValues;
    protected OnValueChangedListener onValueChangedListener;

    private @DrawableRes
    int normalState;
    private @DrawableRes
    int checkedState;
    private @ColorInt
    int normalColorFilter;
    private @ColorInt
    int checkedColorFilter;

    private boolean isTextColor = false;

    public void setTextColor(boolean textColor) {
        isTextColor = textColor;
    }

    public ToolbarToggleImageView(Context context) {
        super(context);
    }

    public ToolbarToggleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolbarToggleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Format getFormat() {
        return format;
    }

    @Override
    public void setFormat(Format format) {
        this.format = format;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value, boolean emitEvent) {
        this.value = value;
        if (!isTextColor) {
            update();
        }
        if (onValueChangedListener != null && emitEvent) {
            onValueChangedListener.onValueChanged(this, value);
        }
    }

    @Override
    public void setWhitelistValues(Object[] whitelistValues) {
        this.whitelistValues = whitelistValues;
    }

    @Override
    public Object[] getWhitelistValues() {
        return whitelistValues;
    }

    @Override
    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }
    public int getCheckedState() {
        return checkedState;
    }

    public void setCheckedState(int checkedState) {
        this.checkedState = checkedState;
        update();
    }

    public int getNormalColorFilter() {
        return normalColorFilter;
    }

    public void setNormalColorFilter(int normalColorFilter) {
        this.normalColorFilter = normalColorFilter;
        update();
    }

    public int getCheckedColorFilter() {
        return checkedColorFilter;
    }

    public void setCheckedColorFilter(int checkedColorFilter) {
        this.checkedColorFilter = checkedColorFilter;
        if (!isTextColor) {
            update();
        } else {
            setColorFilter(checkedColorFilter);
        }
    }

    public abstract boolean isChecked();

    public abstract void clear(boolean emitEvent);

    public abstract void toggle(boolean emitEvent);

    /**
     * 设置默认状态
     * @param normalState
     */
    public void setNormalState(int normalState) {
        this.normalState = normalState;
        update();
    }
    /**
     * 修改图标
     */
    private void update() {
        if (isChecked()) {
            if (checkedState != 0) {
                setImageResource(checkedState);
            }
            setColorFilter(checkedColorFilter);
        } else {
            setImageResource(normalState);
            setColorFilter(normalColorFilter);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.toggle(true);
        }
        return super.onTouchEvent(event);
    }

}
