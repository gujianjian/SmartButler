package com.example.joy.test.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.joy.smartbutler.R;

/**
 * Created by joy on 2018/4/1.
 */

public class DialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        findViewById(R.id.btn__show_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerDialog dialog = new CustomerDialog(DialogActivity.this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
    }
}
