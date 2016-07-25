package com.feicuiedu.demotreasure.User.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.feicuiedu.demotreasure.Home.HomeActivity;
import com.feicuiedu.demotreasure.MainActivity;
import com.feicuiedu.demotreasure.User.Login.LoginActivity;
import com.feicuiedu.demotreasure.User.User;
import com.feicuiedu.demotreasure.commons.ActivityUtils;
import com.feicuiedu.demotreasure.commons.AlertDialogFragment;
import com.feicuiedu.demotreasure.commons.RegexUtils;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.feicuiedu.demotreasure.R.*;

/**
 * 注册视图
 *
 * OkHttp库
 * 是我们使用的网络连接框架基栈技术
 *
 *
 */
public class RegisterActivity extends MvpActivity<RegisterView, RegisterPresenter> implements RegisterView {

    @Bind(id.toolbar) Toolbar toolbar;
    @Bind(id.et_Username) EditText etUsername;
    @Bind(id.et_Password) EditText etPassword;
    @Bind(id.et_Confirm) EditText etConfirm;
    @Bind(id.btn_Register) Button btnRegister;

    private String username; // 用来保存编辑框内的用户名
    private String password; // 用来保存编辑框内的密码

    private ActivityUtils activityUtils;// Activity常用工具集

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(layout.activity_register);
    }

    @Override public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        // 用toolbar来更换以前的actionBar
        setSupportActionBar(toolbar);
        // 激活Home(左上角,内部使用的选项菜单处理的),设置其title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getTitle());
        }
        etConfirm.addTextChangedListener(mTextWatcher); // EditText监听
        etPassword.addTextChangedListener(mTextWatcher); // EditText监听
        etUsername.addTextChangedListener(mTextWatcher); // EditText监听
    }

    @NonNull
    @Override public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    // 选项菜单处理
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            username = etUsername.getText().toString();
            password = etPassword.getText().toString();
            String confirm = etConfirm.getText().toString();
            boolean canRegister = !TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)
                    && password.equals(confirm);
            btnRegister.setEnabled(canRegister);// 注意：在布局内注册按钮默认是不可用的
        }
    };

    @OnClick(id.btn_Register)
    public void register() {
        activityUtils.hideSoftKeyboard();
        // 正则进行判断输入的用户名是否有效
        if (RegexUtils.verifyUsername(username) != RegexUtils.VERIFY_SUCCESS) {
            showUsernameError();
            return;
        }
        // 正则进行判断输入的密码是否有效
        if (RegexUtils.verifyPassword(password) != RegexUtils.VERIFY_SUCCESS) {
            showPasswordError();
            return;
        }
        // 执行注册业务逻辑
        getPresenter().register(new User(username,password));
    }

    // 用户名输入错误Dialog
    private void showUsernameError() {
        String msg = getString(string.username_rules);
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(string.username_error, msg);
        fragment.show(getSupportFragmentManager(), "showUsernameError");
    }

    // 密码输入错误Dialog
    private void showPasswordError() {
        String msg = getString(string.password_rules);
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(string.password_error, msg);
        fragment.show(getSupportFragmentManager(), "showPasswordError");
    }


    private ProgressDialog progressDialog;

    @Override public void showProgress() {
        progressDialog = ProgressDialog.show(this, "", "注册中,请稍后...");
    }

    @Override public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override public void navigateToHome() {
        activityUtils.startActivity(LoginActivity.class);
        finish();
        // 关闭入口Main页面 (发送一个广播出去,是本地广播)
        Intent intent = new Intent(MainActivity.KEY_FINISH);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
