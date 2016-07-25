package com.feicuiedu.demotreasure.Home.map;

import com.feicuiedu.demotreasure.Home.Treasure;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * 作者：yuanchao on 2016/7/19 0019 10:22
 * 邮箱：yuanchao@feicuiedu.com
 */
public interface MapMvpView extends MvpView{

    void showMessage(String msg);

    void setData(List<Treasure> datas);
}
