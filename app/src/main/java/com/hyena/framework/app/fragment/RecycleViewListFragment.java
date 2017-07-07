package com.hyena.framework.app.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.hyena.framework.app.adapter.SingleRecycleViewAdapter;
import com.hyena.framework.app.fragment.bean.UrlModelPair;
import com.hyena.framework.app.widget.RefreshableLayout;
import com.hyena.framework.app.widget.SimpleRecycleView;
import com.hyena.framework.datacache.BaseObject;
import com.hyena.framework.network.NetworkProvider;

import java.util.List;

/**
 * Created by yangzc on 16/10/24.
 */
public abstract class RecycleViewListFragment<T extends BaseUIFragmentHelper, K> extends BaseUIFragment<T> {

    private static final String TAG = "RecycleViewListFragment";

    protected SwipeRefreshLayout          mSrlPanel;
    protected SimpleRecycleView           mRecycleView;
    protected SingleRecycleViewAdapter<K> mRecycleAdapter;

    private boolean mHasMore = true;
    private boolean mIsAutoLoad = true;

    @Override
    public void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        addRefreshableLayout(true);
    }

    @Override
    public View onCreateViewImpl(Bundle savedInstanceState) {
        mSrlPanel = new SwipeRefreshLayout(getContext());
        mRecycleView = new SimpleRecycleView(getContext());
        mSrlPanel.addView(mRecycleView);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        return mSrlPanel;
    }

    @Override
    public void onViewCreatedImpl(View view, Bundle savedInstanceState) {
        super.onViewCreatedImpl(view, savedInstanceState);
        getRefreshLayout().setEnableRefresh(false);
        getRefreshLayout().setEnableLoadMore(false);
        getRefreshLayout().setRefreshListener(mRefreshPanelListener);
        mSrlPanel.setOnRefreshListener(mRefreshListener);
        mRecycleView.setOnLastItemVisibleListener(mLastVisibleListener);

        SingleRecycleViewAdapter<K> adapter = buildAdapter();
        mRecycleView.setAdapter(mRecycleAdapter = adapter);
    }

    private RefreshableLayout.OnRefreshListener mRefreshPanelListener
            = new RefreshableLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {}

        @Override
        public void onLoadMore() {
            if (isAutoLoad())//启动自动加载默认禁止手动加载
                return;

            if (!NetworkProvider.getNetworkProvider()
                    .getNetworkSensor().isNetworkAvailable()) {
                return;
            }
            if (hasMore()) {
                loadMore();
            } else {
                debug("At the end of the recycleView");
            }
        }
    };

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener
            = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            //load first page
            getRefreshLayout().setLoadingMore(false);
            refresh();
        }
    };

    private SimpleRecycleView.OnLastItemVisibleListener mLastVisibleListener
            = new SimpleRecycleView.OnLastItemVisibleListener() {
        @Override
        public void onLastItemVisible() {
            if (!isAutoLoad() || getRefreshLayout().isLoadingMore())//禁止自动加载
                return;

            if (!NetworkProvider.getNetworkProvider()
                    .getNetworkSensor().isNetworkAvailable()) {
                return;
            }
            if (hasMore()) {
                getRefreshLayout().setLoadingMore(true);
                loadMore();
            } else {
                debug("At the end of the recycleView");
            }
        }
    };

    public void refresh() {
        setHasMore(true);
        loadDefaultData(PAGE_FIRST);
    }

    public void loadMore() {
        loadDefaultData(PAGE_MORE);
    }

    @Override
    public void onPreAction(int action, int pageNo) {
        super.onPreAction(action, pageNo);
        if (action == ACTION_DEFAULT) {
            if (!isValid())
                return;
            if (pageNo == PAGE_FIRST) {
                if (isDataEmpty()) {
                    //first load
                    mSrlPanel.setRefreshing(false);
                } else {
                    //refresh
                    mSrlPanel.setRefreshing(true);
                    showContent();
                }
            } else {
                //load more
                mSrlPanel.setRefreshing(false);
                showContent();
            }
        }
    }

    @Override
    public UrlModelPair getRequestUrlModelPair(int action, int pageNo, Object... params) {
        return super.getRequestUrlModelPair(action, pageNo, params);
    }

    @Override
    public BaseObject onProcess(int action, int pageNo, Object... params) {
        return super.onProcess(action, pageNo, params);
    }

    @Override
    public void onGet(int action, int pageNo, BaseObject result, Object... params) {
        super.onGet(action, pageNo, result, params);
        if (action == ACTION_DEFAULT) {
            if (!isValid())
                return;
            //close refresh status
            mSrlPanel.setRefreshing(false);
            getRefreshLayout().setLoadingMore(false);

            List<K> dataList = getList(result);

            if (pageNo == PAGE_FIRST) {
                if (dataList != null && !dataList.isEmpty()) {
                    mRecycleAdapter.setItems(dataList);
                } else {
                    setHasMore(false);
                    showEmpty();
                }
            } else {
                if (dataList != null && !dataList.isEmpty()) {
                    mRecycleAdapter.addItems(dataList);
                } else {
                    setHasMore(false);
                }
            }
        }
    }

    @Override
    public void onGetCache(int action, int pageNo, BaseObject result) {
        if (action == ACTION_DEFAULT) {
            if (!isValid())
                return;
            if (pageNo == PAGE_FIRST) {
                List<K> dataList = getList(result);
                if (dataList != null && !dataList.isEmpty()) {
                    mRecycleAdapter.setItems(dataList);
                    super.onGetCache(action, pageNo, result);
                }
            }
        } else {
            super.onGetCache(action, pageNo, result);
        }
    }

    protected void showEmpty() {
        getEmptyView().showEmpty("", "暂无数据");
    }

    public void setHasMore(boolean hasMore){
        this.mHasMore = hasMore;
    }

    public boolean hasMore(){
        return mHasMore;
    }

    protected boolean isValid() {
        return mRecycleAdapter != null;
    }

    protected boolean isDataEmpty() {
        return mRecycleAdapter.getItemCount() == 0;
    }

    protected boolean isAutoLoad() {
        return mIsAutoLoad;
    }

    protected void setAutoLoad(boolean isAutoLoad) {
        this.mIsAutoLoad = isAutoLoad;
        //启动手动加载
        getRefreshLayout().setEnableLoadMore(!isAutoLoad);
    }

    /**
     * 构建要填充的适配器
     * @return 适配器
     */
    protected abstract SingleRecycleViewAdapter<K> buildAdapter();
    public abstract List<K> getList(BaseObject result);

    protected SimpleRecycleView buildRecycleView() {
        return new SimpleRecycleView(getContext());
    }
}
