package com.example.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.apipractice_20200527.utils.ContextUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {
//        저장된 토큰이 있나? AND 자동로그인이 체크되어있나. => MainActivity로 이동
//        둘중 하나라도 틀리면 => LoginActivity로 이동.

//        이 행동을 2초후에 실행.

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!ContextUtil.getLoginUserToken(mContxt).equals("") && ContextUtil.isAutoLogin(mContxt)){
                    Intent myIntent = new Intent(mContxt,MainActivity.class);
                    startActivity(myIntent);
                }
                else {
                    Intent myIntent = new Intent(mContxt, LoginActivity.class);
                    startActivity(myIntent);
                }
                finish();


            }
        },2000);


    }
}
