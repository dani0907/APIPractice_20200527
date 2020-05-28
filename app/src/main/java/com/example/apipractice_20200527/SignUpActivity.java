package com.example.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.apipractice_20200527.databinding.ActivitySignUpBinding;
import com.example.apipractice_20200527.utils.ServerUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding binding;

    boolean idCheckOk = false;
    boolean nickCheckOk = false;

//    응용문제
//    비번은 타이핑할때마다 길이 검사
//    => 0글자 : 비밀번호를 입력해주세요.
//    => 8글자 미만 : 비밀번호가 너무 짧습니다.
//     그 이상 : 사용해도 좋은 비밀번호입니다.


//    비번 확인도 타이핑 할 때마다 검사.
//    => 0글자 : 비밀번호 확인을 입력해주세요.
//    => 비번과 같다 : 비밀번호 재입력이 확인 되었습니다
//    => 다르다 : 비밀번호가 서로 다릅니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                회원가입 => 이메일 / 비번 / 닉네임 입력값 받아오자.

                String email = binding.emailEdt.getText().toString();
                String pw = binding.pwEdt.getText().toString();
                String nickName = binding.nickEdt.getText().toString();

//                서버에 회원가입 기능 호출 => 가입 정보 전달 (ServerUtil 회원가입 기능 필요)

                ServerUtil.putRequestSignUp(mContxt, email, pw, nickName, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("회원가입응답",json.toString());
                    }
                });
            }
        });

        binding.nickEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nickCheckOk = false;
                binding.nickNameCheckResultTxt.setText("중복검사를 해주세요.");

                checkSignUpEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        닉네임 중복 확인 버튼 => 서버에 중복 확인 요청 (문서 참조)
//        => 성공일 경우 "사용해도 좋습니다" 토스트
//        => 실패일 경우 " 중복된 닉네임 입니다." 토스트

        binding.nickCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputNick = binding.nickEdt.getText().toString();

                ServerUtil.getRequestDuplicatedCheck(mContxt, inputNick, "NICKNAME", new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("로그확인", json.toString());

                        try {
                            int code = json.getInt("code");
                            if(code==200){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.nickNameCheckResultTxt.setText("사용해도 좋은 닉네임입니다.");
                                        nickCheckOk = true;
                                    }
                                });

                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.nickNameCheckResultTxt.setText("사용할 수 없는 닉네임입니다.");
                                    }
                                });
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    checkSignUpEnable();
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });


        binding.emailEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//                이메일을 변경하면 무조건 중복검사를 실패로 변경 => 재검사 요구
                idCheckOk=false;
                binding.idCheckResultTxt.setText("중복검사를 진행해주세요.");

//                버튼 비활성화 체크
                checkSignUpEnable();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.idCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputEmail = binding.emailEdt.getText().toString();

                ServerUtil.getRequestDuplicatedCheck(mContxt, inputEmail, "EMAIL", new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("중복응답확인",json.toString());

                        try {
                            int code = json.getInt("code");
                            if (code ==200){
//                                중복검사 통과
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContxt, "사용해도 좋은 아이디입니다.", Toast.LENGTH_SHORT).show();
                                        binding.idCheckResultTxt.setText("사용해도 좋은 아이디입니다.");

                                        idCheckOk = true;
                                    }
                                });
                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContxt, "중복검사에 통과하지 못했습니다.", Toast.LENGTH_SHORT).show();
                                        binding.idCheckResultTxt.setText("중복검사에 통과하지 못했습니다.");
                                    }
                                });
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    checkSignUpEnable();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
               });

            }
        });

        binding.pwEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkSignUpEnable();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.pwRepeatEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkSignUpEnable();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }

//    응용문제 => 비번 / 비번확인중 어느 것을 타이핑해도 매번 둘 다 검사.

    boolean checkPasswords(){

        boolean isPwOk = false;

        String pw = binding.pwEdt.getText().toString();

        if (pw.length()==0){
            binding.pwCheckResultTxt.setText("비밀번호를 입력해주세요.");
        }
        else if(pw.length()<8){
            binding.pwCheckResultTxt.setText("비밀번호가 너무 짧습니다.");
        }
        else{
            binding.pwCheckResultTxt.setText("사용해도 좋은 비밀번호입니다.");
            isPwOk = true;
        }


        boolean isPwRepeatOk = false;

        String pwRepeat = binding.pwRepeatEdt.getText().toString();

        if (pwRepeat.length()==0){
            binding.pwRepeatCheckResultTxt.setText("비밀번호 확인을 입력해주세요.");
        }
        else if(pwRepeat.equals(pw)){
            binding.pwRepeatCheckResultTxt.setText("비밀번호 재입력이 확인되었습니다.");
            isPwRepeatOk = true;
        }
        else{
            binding.pwRepeatCheckResultTxt.setText("비밀번호가 서로 다릅니다.");
        }

        return isPwOk && isPwRepeatOk;

    }


//    아이디 중복 / 비번확인 / 닉네임 중복이 모두 통과여야 회원가입 버튼을 활성화
//     하나라도 틀리면 회원가입 버튼 비활성화.
    void checkSignUpEnable(){

        boolean isAllPasswordOk = checkPasswords();

        binding.signUpBtn.setEnabled(isAllPasswordOk && idCheckOk && nickCheckOk);

    }

    @Override
    public void setValues() {

    }
}
