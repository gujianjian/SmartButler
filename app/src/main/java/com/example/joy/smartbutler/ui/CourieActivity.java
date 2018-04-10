package com.example.joy.smartbutler.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.CorrectionInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.adapter.CourieAdapter;
import com.example.joy.smartbutler.entity.CourieData;
import com.example.joy.smartbutler.utils.L;
import com.example.joy.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by joy on 2018/4/10.
 */

public class CourieActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_company;
    private EditText et_no;
    private Button btn_query;
    private ListView lv_courie;

    private List<CourieData> mList = new ArrayList<>();
    private CourieAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courie);

        initView();
    }

    private void initView() {
        et_company = findViewById(R.id.et_company);
        et_no = findViewById(R.id.et_no);

        btn_query = findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);

        lv_courie = findViewById(R.id.lv_courie);
        adapter = new CourieAdapter(this, mList);
        lv_courie.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
                String company = et_company.getText().toString();
                String no = et_no.getText().toString();
                if (!TextUtils.isEmpty(company) && !TextUtils.isEmpty(no)) {
                    String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.COURIE_KEY + "&com=" + company + "&no=" + no;
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            L.d(t);
                            parseJson(t);
                        }
                    });
                } else {
                    System.out.println(Toast.makeText(CourieActivity.this, "输入框不能为空", Toast.LENGTH_SHORT));
                }
                break;
        }
    }

    //解析快递信息
    private void parseJson(String str) {
        try {
            JSONObject obj = new JSONObject(str);
            String resultcode = obj.getString("resultcode");
            if(!resultcode.equals("200")){
                Toast.makeText(this,"输入有误",Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject result = obj.getJSONObject("result");

            JSONArray listArray = result.getJSONArray("list");
            for (int i = 0; i < listArray.length(); i++) {
                JSONObject listdata = listArray.getJSONObject(i);
                String datetime = listdata.getString("datetime");
                String remark = listdata.getString("remark");
                String zone = listdata.getString("zone");
                CourieData courieData=new CourieData();
                courieData.setDatetime(datetime);
                courieData.setRemark(remark);
                courieData.setZone(zone);
                mList.add(courieData);
            }
            Collections.reverse(mList);
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //启动该activity的方法
    public static void createIntent(Context mContext) {
        mContext.startActivity(new Intent(mContext, CourieActivity.class));
    }
}
