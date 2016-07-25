package com.feicuiedu.demotreasure.User.Login;

import android.app.DownloadManager;
import android.content.Context;
import android.os.AsyncTask;

import com.feicuiedu.demotreasure.User.User;
import com.feicuiedu.demotreasure.User.UserApi;
import com.feicuiedu.demotreasure.User.UserPrefs;
import com.feicuiedu.demotreasure.net.netClient;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2016/7/12 0012.
 * <p/>
 * 登陆视图业务
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {
    private User user;
    private Context context;
    private Call<LoginResult> loginResultCall;
    /**
     * 本类核心业务
     */
    public void login(User user,Context context) {
        this.user=user;
        this.context=context;
        UserApi userApi=netClient.getInstance().getUserApi();
        if (loginResultCall!=null) loginResultCall.cancel();
        loginResultCall =userApi.login(user);
        loginResultCall.enqueue(callback);

    }
    private Callback<LoginResult> callback =new Callback<LoginResult>() {
        @Override
        public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
            getView().hideProgress();
            if (response.isSuccessful()){
                LoginResult result=response.body();
                if(result==null){
                    getView().showMessage("unknow error!");
                    return;
                }
                getView().showMessage(result.getMsg());
                if (result.getCode()==1){
                    UserPrefs userPrefs= UserPrefs.getInstance();
                    userPrefs.setKeyUsername(user.getUsername());
                    userPrefs.setKeyUserpassword(user.getPassword());
                    getView().navigateToHome();
                    return;
                }
                return;
            }
            getView().showMessage("网路异常！");
        }

        @Override
        public void onFailure(Call<LoginResult> call, Throwable t) {
            getView().hideProgress();
            getView().showMessage(t.getMessage());

        }
    };

//    private final class LoginTask extends AsyncTask<Void,Void,LoginResult>{
//        // 在doInBackground之前,UI线程来调用
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            getView().showProgress();
//        }
//        // 在onPreExecute之后, 后台线程来调用
//        @Override
//        protected LoginResult doInBackground(Void... voids) {
//            OkHttpClient client= netClient.getInstance().getClient();
//            String content =gson.toJson(user);
//            MediaType type = MediaType.parse("treasure/json");
//            RequestBody requestBody=RequestBody.create(type,content);
//            Request request = new Request.Builder().url("http://admin.syfeicuiedu.com/Handler/UserHandler.ashx?action=login")
//                    .post(requestBody)
//                    .build();
//            if(call!=null){
//                call.cancel();
//            }
//            call=client.newCall(request);
//            // 执行（同步）得到响应
//
//            try {
//                Response response = call.execute();
//                if (request==null){
//                    return null;
//                }
//                if (response.isSuccessful()){
//                    // 取出响应体中的数据(json字符串)
//                    String strResult =response.body().string();
//                    // gson处理一下
//                   LoginResult result = gson.fromJson(strResult,LoginResult.class);
//                    return result;
//                }
//            } catch (IOException e) {
//                return  null;
//            }
//            return null;
//        }
//        // 在doInBackground之后,UI线程来调用
//        @Override
//        protected void onPostExecute(LoginResult loginResult) {
//            super.onPostExecute(loginResult);
//            getView().hideProgress();
//            if (loginResult == null) {
//                getView().showMessage("未知错误!");
//                return;
//            }
//            getView().showMessage(loginResult.getMsg());
//            // 成功
//            if (loginResult.getCode() == 1) {
//                // 存住result里的一些重要数据
//                getView().navigateToHome();
//            }
//        }
//    }

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (loginResultCall != null) {
            loginResultCall.cancel();
        }
    }

}
