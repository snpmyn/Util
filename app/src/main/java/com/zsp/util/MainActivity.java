package com.zsp.util;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zsp.utilone.intent.IntentUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import example.TimberActivity;

/**
 * @decs: 主页
 * @author: 郑少鹏
 * @date: 2019/6/2 17:44
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.mainActivityMbTimber)
    public void onViewClicked(View view) {
        if (view.getId() == R.id.mainActivityMbTimber) {
            IntentUtils.jumpNoBundle(this, TimberActivity.class);
        }
    }
}
