package com.example.herve.ourschool.base.base_ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.herve.ourschool.base.base_presenter.BasePresenter;

public abstract class BaseMvpActivity<V, P extends BasePresenter<V>> extends BaseActivity {
    protected P mPresenter;

    protected abstract P initPresenter();

    protected abstract void fetchData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        mPresenter.attach((V) this);

        fetchData();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }
}