package com.example.joy.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.entity.CourieData;
import com.example.joy.smartbutler.ui.BaseActivity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by joy on 2018/4/10.
 */

public class CourieAdapter extends BaseAdapter {

    private Context mContext;
    private List<CourieData> mList;
    private LayoutInflater mInfalter;

    public CourieAdapter(Context mContext, List<CourieData> mList ) {
        this.mContext = mContext;
        this.mList = mList;
        mInfalter = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInfalter.inflate(R.layout.courie_item, null);
            viewHolder.tv_remark = view.findViewById(R.id.tv_remark);
            viewHolder.tv_datetime = view.findViewById(R.id.tv_datetime);
            viewHolder.tv_zone = view.findViewById(R.id.tv_zone);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        CourieData courieData = (CourieData) getItem(i);
        if(courieData!=null){
            viewHolder.tv_remark.setText(courieData.getRemark());
            viewHolder.tv_zone.setText(courieData.getZone());
            viewHolder.tv_datetime.setText(courieData.getDatetime());
        }

        return view;
    }

    class ViewHolder {
        public TextView tv_remark;
        public TextView tv_zone;
        public TextView tv_datetime;
    }
}
