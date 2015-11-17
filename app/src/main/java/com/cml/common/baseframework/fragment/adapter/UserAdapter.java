package com.cml.common.baseframework.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cml.common.baseframework.R;
import com.cml.common.baseframework.db.model.UserModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class UserAdapter extends ArrayAdapter<UserModel> {

    public UserAdapter(Context context) {
        super(context, -1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_user, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserModel userModel = getItem(position);

        holder.usernameView.setText(userModel.username);
        holder.passwrodView.setText(userModel.password);

        //加载网络图片
        Glide.with(getContext()).load(userModel.headIcon).crossFade().into(holder.headIcon);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.username)
        TextView usernameView;
        @Bind(R.id.password)
        TextView passwrodView;
        @Bind(R.id.headIcon)
        ImageView headIcon;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
