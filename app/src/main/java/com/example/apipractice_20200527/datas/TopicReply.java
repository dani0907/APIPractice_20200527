package com.example.apipractice_20200527.datas;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TopicReply {

    private int id;
    private String content;
    private String side;
    private User writer;
    private Calendar createdAt = Calendar.getInstance(); //작성 일시 기록.



    public static TopicReply getTopicReplyFromJson(JSONObject jsonObject){
        TopicReply tr = new TopicReply();
        try {
            tr.id=jsonObject.getInt("id");
            tr.content=jsonObject.getString("content");
            tr.side=jsonObject.getString("side");


//            댓글 json안에 있는 user Json 추출

            JSONObject user = jsonObject.getJSONObject("user");

//            이 user json으로 user클래스로 변환하는 기능 활용
//            활용해서 만든 User객체를 => 댓글의 작성자로 연결.
            tr.writer = User.getUserFromJson(user);
//            String으로 들어오는 created_at을 => Calender타입인 createdAt으로 변환.
            String createdAtStr = jsonObject.getString("created_at");
//            만들어져있는 createdAt캘린더에 => 년월일시 등 데이터 세팅.=>setTime이용

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            tr.createdAt.setTime(sdf.parse(createdAtStr));

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tr;
    }
    public TopicReply(){

    }

    public TopicReply(int id, String content, String side) {
        this.id = id;
        this.content = content;
        this.side = side;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }
}
