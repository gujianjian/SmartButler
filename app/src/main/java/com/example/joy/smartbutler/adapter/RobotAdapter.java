package com.example.joy.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.entity.RobotData;

import java.util.List;

/**
 * Created by joy on 2018/4/16.
 */

public class RobotAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<RobotData> mList;

    public RobotAdapter(Context mContext, List<RobotData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //一定不要忘记指定这个
    @Override
    public int getViewTypeCount() {
        return 3;//左边右边加linelayout
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LeftViewHolder leftViewHolder = null;
        RightViewHolder rightViewHolder = null;
        RobotData robotData = mList.get(i);
        int type = robotData.getType();
        switch (type) {
            case RobotData.VALUE_TEXT_LEFT:
                if (view == null) {
                    leftViewHolder = new LeftViewHolder();
                    view = mInflater.inflate(R.layout.layout_left_item, null);
                    leftViewHolder.tv_left = view.findViewById(R.id.tv_left);
                    view.setTag(leftViewHolder);
                } else {
                    leftViewHolder = (LeftViewHolder) view.getTag();
                }
                break;
            case RobotData.VALUE_TEXT_RIGHT:
                if (view == null) {
                    rightViewHolder = new RightViewHolder();

                    view = mInflater.inflate(R.layout.layout_right_item, null);
                    rightViewHolder.tv_right = view.findViewById(R.id.tv_right);
                    view.setTag(rightViewHolder);
                }else {
                    rightViewHolder = (RightViewHolder) view.getTag();
                }

                break;
        }


        switch (type) {
            case RobotData.VALUE_TEXT_LEFT:
                leftViewHolder.tv_left.setText(robotData.getText().toString());
                break;
            case RobotData.VALUE_TEXT_RIGHT:
                rightViewHolder.tv_right.setText(robotData.getText().toString());
                break;
        }
        return view;
    }


    class LeftViewHolder {
        public TextView tv_left;
    }

    class RightViewHolder {
        public TextView tv_right;
    }
}
