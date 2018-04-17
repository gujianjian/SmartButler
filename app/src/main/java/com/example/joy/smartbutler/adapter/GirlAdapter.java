package com.example.joy.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.entity.GirlData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by joy on 2018/4/17.
 */

public class GirlAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<GirlData> mListGrils;
    private WindowManager wm;
    private int width,height;

    public GirlAdapter(Context mContext,List<GirlData> mListGrils) {
        this.mContext=mContext;
        this.mListGrils=mListGrils;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width=wm.getDefaultDisplay().getWidth();
        height=wm.getDefaultDisplay().getHeight();
    }

    @Override
    public int getCount() {
        return mListGrils.size();
    }

    @Override
    public Object getItem(int position) {
        return mListGrils.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder;
        if (convertView == null) {
            viewholder=new Viewholder();
            convertView = mInflater.inflate(R.layout.layout_girl_item, null);
            viewholder.iv_girlimg = convertView.findViewById(R.id.iv_girlimg);
            convertView.setTag(viewholder);
        }else{
            viewholder= (Viewholder) convertView.getTag();
        }

        GirlData data = mListGrils.get(position);
        String url=data.getUrl();
        Picasso.with(mContext).load(url)
                .resize(width/2,height/5)
                .centerCrop()
                .into(viewholder.iv_girlimg);

        return convertView;
    }

    class Viewholder{
        public ImageView iv_girlimg;
    }
}
