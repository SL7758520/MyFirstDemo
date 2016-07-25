package com.feicuiedu.demotreasure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.feicuiedu.demotreasure.User.Login.LoginActivity;
import com.feicuiedu.demotreasure.User.Register.RegisterActivity;
import com.feicuiedu.demotreasure.commons.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private ActivityUtils activityUtils;
    @Bind(R.id.btn_Login)
    Button btn_Login;
    @Bind(R.id.btn_Register)
    Button btn_Register;
    public  static final String KEY_FINISH="key_finish";
    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //获得广播后直接finish自己
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //过滤广播，得到自己需要的广播
        IntentFilter intentFilter=new IntentFilter(KEY_FINISH);
        //注册广播
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,intentFilter);

    }

    @OnClick({R.id.btn_Register, R.id.btn_Login})
    public void click(View v) {
    switch (v.getId()){
        case R.id.btn_Login:
            activityUtils.startActivity(LoginActivity.class);
            break;
        case R.id.btn_Register:
            activityUtils.startActivity(RegisterActivity.class);
            break;

    }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
