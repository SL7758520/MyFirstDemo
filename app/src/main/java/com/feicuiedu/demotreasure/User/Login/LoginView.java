package com.feicuiedu.demotreasure.User.Login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/7/13.
 */
public interface LoginView extends MvpView {
    /** 显示登陆中的Loading视图 */
    void showProgress();
    /** 隐藏登陆中的Loading视图*/
    void hideProgress();
    /** 显示信息*/
    void showMessage(String msg);
    /** 导航到Home页面*/
    void navigateToHome();

}
