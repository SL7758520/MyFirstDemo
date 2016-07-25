package com.feicuiedu.demotreasure.Home.list;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.demotreasure.Home.TreasureRepo;
import com.feicuiedu.demotreasure.R;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;


/**
 * 列表模式宝藏
 * <p/>
 * listView
 * TreasureRepo ----
 * <p/>
 * 5.0后,出的 RecylerView控件
 * <p/>
 * RecylerView控件 封装了ViewHolder,
 * LayoutManage,设定LAYOUT方式
 * 动画 (item,layout)
 * 对指定位置刷新
 * <p/>
 * 作者：yuanchao on 2016/7/22 0022 09:40
 * 邮箱：yuanchao@feicuiedu.com
 */
public class TreasureListFragment extends Fragment {
    RecyclerView recyclerView;
    TreasureLiastAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = new RecyclerView(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setItemAnimator(new ScaleInAnimator());
        recyclerView.setBackgroundResource(R.drawable.screen_bg);
        return recyclerView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TreasureLiastAdapter();
        recyclerView.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setItem(TreasureRepo.getInstance().getTreasure());
            }
        }, 50);
    }
}
