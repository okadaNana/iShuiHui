package com.lufficc.ishuhui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.lufficc.ishuhui.R;
import com.lufficc.ishuhui.adapter.ComicAdapter;
import com.lufficc.ishuhui.adapter.LoadMoreAdapter;
import com.lufficc.ishuhui.fragment.IView.IView;
import com.lufficc.ishuhui.fragment.presenter.CategoryFragmentPresenter;
import com.lufficc.ishuhui.model.ComicModel;
import com.lufficc.stateLayout.StateLayout;

import butterknife.BindView;
import retrofit2.Call;

public class CategoryFragment extends BaseFragment implements IView<ComicModel>, SwipeRefreshLayout.OnRefreshListener, LoadMoreAdapter.LoadMoreListener {
    private static final String CLASSIFY_ID = "CLASSIFY_ID";

    //ClassifyId   分类标识，0热血，1国产，2同人，3鼠绘
    public static final String CLASSIFY_ID_HOT = "0";
    public static final String CLASSIFY_ID_CHINA = "1";
    public static final String CLASSIFY_ID_SAME = "2";
    public static final String CLASSIFY_ID_MOUSE = "3";
    private String classifyId;
    @BindView(R.id.stateLayout)
    StateLayout stateLayout;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    CategoryFragmentPresenter presenter;
    ComicAdapter adapter;
    private int PageIndex = 0, prevPage;

    public static CategoryFragment newInstance(String classifyId) {
        CategoryFragment fragment = new CategoryFragment();
        String title = "热血";
        Bundle args = new Bundle();
        switch (classifyId) {
            case CLASSIFY_ID_HOT:
                title = "热血";
                break;
            case CLASSIFY_ID_CHINA:
                title = "国产";
                break;
            case CLASSIFY_ID_SAME:
                title = "同人";
                break;
            case CLASSIFY_ID_MOUSE:
                title = "鼠绘";
                break;
        }
        args.putString(CLASSIFY_ID, classifyId);
        fragment.setArguments(args);
        fragment.title = title;
        return fragment;
    }

    @Override
    public void initialize(Bundle savedInstanceState) {
        if (getArguments() != null) {
            classifyId = getArguments().getString(CLASSIFY_ID);
        }
        init();
    }

    private String title;

    @Override
    public String toString() {
        return title;
    }

    private void init() {
        stateLayout.setErrorAndEmptyAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        stateLayout.setInfoContentViewMargin(0,-256,0,0);
        presenter = new CategoryFragmentPresenter(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter = new ComicAdapter(getContext()));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        adapter.setLoadMoreListener(this);
        getData();
    }

    private void getData() {
        if (adapter.isDataEmpty())
            stateLayout.showProgressView();
        presenter.getData(classifyId, PageIndex);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_categoty;
    }

    @Override
    public void onSuccess(ComicModel comicModel) {
        stateLayout.showContentView();
        if (comicModel.Return.List.isEmpty()) {
            adapter.noMoreData();
        } else {
            if (PageIndex == 0) {
                adapter.setData(comicModel.Return.List);
            } else {
                adapter.addData(comicModel.Return.List);
            }
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailure(Call call, Throwable e) {
        if (adapter.isDataEmpty()) {
            stateLayout.showErrorView();
        } else {
            adapter.noMoreData("加载出错");
        }
    }

    @Override
    public void onRefresh() {
        adapter.noMoreData();
        prevPage = PageIndex;
        PageIndex = 0;
        if (adapter.isDataEmpty()) {
            stateLayout.showProgressView();
        }
        getData();
    }

    @Override
    public void onLoadMore() {
        prevPage = PageIndex;
        ++PageIndex;
        getData();
    }
}