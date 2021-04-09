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

import com.longyu.quillandroid.toolbar.label.pop.bean.TextSizeBean;

/**
 * @Author: com.longyu
 * @CreateDate: 2021/4/7 11:05
 * @Description:
 */
public class TextSizeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity mActivity;

    private List<TextSizeBean> mTextSizes = new ArrayList<>();

    public List<TextSizeBean> getTextSizes() {
        return mTextSizes;
    }

    public TextSizeAdapter(Activity activity, List<TextSizeBean> list) {
        this.mActivity = activity;
        this.mTextSizes = list;
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
        return mTextSizes.size() > 0 ? mTextSizes.size() : 0;
    }

    public class TextSizeViewHolder extends RecyclerView.ViewHolder {

        private TextView textSize;

        public TextSizeViewHolder(@NonNull View itemView) {
            super(itemView);
            textSize = itemView.findViewById(R.id.tv_text_size);
        }

        public void setData(int position) {
            textSize.setText("" + mTextSizes.get(position).getTxtSize());
            if (mTextSizes.get(position).isSelect()) {
                textSize.setTextColor(mActivity.getResources().getColor(R.color.color_bfbfbf));
            } else {
                textSize.setTextColor(mActivity.getResources().getColor(R.color.white));
            }
            textSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnTextSizeClickListener != null) {
                        mOnTextSizeClickListener.onTextSizeClick(position, mTextSizes.get(position));
                    }
                }
            });
        }
    }

    public interface OnTextSizeClickListener {
        void onTextSizeClick(int position, TextSizeBean bean);
    }

    private OnTextSizeClickListener mOnTextSizeClickListener;

    public void setOnTextSizeClickListener(OnTextSizeClickListener mOnTextSizeClickListener) {
        this.mOnTextSizeClickListener = mOnTextSizeClickListener;
    }
}
