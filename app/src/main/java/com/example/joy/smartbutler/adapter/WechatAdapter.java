package com.example.joy.smartbutler.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.entity.WechatData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by joy on 2018/4/11.
 */

public class WechatAdapter extends BaseAdapter {

    private Context mContext;
    private List<WechatData> mList;
    private LayoutInflater mInflater;
    private WechatData wechatData;
    private WindowManager wm;
    private final int width;

    public WechatAdapter(Context mContext, List<WechatData> mList) {
        this.mContext=mContext;
        this.mList=mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
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
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view = mInflater.inflate(R.layout.wechat_item, null);
            viewHolder.iv_firstImg = view.findViewById(R.id.iv_firstImg);
            viewHolder.tv_title = view.findViewById(R.id.tv_title);
            viewHolder.tv_source = view.findViewById(R.id.tv_source);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }

        //暂时先不设置图片
        wechatData = mList.get(i);

        Picasso.with(mContext)
                .load(wechatData.getFirstImg())
                .resize(width/3, 200)
                .centerCrop()
                .into(viewHolder.iv_firstImg);

        viewHolder.tv_title.setText(wechatData.getTitle());
        viewHolder.tv_source.setText(wechatData.getSource());

        return view;
    }

    class ViewHolder{
        public ImageView iv_firstImg;
        public TextView tv_title;
        public TextView tv_source;
    }
}
