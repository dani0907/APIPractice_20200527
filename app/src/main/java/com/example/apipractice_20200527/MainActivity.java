package com.example.apipractice_20200527;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apipractice_20200527.databinding.ActivityMainBinding;
import com.example.apipractice_20200527.datas.Topic;
import com.example.apipractice_20200527.datas.TopicReply;
import com.example.apipractice_20200527.datas.User;
import com.example.apipractice_20200527.utils.ContextUtil;
import com.example.apipractice_20200527.utils.ServerUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    List<TopicReply> replyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(mContxt);
                alert.setTitle("로그아웃");
                alert.setMessage("정말 로그아웃 하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContextUtil.setLoginUserToken(mContxt,"");

                        Intent myIntent = new Intent(mContxt, LoginActivity.class);
                        startActivity(myIntent);

                        finish();
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContxt, "로그아웃을 취소했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();
            }
        });

    }

    @Override
    public void setValues() {

        ServerUtil.getRequestMainInfo(mContxt, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("메인화면응답", json.toString());

                try {
                    int code = json.getInt("code");
                    if(code ==200){
                        JSONObject data = json.getJSONObject("data");

                        JSONObject user = data.getJSONObject("user");

                        final User me = User.getUserFromJson(user);

                        JSONObject topic = data.getJSONObject("topic");

                        final Topic thisWeekTopic = Topic.getTopicFromJson(topic);

//                        댓글 목록도 파싱하자.

                        JSONArray replies = data.getJSONArray("replies");
                        for(int i= 0; i < replies.length(); i++){
                            JSONObject reply = replies.getJSONObject(i);

                            Log.d("댓글내용",reply.getString("content"));

                            replyList.add(TopicReply.getTopicReplyFromJson(reply));
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.nickNameTxt.setText(me.getNickName());
                                binding.emailTxt.setText(me.getEmail());

                                Glide.with(mContxt).load(thisWeekTopic.getImageUrl()).into(binding.topicImg);
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
