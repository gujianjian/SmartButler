package com.example.joy.smartbutler.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.adapter.RobotAdapter;
import com.example.joy.smartbutler.entity.RobotData;
import com.example.joy.smartbutler.utils.L;
import com.example.joy.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2018/3/22.
 */

public class ButlerFragment extends Fragment implements View.OnClickListener {

    private ListView mListRobot;
    private RobotAdapter adapter;
    private List<RobotData> mListdata = new ArrayList<>();
    private Button btn_send;
    private EditText et_text;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mListRobot = view.findViewById(R.id.mListRobot);
        adapter=new RobotAdapter(getActivity(),mListdata);
        mListRobot.setAdapter(adapter);

        btn_send = view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        et_text = view.findViewById(R.id.et_text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                String text=et_text.getText().toString();
                String url="http://www.tuling123.com/openapi/api?key="+ StaticClass.ROBOT_key+"&info="+ URLEncoder.encode(text);
                if(!TextUtils.isEmpty(text)){
                    addRightItem(text);
                    et_text.setText("");
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            L.d(t);
                            if(!TextUtils.isEmpty(t)){
                                parseJson(t);
                            }
                        }
                    });

                }
                break;

        }
    }

    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            String text = jsonObject.getString("text");
            addLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addLeftItem(String text) {
        RobotData data=new RobotData();
        data.setText(text);
        data.setType(RobotData.VALUE_TEXT_LEFT);
        mListdata.add(data);
        adapter.notifyDataSetChanged();
        mListRobot.setSelection(mListdata.size());
    }

    private void addRightItem(String text) {
        RobotData data=new RobotData();
        data.setText(text);
        data.setType(RobotData.VALUE_TEXT_RIGHT);
        mListdata.add(data);
        adapter.notifyDataSetChanged();
        mListRobot.setSelection(mListdata.size());
    }
}
