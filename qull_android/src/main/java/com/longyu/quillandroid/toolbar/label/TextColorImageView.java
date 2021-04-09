package com.longyu.quillandroid.toolbar.label;

import android.content.Context;

import com.longyu.qull_android.R;

import com.longyu.quillandroid.Format;
import com.longyu.quillandroid.toolbar.ToolbarToggleImageView;

/**
 * @Author: com.longyu
 * @CreateDate: 2021/4/6 14:31
 * @Description:
 */
public class TextColorImageView extends ToolbarToggleImageView {

    public TextColorImageView(Context context) {
        super(context);
        setFormat(Format.COLOR);
        setWhitelistValues(new Object[]{true, false});
        setNormalState(R.drawable.ic_format_text_color);
    }

    @Override
    public boolean isChecked() {
        return getValue() != null && getValue().equals(true);
    }

    @Override
    public void clear(boolean emitEvent) {
        setValue(false, emitEvent);
    }

    @Override
    public void toggle(boolean emitEvent) {
        if (isChecked()) {
            setValue(false, emitEvent);
        } else {
            setValue(true, emitEvent);
        }
    }
}
