package com.example.apipractice_20200527;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.apipractice_20200527.databinding.ActivityLoginBinding;
import com.example.apipractice_20200527.utils.ContextUtil;
import com.example.apipractice_20200527.utils.ServerUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.singUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContxt,SignUpActivity.class);
                startActivity(myIntent);
            }
        });

        binding.singUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContxt,SignUpActivity.class);
                startActivity(myIntent);
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.emailEdt.getText().toString();
                String password = binding.pwEdt.getText().toString();

                ServerUtil.postRequestLogin(mContxt, email, password, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("Json확인",json.toString());

                        try {
                            int code = json.getInt("code");

                            if(code ==200){
                                Log.d("분석결과","로그인 성공!");
                                JSONObject data = json.getJSONObject("data");
                                String token = data.getString("token");

                                ContextUtil.setLoginUserToken(mContxt, token);

                                Intent myIntent = new Intent(mContxt,MainActivity.class);
                                startActivity(myIntent);
                                finish();
                            }
                            else{
                                Log.d("분석결과","로그인 실패...");
                                final String failReason = json.getString("message");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContxt, failReason, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void setValues() {

    }
}
