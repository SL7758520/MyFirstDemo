package com.feicuiedu.demotreasure.User.Register;

import com.feicuiedu.demotreasure.User.User;
import com.feicuiedu.demotreasure.User.UserApi;
import com.feicuiedu.demotreasure.net.netClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/14.
 */
public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView> {
private Call<RegisterResult> resultCall;
    public void register(User user) {
        UserApi userApi = netClient.getInstance().getUserApi();
        if (resultCall!=null) resultCall.cancel();
        resultCall = userApi.register(user);
        resultCall.enqueue(callback);
    }

    private Callback<RegisterResult> callback = new Callback<RegisterResult>() {
        @Override
        public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
            getView().hideProgress();
            if (response.isSuccessful()) {
                RegisterResult registerResult = response.body();
                if (registerResult == null) {
                    getView().showMessage("unknow error!");
                    return;
                }
                getView().showMessage(registerResult.getMsg());
                if (registerResult.getCode() == 1) {
                    getView().navigateToHome();
                    return;
                }
                return;
            }
            getView().showMessage("网络异常!");
        }

        @Override
        public void onFailure(Call<RegisterResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.getMessage());
        }
    };

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (resultCall!=null){
            resultCall.cancel();
        }
    }
}
