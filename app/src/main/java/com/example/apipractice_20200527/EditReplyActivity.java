package com.example.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.apipractice_20200527.databinding.ActivityEditReplyBinding;
import com.example.apipractice_20200527.datas.Topic;
import com.example.apipractice_20200527.utils.ServerUtil;

import org.json.JSONObject;

public class EditReplyActivity extends BaseActivity {
    ActivityEditReplyBinding binding;
    Topic mainTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_reply);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {
        binding.postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                토픽의 주제?
                int topicId = mainTopic.getId();

//                선택한 진영?
                int selectedId = binding.sideRadioGroup.getCheckedRadioButtonId();

                if(selectedId == -1){
                    Toast.makeText(mContxt, "진영을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                String side = ((RadioButton)findViewById(selectedId)).getText().toString();
                Log.d("진영",side);

//                입력한 내용?

//                진영 미선택 / 내용이 10글자 이내면 등록 거부 처리

                if(binding.contentEdt.getText().length()<10){
                    Toast.makeText(mContxt, "의견은 10자 이상으로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String content = binding.contentEdt.getText().toString();

//                검사를 다 통과하면 서버에 요청.

                ServerUtil.postRequestTopic(mContxt, topicId, content, side, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("의견 남기기 응답",json.toString());

                    }
                });

            }
        });

    }

    @Override
    public void setValues() {

        mainTopic = (Topic)getIntent().getSerializableExtra("topic");
        binding.topicTitleTxt.setText(mainTopic.getTitle());

    }
}
