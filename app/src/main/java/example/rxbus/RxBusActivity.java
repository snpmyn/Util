package example.rxbus;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zsp.util.R;
import com.zsp.utilone.rxbus.RxBus;
import com.zsp.utilone.rxbus.annotation.Subscribe;
import com.zsp.utilone.rxbus.annotation.Tag;
import com.zsp.utilone.rxbus.thread.EventThread;
import com.zsp.utilone.toast.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import value.UtilRxBusConstant;

/**
 * @decs: RxBus页
 * @author: 郑少鹏
 * @date: 2019/8/28 14:05
 */
public class RxBusActivity extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);
        ButterKnife.bind(this);
        RxBus.get().register(this);
    }

    @OnClick({R.id.rxBusActivityRxBusTestOne, R.id.rxBusActivityRxBusTestTwo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // RxBus测试一
            case R.id.rxBusActivityRxBusTestOne:
                RxBus.get().post(UtilRxBusConstant.RXBUS_TEST_$_ONE, "1");
                break;
            // RxBus测试二
            case R.id.rxBusActivityRxBusTestTwo:
                RxBus.get().post(UtilRxBusConstant.RXBUS_TEST_$_TWO, 2);
                break;
            default:
                break;
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {@Tag(UtilRxBusConstant.RXBUS_TEST_$_ONE)})
    public void rxBusTestOne(String string) {
        // RxBus测试一
        ToastUtils.shortShow(this, string);
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {@Tag(UtilRxBusConstant.RXBUS_TEST_$_TWO)})
    public void rxBusTestTwo(Integer integer) {
        // RxBus测试二
        ToastUtils.shortShow(this, String.valueOf(integer));
    }
}
