package example.map;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zsp.util.R;
import com.zsp.utilone.map.MapUtils;
import com.zsp.utilone.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @decs: Map页
 * @author: 郑少鹏
 * @date: 2019/9/6 11:30
 */
public class MapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.mapActivityMbMapFromList)
    public void onViewClicked(View view) {
        if (view.getId() == R.id.mapActivityMbMapFromList) {
            List<String> list = new ArrayList<>(6);
            list.add("key1");
            list.add("value1");
            list.add("key2");
            list.add("value2");
            list.add("key3");
            list.add("value3");
            ToastUtils.shortShow(this, MapUtils.mapFromList(list).toString());
        }
    }
}
