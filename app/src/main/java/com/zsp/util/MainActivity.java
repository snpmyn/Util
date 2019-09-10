package com.zsp.util;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zsp.utilone.intent.IntentUtils;
import com.zsp.utilone.permission.SoulPermissionUtils;
import com.zsp.utilone.toast.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import example.map.MapActivity;
import example.rxbus.RxBusActivity;
import example.storage.MmkvActivity;
import example.timber.TimberActivity;

/**
 * @decs: 主页
 * @author: 郑少鹏
 * @date: 2019/6/2 17:44
 */
public class MainActivity extends AppCompatActivity {
    private SoulPermissionUtils soulPermissionUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initConfiguration();
        execute();
    }

    private void initConfiguration() {
        soulPermissionUtils = new SoulPermissionUtils();
    }

    private void execute() {
        soulPermissionUtils.checkAndRequestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, soulPermissionUtils,
                true, new SoulPermissionUtils.CheckAndRequestPermissionCallBack() {
                    @Override
                    public void onPermissionOk() {

                    }

                    @Override
                    public void onPermissionDeniedNotRationaleInMiUi(String s) {
                        ToastUtils.shortShow(MainActivity.this, s);
                    }

                    @Override
                    public void onPermissionDeniedNotRationaleWithoutLoopHint(String s) {

                    }
                });
    }

    @OnClick({R.id.mainActivityMbTimber,
            R.id.mainActivityMbRxBus,
            R.id.mainActivityMbMap,
            R.id.mainActivityMbMmkv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // timber
            case R.id.mainActivityMbTimber:
                IntentUtils.jumpNoBundle(this, TimberActivity.class);
                break;
            // RxBus
            case R.id.mainActivityMbRxBus:
                IntentUtils.jumpNoBundle(this, RxBusActivity.class);
                break;
            // Map
            case R.id.mainActivityMbMap:
                IntentUtils.jumpNoBundle(this, MapActivity.class);
                break;
            // MMKV
            case R.id.mainActivityMbMmkv:
                IntentUtils.jumpNoBundle(this, MmkvActivity.class);
                break;
            default:
                break;
        }
    }
}
