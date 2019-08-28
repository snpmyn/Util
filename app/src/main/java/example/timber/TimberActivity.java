package example.timber;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zsp.util.R;

import timber.log.Timber;

/**
 * @decs: timber页
 * @author: 郑少鹏
 * @date: 2019/7/28 16:19
 */
public class TimberActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timber);
        execute();
    }

    private void execute() {
        Timber.d("timber页：%s%s", "timber", "测试");
    }
}
