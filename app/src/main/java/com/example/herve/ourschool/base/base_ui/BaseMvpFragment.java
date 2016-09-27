package com.example.herve.ourschool.base.base_ui;

import android.os.Bundle;
import android.util.Log;

import com.example.herve.ourschool.base.base_presenter.BasePresenter;

public abstract class BaseMvpFragment<V, P extends BasePresenter<V>> extends BaseFragment {
    protected static final String SUB_TYPE = "subtype";

    protected P mPresenter;
    private String TAG = getClass().getSimpleName();

    protected abstract P initPresenter();

    protected abstract void fetchData();

    protected boolean mIsViewInitiated;
    protected boolean mIsVisibleToUser;
    protected boolean mIsDataInitiated;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "setUserVisibleHint: " + isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        initFetchData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = initPresenter();
        mPresenter.attach((V) this);

        mIsViewInitiated = true;
        initFetchData();
    }

    protected void initFetchData() {
        if (mIsVisibleToUser && mIsViewInitiated && !mIsDataInitiated) {
            fetchData();
            mIsDataInitiated = true;
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }
}