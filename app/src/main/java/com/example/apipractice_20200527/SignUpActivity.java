package com.example.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.apipractice_20200527.databinding.ActivitySignUpBinding;

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding binding;

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

        binding.pwEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw = s.toString();

                if (pw.length()==0){
                    binding.pwCheckResultTxt.setText("비밀번호를 입력해주세요.");
                }
                else if(pw.length()<8){
                    binding.pwCheckResultTxt.setText("비밀번호가 너무 짧습니다.");
                }
                else{
                    binding.pwCheckResultTxt.setText("사용해도 좋은 비밀번호입니다.");
                }

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

                String pwRepeat = s.toString();

                if (pwRepeat.length()==0){
                    binding.pwRepeatCheckResultTxt.setText("비밀번호 확인을 입력해주세요.");
                }
                else if(pwRepeat.equals(binding.pwEdt.getText().toString())){
                    binding.pwRepeatCheckResultTxt.setText("비밀번호 재입력이 확인되었습니다.");
                }
                else{
                    binding.pwRepeatCheckResultTxt.setText("비밀번호가 서로 다릅니다.");
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void setValues() {

    }
}
