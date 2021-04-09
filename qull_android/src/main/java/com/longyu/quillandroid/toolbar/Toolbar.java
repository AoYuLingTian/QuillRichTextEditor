/**
 * Copyright (C) 2017 Pixel Dreamland LLC
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.longyu.quillandroid.toolbar;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.longyu.qull_android.R;

import java.lang.reflect.Constructor;
import java.util.*;

import com.longyu.quillandroid.Editor;
import com.longyu.quillandroid.Format;
import com.longyu.quillandroid.FormatSet;
import com.longyu.quillandroid.OnToolbarClickListener;
import com.longyu.quillandroid.ToolbarFormat;
import com.longyu.quillandroid.Util;
import com.longyu.quillandroid.toolbar.label.OrderlyImageView;
import com.longyu.quillandroid.toolbar.label.SizeImageView;
import com.longyu.quillandroid.toolbar.label.TextColorImageView;
import com.longyu.quillandroid.toolbar.label.TextImageView;
import com.longyu.quillandroid.toolbar.label.ImgImageView;
import com.longyu.quillandroid.toolbar.label.UnorderedImageView;

/**
 * Toolbar
 *
 * @author jkidi(Jakub Kidacki)
 */
public class Toolbar extends LinearLayout implements ToolbarElement.OnValueChangedListener {

    public static final ToolbarFormat[] ALL_TYPES = new ToolbarFormat[]{
            new ToolbarFormat(Format.IMAGE),
            new ToolbarFormat(Format.TEXT),
            new ToolbarFormat(Format.SIZE),
            new ToolbarFormat(Format.COLOR),
//            new ToolbarFormat(Format.LIST, new Object[]{"ordered"}),
//            new ToolbarFormat(Format.LIST, new Object[]{"bullet"})
            new ToolbarFormat(Format.ORDERED),
            new ToolbarFormat(Format.BULLET)
    };

    private LinearLayout containerLayout;

    private Map<Format, Class<? extends ToolbarElement>> formatClassMap;
    private ToolbarFormat[] toolbarFormats;
    private Map<Format, List<ToolbarElement>> toolbarElementMap;

    private Editor editor;

    private Context mContext;

    private OnToolbarClickListener mOnToolbarClickListener;

    public void setOnToolbarClickListener(OnToolbarClickListener mOnToolbarClickListener) {
        this.mOnToolbarClickListener = mOnToolbarClickListener;
    }

    public Toolbar(Context context) {
        super(context);
        this.mContext = context;
        init(null, 0);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs, 0);
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.toolbar_rich_editor, this);
        containerLayout = (LinearLayout) findViewById(R.id.containerLayout);
        ImageView ivFormatKeyboard = findViewById(R.id.iv_format_keyboard);
        ivFormatKeyboard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.hideKeyboard(v);
            }
        });

        toolbarFormats = ALL_TYPES;

        formatClassMap = new HashMap<>();
        formatClassMap.put(Format.IMAGE, ImgImageView.class);
        formatClassMap.put(Format.TEXT, TextImageView.class);
        formatClassMap.put(Format.SIZE, SizeImageView.class);
        formatClassMap.put(Format.COLOR, TextColorImageView.class);
        formatClassMap.put(Format.ORDERED, OrderlyImageView.class);
        formatClassMap.put(Format.BULLET, UnorderedImageView.class);

        initElements();
    }

    /**
     * 初始化编辑栏
     */
    private void initElements() {
        containerLayout.removeAllViews();
        toolbarElementMap = new HashMap<>();
        for (ToolbarFormat toolbarFormat : toolbarFormats) {
            Class<? extends ToolbarElement> cls = formatClassMap.get(toolbarFormat.getFormat());
            ToolbarElement element;
            try {
                Constructor<? extends ToolbarElement> constructor = cls.getConstructor(Context.class);
                element = constructor.newInstance(getContext());
                element.setFormat(toolbarFormat.getFormat());
                element.setOnValueChangedListener(this);
                if (toolbarFormat.getWhitelistValues() != null) {
                    element.setWhitelistValues(toolbarFormat.getWhitelistValues());
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
                params.weight = 1.0f;
                params.topMargin = 20;
                params.bottomMargin = 20;
                params.leftMargin = 20;
                params.rightMargin = 20;
                ((View) element).setLayoutParams(params);
                containerLayout.addView((View) element);
                List<ToolbarElement> elementList = toolbarElementMap.get(toolbarFormat.getFormat());
                if (elementList != null) {
                    elementList.add(element);
                } else {
                    elementList = new ArrayList<>();
                    elementList.add(element);
                    toolbarElementMap.put(toolbarFormat.getFormat(), elementList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        TextColorImageView childAt = (TextColorImageView) containerLayout.getChildAt(3);
        childAt.setTextColor(true);
    }

    private void resetButtons() {
        for (List<ToolbarElement> elementList : toolbarElementMap.values()) {
            for (ToolbarElement element : elementList) {
                element.clear(false);
            }
        }
    }

    @Override
    public void onValueChanged(ToolbarElement toolbarElement, Object value) {
        if (toolbarElement.getFormat().getName().equals(Format.IMAGE.getName())) {
            if (mOnToolbarClickListener != null) {
                mOnToolbarClickListener.onToolbarClick(toolbarElement.getFormat(), toolbarElement.getFormat().getName());
            }
        } else if (toolbarElement.getFormat().getName().equals(Format.TEXT.getName())) {
            if (mOnToolbarClickListener != null) {
                mOnToolbarClickListener.onToolbarTxtClick(this);
            }
        } else if (toolbarElement.getFormat().getName().equals(Format.SIZE.getName())) {
            if (mOnToolbarClickListener != null) {
                mOnToolbarClickListener.onToolbarTxtSizeClick(this);
            }
        } else if (toolbarElement.getFormat().getName().equals(Format.ORDERED.getName())) {
            OrderlyImageView orderlyImageView = (OrderlyImageView) containerLayout.getChildAt(4);
            UnorderedImageView childAt = (UnorderedImageView) containerLayout.getChildAt(5);
            childAt.setValue(false, false);
            if (orderlyImageView.getValue() != null && orderlyImageView.getValue().equals(false)) {
                editor.format(Format.LIST, false, null);
            } else {
                editor.format(Format.LIST, Format.ORDERED.getName(), null);
            }
        } else if (toolbarElement.getFormat().getName().equals(Format.BULLET.getName())) {
            UnorderedImageView unorderedImageView = (UnorderedImageView) containerLayout.getChildAt(5);
            if (unorderedImageView.getValue() != null && unorderedImageView.getValue().equals(false)) {
                editor.format(Format.LIST, false, null);
            } else {
                editor.format(Format.LIST, Format.BULLET.getName(), null);
            }
            OrderlyImageView childAt = (OrderlyImageView) containerLayout.getChildAt(4);
            childAt.setValue(false, false);
        } else if (toolbarElement.getFormat().getName().equals(Format.COLOR.getName())) {
            if (mOnToolbarClickListener != null) {
                mOnToolbarClickListener.onToolbarTxtColorClick(this);
            }
        } else if (toolbarElement.getFormat().getName().equals(Format.BACKGROUND.getName())) {
            editor.format(Format.BACKGROUND, "#00EEEE", null);
        } else {
            editor.format(toolbarElement.getFormat(), value, null);
        }
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(final Editor editor) {
        this.editor = editor;
        editor.addSelectionChangeListener(new Editor.SelectionChangeListener() {
            @Override
            public void onSelectionChange(int index, int length, int oldIndex, int oldLength, String source) {
                editor.getFormat(new ValueCallback<FormatSet>() {
                    @Override
                    public void onReceiveValue(FormatSet formatSet) {
                        resetButtons();
                        boolean isTextColor = false;
                        boolean isList = false;
                        for (Format format : formatSet.getFormats()) {
                            if (mOnToolbarClickListener != null) {
                                int position = -1;
                                String value = "";
                                if (format.getName().equals(Format.BOLD.getName())) {
                                    position = 0;
                                } else if (format.getName().equals(Format.ITALIC.getName())) {
                                    position = 1;
                                } else if (format.getName().equals(Format.UNDERLINE.getName())) {
                                    position = 2;
                                } else if (format.getName().equals(Format.STRIKE.getName())) {
                                    position = 3;
                                } else if (format.getName().equals(Format.SIZE.getName())) {
                                    position = 4;
                                    value = (String) formatSet.getValue(Format.SIZE);
                                } else if (format.getName().equals(Format.COLOR.getName())) {
                                    isTextColor = true;
                                    position = 5;
                                    value = (String) formatSet.getValue(Format.COLOR);
                                    setToolbarTextColor(value);
                                } else if (format.getName().equals(Format.LIST.getName())) {
                                    isList = true;
                                    String value1 = (String) formatSet.getValue(Format.LIST);
                                    if (value1.equals(Format.ORDERED.getName())) {
                                        setToolbarListState(0);
                                    } else {
                                        setToolbarListState(1);
                                    }
                                    break;
                                }
                                mOnToolbarClickListener.onToolbarState(position, true, value);
                            }
                        }
                        if (!isTextColor) {
                            setToolbarTextColor("");
                            mOnToolbarClickListener.onToolbarState(5, true, "");
                        } else {
                            isTextColor = false;
                        }
                        if (!isList) {
                            setToolbarListState(2);
                        } else {
                            isList = false;
                        }
                    }
                });
            }
        });
        // register fonts
        List<ToolbarElement> fontElements = toolbarElementMap.get(Format.FONT);
        if (fontElements != null) {
            Set<String> fonts = new LinkedHashSet<>();
            for (ToolbarElement element : fontElements) {
                for (Object value : element.getWhitelistValues()) {
                    fonts.add((String) value);
                }
            }
            if (fonts.size() > 0) {
                editor.registerFonts(fonts.toArray(new String[fonts.size()]));
            }
        }
    }

    /**
     * 设置  添加图片
     *
     * @param format
     * @param img
     */
    public void setImage(Format format, String img) {
        editor.format(format, "https://img0.baidu.com/it/u=1186596535,2310502619&fm=26&fmt=auto&gp=0.jpg", null);
//        editor.insertEmbed(10, format.getName(), "https://img2.baidu.com/it/u=2868088974,569311709&fm=26&fmt=auto&gp=0.jpg", null);
    }

    /**
     * 设置字体样式
     *
     * @param boo      是否选中
     * @param position 0：粗体 1：斜体 2：下划线 3：删除线
     */
    public void setTxtPosition(boolean boo, int position) {
        Format format = null;
        switch (position) {
            case 0:
                format = Format.BOLD;
                break;
            case 1:
                format = Format.ITALIC;
                break;
            case 2:
                format = Format.UNDERLINE;
                break;
            case 3:
                format = Format.STRIKE;
                break;
        }
        editor.format(format, boo, null);
    }

    /**
     * 设置toolbar状态
     *
     * @param position
     */
    public void setToolbarState(int position) {
        if (position == 0) {
            ImgImageView imageView = (ImgImageView) containerLayout.getChildAt(0);
            imageView.setValue(false, false);
        } else if (position == 1) {
            TextImageView childAt = (TextImageView) containerLayout.getChildAt(1);
            childAt.setValue(false, false);
        } else if (position == 2) {
            SizeImageView childAt = (SizeImageView) containerLayout.getChildAt(2);
            childAt.setValue(false, false);
        }
    }

    /**
     * 设置文字大小
     *
     * @param textSize
     */
    public void setTextSize(String textSize) {
        if ("normal".equals(textSize)) {
            editor.format(Format.SIZE, false, null);
        } else {
            editor.format(Format.SIZE, textSize, null);
        }
    }

    /**
     * 设置文字颜色
     *
     * @param textColor
     */
    public void setTextColor(String textColor) {
        setToolbarTextColor(textColor);
        if ("black".equals(textColor)) {
            editor.format(Format.COLOR, false, null);
        } else {
            editor.format(Format.COLOR, textColor, null);
        }
    }

    /**
     * 设置编辑栏文本颜色
     *
     * @param textColor
     */
    private void setToolbarTextColor(String textColor) {
        TextColorImageView childAt = (TextColorImageView) containerLayout.getChildAt(3);
        if ("black".equals(textColor)) {
            childAt.setCheckedColorFilter(0);
        } else if ("red".equals(textColor)) {
            childAt.setCheckedColorFilter(Color.RED);
        } else if ("orange".equals(textColor)) {
            childAt.setCheckedColorFilter(mContext.getResources().getColor(R.color.color_ffa500));
        } else if ("yellow".equals(textColor)) {
            childAt.setCheckedColorFilter(Color.YELLOW);
        } else if ("green".equals(textColor)) {
            childAt.setCheckedColorFilter(Color.GREEN);
        } else if ("blue".equals(textColor)) {
            childAt.setCheckedColorFilter(Color.BLUE);
        } else if ("purple".equals(textColor)) {
            childAt.setCheckedColorFilter(mContext.getResources().getColor(R.color.color_a020f0));
        } else {
            childAt.setCheckedColorFilter(0);
        }
    }

    /**
     * 设置编辑栏有序无序列表显示问题
     *
     * @param state 0：有序 1：无序 2：默认
     */
    private void setToolbarListState(int state) {
        if (state == 0) {
            OrderlyImageView childAt = (OrderlyImageView) containerLayout.getChildAt(4);
            childAt.setValue(true, false);
        } else if (state == 1) {
            UnorderedImageView childAt = (UnorderedImageView) containerLayout.getChildAt(5);
            childAt.setValue(true, false);
        } else {
            OrderlyImageView childAt = (OrderlyImageView) containerLayout.getChildAt(4);
            childAt.setValue(false, false);
            UnorderedImageView childAt1 = (UnorderedImageView) containerLayout.getChildAt(5);
            childAt1.setValue(false, false);
        }
    }

}
