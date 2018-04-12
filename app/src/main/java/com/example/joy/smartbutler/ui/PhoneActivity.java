package com.example.joy.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joy.smartbutler.R;
import com.example.joy.smartbutler.entity.PhoneData;
import com.example.joy.smartbutler.utils.L;
import com.example.joy.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joy on 2018/4/11.
 */

public class PhoneActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_query, btn_del;
    private TextView tv_phoneInfo;
    private ImageView iv_phone;
    private EditText et_phone;
    private boolean flag=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        initView();
    }

    private void initView() {
        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_query = findViewById(R.id.btn_query);
        et_phone = findViewById(R.id.et_phone);
        btn_del = findViewById(R.id.btn_del);
        tv_phoneInfo = findViewById(R.id.tv_phoneinfo);
        iv_phone = findViewById(R.id.iv_phoneImg);

        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btn_del.setOnClickListener(this);

        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                et_phone.setText("");
                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        String str = et_phone.getText().toString();
        switch (view.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                et_phone.setText(str + ((Button) view).getText().toString());
                et_phone.setSelection(str.length() + 1);
                break;
            case R.id.btn_del:
                if (!TextUtils.isEmpty(str) && str.length() > 0) {
                    et_phone.setText(str.substring(0, str.length() - 1));
                    et_phone.setSelection(str.length() - 1);
                }
                break;
            case R.id.btn_query:
                if(flag){
                    et_phone.setText("");
                    flag=false;

                }
                String url = "http://apis.juhe.cn/mobile/get?phone=" + str + "&key=" + StaticClass.PHONE_KEY;
                RxVolley.get(url, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.d(t);
                        parseJson(t);
                    }
                });
                break;
            default:
                break;

        }
    }

    //解析归属地
    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject result = jsonObject.getJSONObject("result");
//            PhoneData phoneData=new PhoneData();
            String province = result.getString("province");
            String city = result.getString("city");
            String areacode = result.getString("areacode");
            String zip = result.getString("zip");
            String company = result.getString("company");
            String card = result.getString("card");

            switch (company) {
                case "移动":
                    iv_phone.setImageResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    iv_phone.setImageResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    iv_phone.setImageResource(R.drawable.china_telecom);
                    break;
            }

            tv_phoneInfo.setText("归属地：" + province + " " + city + "\n" +
                    "区号：" + areacode + "\n" +
                    "邮编：" + zip + "\n" +
                    "运营商：" + company + "\n" +
                    "类型：" + card);
//            phoneData.setProvince(province);
//            phoneData.setCity(city);
//            phoneData.setAreacode(areacode);
//            phoneData.setZip(zip);
//            phoneData.setCompany(company);
//            phoneData.setCard(card);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
