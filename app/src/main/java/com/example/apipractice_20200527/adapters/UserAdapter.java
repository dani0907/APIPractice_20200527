package com.example.apipractice_20200527.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.apipractice_20200527.R;
import com.example.apipractice_20200527.datas.User;

import org.w3c.dom.Text;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    Context mContext;
    List<User> mList;
    LayoutInflater inf;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
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
            row = inf.inflate(R.layout.user_list_item,null);
        }
        TextView nickNameTxt = row.findViewById(R.id.nickNameTxt);
        TextView emailTxt = row.findViewById(R.id.emailTxt);

        User data = mList.get(position);

        nickNameTxt.setText(data.getNickName());
        emailTxt.setText(String.format("(%s)",data.getEmail()));

        return row;
    }
}
