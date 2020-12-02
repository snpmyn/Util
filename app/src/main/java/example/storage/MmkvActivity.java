package example.storage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zsp.util.R;
import com.zsp.utilone.storage.mmkv.MmkvKit;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @decs: MMKV页
 * @author: 郑少鹏
 * @date: 2019/9/9 17:02
 */
public class MmkvActivity extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.mmkvActivityTvResult)
    TextView mmkvActivityTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmkv);
        ButterKnife.bind(this);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.mmkvActivityMbEncode,
            R.id.mmkvActivityMbRemoveBooleanValue,
            R.id.mmkvActivityMbRemoveIntValueAndLongValue,
            R.id.mmkvActivityMbClearMemoryCache,
            R.id.mmkvActivityMbDecode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 编码
            case R.id.mmkvActivityMbEncode:
                encode();
                break;
            // 移boolean值
            case R.id.mmkvActivityMbRemoveBooleanValue:
                MmkvKit.defaultMmkv().removeValueForKey("boolean");
                break;
            // 移int值和long值
            case R.id.mmkvActivityMbRemoveIntValueAndLongValue:
                MmkvKit.defaultMmkv().removeValuesForKeys(new String[]{"int", "long"});
                break;
            // 清内存缓存
            case R.id.mmkvActivityMbClearMemoryCache:
                MmkvKit.defaultMmkv().clearMemoryCache();
                break;
            // 解码
            case R.id.mmkvActivityMbDecode:
                decode();
                break;
            default:
                break;
        }
    }

    /**
     * 编码
     */
    private void encode() {
        MmkvKit.defaultMmkv().encode("boolean", true);
        MmkvKit.defaultMmkv().encode("int", Integer.MIN_VALUE);
        MmkvKit.defaultMmkv().encode("long", Long.MAX_VALUE);
        MmkvKit.defaultMmkv().encode("float", -3.14F);
        MmkvKit.defaultMmkv().encode("double", Double.MIN_VALUE);
        MmkvKit.defaultMmkv().encode("string", "hello from mmkv");
        MmkvKit.defaultMmkv().encode("bytes", new byte[]{'m', 'm', 'k', 'v'});
    }

    /**
     * 解码
     */
    private void decode() {
        boolean booleanValue = MmkvKit.defaultMmkv().decodeBool("boolean");
        int intValue = MmkvKit.defaultMmkv().decodeInt("int");
        long longValue = MmkvKit.defaultMmkv().decodeLong("long");
        float floatValue = MmkvKit.defaultMmkv().decodeFloat("float");
        double doubleValue = MmkvKit.defaultMmkv().decodeDouble("double");
        String stringValue = MmkvKit.defaultMmkv().decodeString("string");
        byte[] bytesValue = MmkvKit.defaultMmkv().decodeBytes("bytes");
        String result = "数量->" + "\n" +
                MmkvKit.defaultMmkv().count() + "\n\n" +
                "总体积->" + "\n" +
                MmkvKit.defaultMmkv().totalSize() + "\n\n" +
                "解码->" + "\n" +
                booleanValue + "\n" +
                intValue + "\n" +
                longValue + "\n" +
                floatValue + "\n" +
                doubleValue + "\n" +
                stringValue + "\n" +
                Arrays.toString(bytesValue) + "\n\n" +
                "所有键->" + "\n" +
                Arrays.toString(MmkvKit.defaultMmkv().allKeys()) + "\n\n" +
                "含String->" + "\n" +
                MmkvKit.defaultMmkv().containsKey("string");
        mmkvActivityTvResult.setText(result);
    }
}
