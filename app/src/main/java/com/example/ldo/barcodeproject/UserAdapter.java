package com.example.ldo.barcodeproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ldo.barcodeproject.R;
import com.example.ldo.barcodeproject.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    private LayoutInflater inflater;
    private int layout;
    private List<User> users;

    public UserAdapter(Context context, int resource, List<User> users) {
        super(context, resource, users);
        this.users = users;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        User user = users.get(position);

        viewHolder.loginView.setText(user.getLogin());

        return convertView;
    }
    private class ViewHolder {
        final TextView loginView;
        ViewHolder(View view){
            loginView = (TextView) view.findViewById(R.id.login);
        }
    }
}
