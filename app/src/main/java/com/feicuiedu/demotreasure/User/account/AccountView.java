package com.feicuiedu.demotreasure.User.account;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/7/15.
 */
public interface AccountView extends MvpView{

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    /** 更新头像*/
    void updatePhoto(String url);
}
