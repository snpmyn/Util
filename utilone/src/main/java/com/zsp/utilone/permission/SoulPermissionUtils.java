package com.zsp.utilone.permission;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.zsp.utilone.toast.ToastUtils;

/**
 * Created on 2019/5/30.
 *
 * @author 郑少鹏
 * @desc SoulPermissionUtils
 */
public class SoulPermissionUtils {
    private SoulPermissionUtilsCallBack soulPermissionUtilsCallBack;

    /**
     * 初始
     * <p>
     * Android 4.0+支持。
     * <p>
     * SoulPermission内自动初始。
     * 用Tinker、腾讯乐固等替Application或致SoulPermission内初始失败于{@link Application#onCreate()}调init即可。
     * 设debug可看到错误日志打印和相关Toast。
     *
     * @param application 应用
     */
    public void init(Application application) {
        SoulPermission.init(application);
    }

    /**
     * 调试
     * <p>
     * 日志打印。
     *
     * @param debug 调试
     */
    public void setDebug(boolean debug) {
        SoulPermission.setDebug(debug);
    }

    /**
     * 单权限被拒
     *
     * @param context    上下文
     * @param permission 权限
     */
    public void singlePermissionDenied(Context context, com.qw.soul.permission.bean.Permission permission) {
        if (permission.shouldRationale()) {
            ToastUtils.shortShow(context, permission.permissionName + "\n you should show a explain for user then retry");
        } else {
            Activity activity = SoulPermission.getInstance().getTopActivity();
            if (null == activity) {
                return;
            }
            String permissionName = permission.getPermissionNameDesc();
            new MaterialAlertDialogBuilder(activity)
                    .setTitle("提示")
                    .setMessage(permissionName + "异常，请前往设置->权限管理，打开" + permissionName + "。")
                    .setPositiveButton("去设置", (dialogInterface, i) -> SoulPermission.getInstance().goApplicationSettings(data -> {
                        // If you need to know when back from app detail.
                        soulPermissionUtilsCallBack.goAppDetail();
                    })).show();
        }
    }

    /**
     * 多权限被拒
     *
     * @param context 上下文
     */
    public void multiPermissionsDenied(Context context, Permission[] refusedPermissions) {
        ToastUtils.shortShow(context, refusedPermissions[0].toString() + "\n is refused you can not do next things");
    }

    interface SoulPermissionUtilsCallBack {
        /**
         * 去应用详情
         */
        void goAppDetail();
    }

    public void setSoulPermissionUtilsCallBack(SoulPermissionUtilsCallBack soulPermissionUtilsCallBack) {
        this.soulPermissionUtilsCallBack = soulPermissionUtilsCallBack;
    }
}
