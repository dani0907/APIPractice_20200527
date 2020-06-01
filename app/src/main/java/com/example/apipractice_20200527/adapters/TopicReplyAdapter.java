package com.example.apipractice_20200527.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apipractice_20200527.R;
import com.example.apipractice_20200527.datas.Topic;
import com.example.apipractice_20200527.datas.TopicReply;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

public class TopicReplyAdapter extends ArrayAdapter<TopicReply> {

    Context mContext;
    List<TopicReply> mList;
    LayoutInflater inf;

    public TopicReplyAdapter(@NonNull Context context, int resource, @NonNull List<TopicReply> objects) {
        super(context, resource, objects);
        mContext = context;
        mList = objects;
        inf = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if(row == null){
            row=inf.inflate(R.layout.topic_reply_list_item,null);
        }

        TextView contextTxt = row.findViewById(R.id.contentTxt);
        TextView writerNickNameTxt = row.findViewById(R.id.writerNickNameTxt);
        TextView sideTxt = row.findViewById(R.id.sideTxt);
        TextView createdAtTxt = row.findViewById(R.id.createdTxt);

        TopicReply data = mList.get(position);

        contextTxt.setText(data.getContent());
        writerNickNameTxt.setText(data.getWriter().getNickName());

        sideTxt.setText(data.getSide());

        if(data.getSide().equals("찬성")){
            sideTxt.setTextColor(Color.RED);
            sideTxt.setBackgroundResource(R.drawable.red_border_box);

        }
        else{

            sideTxt.setTextColor(Color.BLUE);
            sideTxt.setBackgroundResource(R.drawable.blue_border_box);
        }

//        언제 댓글을 남겼는지 표시.
//        댓글 남긴 시간을 파싱  => M월 d일 H시 m분



//        SimpleDateFormat sdf = new SimpleDateFormat("M월 d일 a h시 m분");
//        createdAtTxt.setText(sdf.format(data.getCreatedAt().getTime()));

        createdAtTxt.setText(data.getFormattedTimeAgo());


       return row;

    }
}
