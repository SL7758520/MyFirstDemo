package com.feicuiedu.demotreasure.User.Login;

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
import com.feicuiedu.demotreasure.R;
import com.feicuiedu.demotreasure.User.User;
import com.feicuiedu.demotreasure.User.UserPrefs;
import com.feicuiedu.demotreasure.commons.ActivityUtils;
import com.feicuiedu.demotreasure.commons.AlertDialogFragment;
import com.feicuiedu.demotreasure.commons.RegexUtils;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/13.
 */
public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_Password)
    EditText etPassword;
    @Bind(R.id.et_Username)
    EditText etUsername;
    @Bind(R.id.btn_Login)
    Button btnLogin;

    private String userName; // 用来存储用户名
    private String passWord; // 用来存储密码
    private UserPrefs userPrefs;

    private ActivityUtils activityUtils;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils=new ActivityUtils(this);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        // 用toolbar来更换以前的actionBar
        setSupportActionBar(toolbar);
        // 激活Home(左上角,内部使用的选项菜单处理的),设置其title
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getTitle());
        }
        if (userPrefs==null) {
            userPrefs=UserPrefs.getInstance();
        }
       String usetname= userPrefs.getKeyUsername();

        String userpassword =userPrefs.getKeyUserpassword();
        if(usetname!=null && userpassword!=null){
            etUsername.setText(usetname);
            etPassword.setText(userpassword);
            userName =etUsername.getText().toString().trim();
            passWord=etPassword.getText().toString().trim();
            boolean canLogin= !(TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord));
            // 默认情况下Login按钮是未激活，不可点的
            btnLogin.setEnabled(true);
        }
        etUsername.addTextChangedListener(mTextWatcher);
        etPassword.addTextChangedListener(mTextWatcher);

    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        //返回一个LoginPresenter对象
        return new LoginPresenter();
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

    private final TextWatcher mTextWatcher =new TextWatcher() {
        //在文本框改变之前调用（视图的改变）
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



        }
        //在文本框改变时调用
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        //在文本框改变之后调用（获取数据）
        @Override
        public void afterTextChanged(Editable editable) {
            userName =etUsername.getText().toString().trim();
            passWord=etPassword.getText().toString().trim();
            boolean canLogin= !(TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord));
            // 默认情况下Login按钮是未激活，不可点的
            btnLogin.setEnabled(canLogin);

        }


    };
    @OnClick(R.id.btn_Login)
    public void login() {
        // 用户名是否有效
        if (RegexUtils.verifyUsername(userName) != RegexUtils.VERIFY_SUCCESS) {
                showUsernameError();
            return;
        }
        // 密码是否有效
        if (RegexUtils.verifyPassword(passWord) != RegexUtils.VERIFY_SUCCESS) {
                showPasswordError();
            return;
        }
        // 执行业务

            getPresenter().login(new User(userName,passWord),this);
    }
    // 用户名输入错误Dialog
    private void showUsernameError(){
        String msg = getString(R.string.username_rules);
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(R.string.username_error, msg);
        fragment.show(getSupportFragmentManager(), "showUsernameError");
    }

    // 密码输入错误Dialog
    private void showPasswordError(){
        String msg = getString(R.string.password_rules);
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(R.string.password_error, msg);
        fragment.show(getSupportFragmentManager(), "showPasswordError");
    }

    private ProgressDialog progressDialog;

    @Override
    public void showProgress() {
        activityUtils.hideSoftKeyboard();
    progressDialog=ProgressDialog.show(this,"","正在登录中，请稍候...");
    }

    @Override
    public void hideProgress() {
        if(progressDialog!=null){
            progressDialog.dismiss();
        }

    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);

    }

    @Override
    public void navigateToHome() {
        // 切换到Home页面
activityUtils.startActivity(HomeActivity.class);
        //关闭当前页面
        finish();
        // 关闭入口Main页面 (发送一个广播出去,是本地广播)
        Intent intent =new Intent(MainActivity.KEY_FINISH);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    @Override protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
