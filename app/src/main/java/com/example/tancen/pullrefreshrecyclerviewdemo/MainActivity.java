package com.example.tancen.pullrefreshrecyclerviewdemo;

import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        requestRefreshData();
    }

    private void initViews() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        layoutManager = new LinearLayoutManager(this);

        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);//设置刷新时进度条颜色，最多四种
        refreshLayout.setOnRefreshListener(this);

        mAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new OnRecyclerScrollListener());
    }

    /**
     * 用于下来刷新
     */
    @Override
    public void onRefresh() {

        requestRefreshData();
    }

    /**
     * 用于上拉加载更多
     */
    public class OnRecyclerScrollListener extends RecyclerView.OnScrollListener {

        int lastVisibleItem = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //如果滚动到底部，就获取更多的数据
            if (mAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {

                requestMoreData();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
    }

    /**
     * 模拟刷新请求
     */
    private void requestRefreshData() {
        refreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<DataBean> list = new ArrayList<>();
                for (int i = (int) (5 + Math.random() * 10); i >= 0; i--) {
                    DataBean bean = new DataBean();
                    bean.setId(i);
                    bean.setName("data_refresh_" + i);
                    list.add(bean);
                }
                mAdapter.clear();
                mAdapter.addFirstAll(list);
                refreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    /**
     * 模拟加载更多请求
     */
    private void requestMoreData() {
        mAdapter.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<DataBean> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    DataBean bean = new DataBean();
                    bean.setId(i);
                    bean.setName("data_load_more_" + i);
                    list.add(bean);
                }
                mAdapter.addAll(list);
                mAdapter.showLoadMore();
            }
        }, 3000);
    }
}
