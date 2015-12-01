package com.example.tancen.pullrefreshrecyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by classTC on 11/30/2015.Recycler的适配器
 */
public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_FOOTER = 1;

    private ArrayList<DataBean> dataList;

    private ProgressBar pbLoading;
    private TextView tvLoadMore;

    public RecyclerAdapter() {
        dataList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false));
        } else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_CONTENT) {
            DataBean bean = dataList.get(position);
            ((ContentViewHolder) holder).tvId.setText("" + bean.getId());
            ((ContentViewHolder) holder).tvName.setText(bean.getName());
        } else if (type == TYPE_FOOTER) {
            pbLoading = ((FooterViewHolder) holder).pbLoading;
            tvLoadMore = ((FooterViewHolder) holder).tvLoadMore;
        }
    }

    /**
     * 获取数据集加上一个footer的数量
     */
    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }
    }

    /**
     * 获取数据集的大小
     */
    public int getListSize() {
        return dataList.size();
    }

    /**
     * 将新增数据集添加到尾部,一般用作加载更多F
     */
    public void addAll(ArrayList<DataBean> list) {
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 将新增数据集添加到前面，一般用作刷新
     */
    public void addFirstAll(ArrayList<DataBean> list) {
        dataList.addAll(0, list);
        notifyDataSetChanged();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 内容的ViewHolder
     */
    public static class ContentViewHolder extends ViewHolder {
        private TextView tvId, tvName;

        public ContentViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tv_item_id);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_name);
        }
    }

    /**
     * footer的ViewHolder
     */
    public static class FooterViewHolder extends ViewHolder {
        private TextView tvLoadMore;
        private ProgressBar pbLoading;

        public FooterViewHolder(View itemView) {
            super(itemView);
            tvLoadMore = (TextView) itemView.findViewById(R.id.tv_item_footer_load_more);
            pbLoading = (ProgressBar) itemView.findViewById(R.id.pb_item_footer_loading);
        }
    }

    /**
     * 显示正在加载的进度条
     */
    public void showLoading() {
        if (pbLoading != null && tvLoadMore != null) {
            pbLoading.setVisibility(View.VISIBLE);
            tvLoadMore.setVisibility(View.GONE);
        }
    }

    /**
     * 显示上拉加载的文字
     */
    public void showLoadMore() {
        if (pbLoading != null && tvLoadMore != null) {
            pbLoading.setVisibility(View.GONE);
            tvLoadMore.setVisibility(View.VISIBLE);
        }
    }
}
