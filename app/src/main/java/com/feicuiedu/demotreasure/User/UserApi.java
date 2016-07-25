package com.feicuiedu.demotreasure.User;

import com.feicuiedu.demotreasure.User.Login.LoginResult;
import com.feicuiedu.demotreasure.User.Register.RegisterResult;
import com.feicuiedu.demotreasure.User.account.UpdataResult;
import com.feicuiedu.demotreasure.User.account.Update;
import com.feicuiedu.demotreasure.User.account.UploadResult;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Administrator on 2016/7/14.
 */
public interface UserApi {
    @POST("/Handler/UserHandler.ashx?action=register")
    Call<RegisterResult> register(@Body User user);

    @POST("/Handler/UserHandler.ashx?action=login")
    Call<LoginResult> login(@Body User user);

    // 头像上传(是一个多部分请求)
    @Multipart
    @POST("/Handler/UserLoadPicHandler1.ashx")
    Call<UploadResult> upload(@Part MultipartBody.Part part);

    // 更新头像
    @POST("/Handler/UserHandler.ashx?action=update")
    Call<UpdataResult> update(@Body Update update);




}
