package com.example.joy.smartbutler.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.adapter.GirlAdapter;
import com.example.joy.smartbutler.entity.GirlData;
import com.example.joy.smartbutler.utils.L;
import com.example.joy.smartbutler.utils.StaticClass;
import com.example.joy.smartbutler.view.MyDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by joy on 2018/3/22.
 */

public class GirlFragment extends Fragment {

    private static final int GIRL_RESULT = 100;
    private GridView gv_girl;
    private GirlAdapter adapter;
    private List<GirlData> mListGirls = new ArrayList<>();
    private MyDialog myDialog;
    private PhotoView pv_img;

   public  Handler handler=new Handler(new Handler.Callback() {
       @Override
       public boolean handleMessage(Message msg) {
           switch (msg.what){
               case GIRL_RESULT:
                   parseJson(msg.obj.toString());
                   break;

           }
           return true;
       }
   });

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);
        initData();
        findView(view);

        return view;
    }

    private void findView(View view) {
        gv_girl = view.findViewById(R.id.mGridView);
        adapter = new GirlAdapter(getActivity(), mListGirls);
        gv_girl.setAdapter(adapter);
        gv_girl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GirlData data = mListGirls.get(position);
                Picasso.with(getActivity()).load(data.getUrl()).into(pv_img);

                myDialog.show();
            }
        });


    }



    private void initData() {
        myDialog = new MyDialog(getActivity(), 0, R.layout.layout_dialog_photo_item, Gravity.CENTER);
        pv_img = myDialog.findViewById(R.id.pv_img);


        String url = StaticClass.GIRL_URL;
        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.d("girl_error:"+e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                L.d("response:"+result);
                Message message=handler.obtainMessage();
                message.obj=result;
                message.what=GIRL_RESULT;
                handler.sendMessage(message);

            }
        });

    }

    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray results = jsonObject.getJSONArray("results");
            for(int i=0;i<results.length();i++) {
                JSONObject obj = results.getJSONObject(i);
                String url = obj.getString("url");
                GirlData data = new GirlData();
                data.setUrl(url);
                mListGirls.add(data);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
