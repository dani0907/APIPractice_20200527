package com.example.apipractice_20200527;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.apipractice_20200527.databinding.ActivityEditReplyBinding;
import com.example.apipractice_20200527.datas.Topic;

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

    }

    @Override
    public void setValues() {

        mainTopic = (Topic)getIntent().getSerializableExtra("topic");
        binding.topicTitleTxt.setText(mainTopic.getTitle());

    }
}
