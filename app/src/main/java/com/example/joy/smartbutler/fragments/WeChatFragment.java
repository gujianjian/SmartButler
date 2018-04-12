package com.example.joy.smartbutler.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.adapter.WechatAdapter;
import com.example.joy.smartbutler.entity.WechatData;
import com.example.joy.smartbutler.ui.WebViewActivity;
import com.example.joy.smartbutler.utils.L;
import com.example.joy.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2018/3/22.
 */

public class WeChatFragment extends Fragment {

    private ListView lv_wechat;
    private WechatAdapter adapter;
    private List<WechatData> mList = new ArrayList<>();
    private List<String> mListTitle = new ArrayList<>();
    private List<String> mListUrl = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);
        findView(view);
        return view;

    }

    private void findView(View view) {
        lv_wechat = view.findViewById(R.id.lv_wechat);
        adapter = new WechatAdapter(getActivity(), mList);
        lv_wechat.setAdapter(adapter);
        lv_wechat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                L.d("position:"+i);
                String title = mListTitle.get(i);
                String url = mListUrl.get(i);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("url", url);
                WebViewActivity.createIntent(getActivity(),bundle);
            }
        });

        String url="http://v.juhe.cn/weixin/query?key="+ StaticClass.WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.d(t);
                parseJson(t);
            }
        });
    }

    private void parseJson(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray list = result.getJSONArray("list");
            for(int i=0;i<list.length();i++) {
                JSONObject obj = list.getJSONObject(i);
                String title = obj.getString("title");
                String source = obj.getString("source");
                String firstImg = obj.getString("firstImg");
                String url = obj.getString("url");
                WechatData data=new WechatData();
                data.setTitle(title);
                data.setSource(source);
                data.setFirstImg(firstImg);
                data.setUrl(url);
                mList.add(data);
                mListTitle.add(title);
                mListUrl.add(url);

            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
