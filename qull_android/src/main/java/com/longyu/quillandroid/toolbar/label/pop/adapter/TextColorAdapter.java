package com.longyu.quillandroid.toolbar.label.pop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longyu.qull_android.R;

import java.util.ArrayList;
import java.util.List;

import com.longyu.quillandroid.toolbar.label.pop.bean.TextColorBean;

/**
 * @Author: com.longyu
 * @CreateDate: 2021/4/7 11:05
 * @Description:
 */
public class TextColorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;

    private List<TextColorBean> mTextColors = new ArrayList<>();

    public List<TextColorBean> getTextColors() {
        return mTextColors;
    }

    public TextColorAdapter(Activity activity, List<TextColorBean> list) {
        this.mActivity = activity;
        this.mTextColors = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TextSizeViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.pop_text_size_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TextSizeViewHolder) holder).setData(position);
    }

    @Override
    public int getItemCount() {
        return mTextColors.size() > 0 ? mTextColors.size() : 0;
    }

    public class TextSizeViewHolder extends RecyclerView.ViewHolder {

        private TextView textSize;

        public TextSizeViewHolder(@NonNull View itemView) {
            super(itemView);
            textSize = itemView.findViewById(R.id.tv_text_size);
        }

        public void setData(int position) {
            textSize.setText("" + mTextColors.get(position).getName());
            if (mTextColors.get(position).isSelect()) {
                textSize.setTextColor(mActivity.getResources().getColor(R.color.color_bfbfbf));
            } else {
                textSize.setTextColor(mActivity.getResources().getColor(R.color.white));
            }
            textSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnTextSizeClickListener != null) {
                        mOnTextSizeClickListener.onTextSizeClick(position, mTextColors.get(position));
                    }
                }
            });
        }
    }

    public interface OnTextColorClickListener {
        void onTextSizeClick(int position, TextColorBean bean);
    }

    private OnTextColorClickListener mOnTextSizeClickListener;

    public void setOnTextColorClickListener(OnTextColorClickListener mOnTextSizeClickListener) {
        this.mOnTextSizeClickListener = mOnTextSizeClickListener;
    }
}
