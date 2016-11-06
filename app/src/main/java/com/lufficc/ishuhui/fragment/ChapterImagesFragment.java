package com.lufficc.ishuhui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lufficc.ishuhui.R;
import com.lufficc.ishuhui.activity.preview.ImagesActivity;
import com.lufficc.ishuhui.adapter.ChapterImagesAdapter;
import com.lufficc.ishuhui.manager.Orm;
import com.lufficc.ishuhui.model.ChapterImages;
import com.lufficc.ishuhui.utils.AppUtils;
import com.lufficc.lightadapter.OnDataClickListener;
import com.lufficc.stateLayout.StateLayout;

import butterknife.BindView;

public class ChapterImagesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnDataClickListener {

    @BindView(R.id.stateLayout)
    StateLayout stateLayout;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    ChapterImagesAdapter adapter;


    public static ChapterImagesFragment newInstance() {
        return new ChapterImagesFragment();
    }

    @Override
    public void initialize(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        stateLayout.setErrorAndEmptyAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        stateLayout.setInfoContentViewMargin(0, -256, 0, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter = new ChapterImagesAdapter(getActivity()));
        adapter.setOnDataClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        getData();
    }

    private void getData() {
        adapter.setData(Orm.getLiteOrm().cascade().query(ChapterImages.class));
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_categoty;
    }


    @Override
    public void onRefresh() {
        getData();
    }


    @Override
    public void onDataClick(int position, Object data) {
        ChapterImages chapterImages = (ChapterImages) data;
        ImagesActivity.showImages(getActivity(), AppUtils.fileEntry2ImageItem(chapterImages.getImages()));
    }
}