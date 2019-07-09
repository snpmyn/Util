package com.zsp.utilone.permission;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.zsp.utilone.miui.MiuiUtils;
import com.zsp.utilone.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/5/30.
 *
 * @author 郑少鹏
 * @desc SoulPermissionUtils
 */
public class SoulPermissionUtils {
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
     * 检查并请求单权限
     *
     * @param context                           上下文
     * @param permissionName                    权限名
     * @param soulPermissionUtils               SoulPermissionUtils
     * @param loopHint                          循提
     * @param checkAndRequestPermissionCallBack CheckAndRequestPermissionCallBack
     */
    public void checkAndRequestPermission(Context context,
                                          String permissionName,
                                          SoulPermissionUtils soulPermissionUtils,
                                          boolean loopHint,
                                          CheckAndRequestPermissionCallBack checkAndRequestPermissionCallBack) {
        SoulPermission.getInstance().checkAndRequestPermission(permissionName,
                // If you want do noting or no need all the callbacks, you may use SimplePermissionAdapter instead.
                new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        checkAndRequestPermissionCallBack.onPermissionOk();
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        soulPermissionUtils.singlePermissionDenied(context, permission, loopHint, checkAndRequestPermissionCallBack);
                    }
                });
    }

    /**
     * 单权限被拒
     *
     * @param context                           上下文
     * @param permission                        权限
     * @param loopHint                          循提
     * @param checkAndRequestPermissionCallBack CheckAndRequestPermissionCallBack
     */
    private void singlePermissionDenied(Context context,
                                        com.qw.soul.permission.bean.Permission permission,
                                        boolean loopHint,
                                        CheckAndRequestPermissionCallBack checkAndRequestPermissionCallBack) {
        String permissionNameDesc = permission.getPermissionNameDesc();
        String message = permissionNameDesc + "异常，前往设置->权限管理，打开" + permissionNameDesc + "。";
        if (permission.shouldRationale()) {
            ToastUtils.shortShow(context, message);
        } else {
            Activity activity = SoulPermission.getInstance().getTopActivity();
            if (null == activity) {
                return;
            }
            // MIUI避跳详情页设权限返应用重启
            if (MiuiUtils.isMiUi()) {
                checkAndRequestPermissionCallBack.onPermissionDeniedInMiUi(message);
                return;
            }
            // 其它对话框提示
            new MaterialAlertDialogBuilder(activity)
                    .setTitle("提示")
                    .setMessage(message)
                    .setPositiveButton("去设置", (dialogInterface, i) -> SoulPermission.getInstance().goApplicationSettings(data -> {
                        // If you need to know when back from app detail.
                        if (checkSinglePermission(permission.permissionName)) {
                            checkAndRequestPermissionCallBack.onPermissionOk();
                            return;
                        }
                        if (loopHint) {
                            singlePermissionDenied(context, permission, true, checkAndRequestPermissionCallBack);
                            return;
                        }
                        checkAndRequestPermissionCallBack.onPermissionDeniedWithoutLoopHint(message);
                    })).setCancelable(false).show();
        }
    }

    /**
     * 检查单权限
     *
     * @param permissionName 权限名
     * @return 已授予
     */
    private boolean checkSinglePermission(String permissionName) {
        Permission checkResult = SoulPermission.getInstance().checkSinglePermission(permissionName);
        return checkResult.isGranted();
    }

    /**
     * 检查并请求多权限
     *
     * @param soulPermissionUtils                SoulPermissionUtils
     * @param loopHint                           循提
     * @param checkAndRequestPermissionsCallBack CheckAndRequestPermissionsCallBack
     * @param permissionNames                    权限名
     */
    public void checkAndRequestPermissions(SoulPermissionUtils soulPermissionUtils,
                                           boolean loopHint,
                                           CheckAndRequestPermissionsCallBack checkAndRequestPermissionsCallBack,
                                           String... permissionNames) {
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(permissionNames),
                // If you want do noting or no need all the callbacks, you may use SimplePermissionsAdapter instead.
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        checkAndRequestPermissionsCallBack.onAllPermissionOk();
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        soulPermissionUtils.multiPermissionsDenied(loopHint, checkAndRequestPermissionsCallBack, refusedPermissions);
                    }
                });
    }

    /**
     * 多权限被拒
     *
     * @param loopHint                           循提
     * @param checkAndRequestPermissionsCallBack CheckAndRequestPermissionsCallBack
     * @param refusedPermissions                 被拒权限
     */
    private void multiPermissionsDenied(boolean loopHint,
                                        CheckAndRequestPermissionsCallBack checkAndRequestPermissionsCallBack,
                                        Permission[] refusedPermissions) {
        Activity activity = SoulPermission.getInstance().getTopActivity();
        if (null == activity) {
            return;
        }
        String[] permissionNameDescriptions = permissionNameDescriptions(refusedPermissions);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : permissionNameDescriptions) {
            stringBuilder.append(s).append("\n");
        }
        String message = "正常使用需授予以下权限：\n\n" + stringBuilder;
        // MIUI避跳详情页设权限返应用重启
        if (MiuiUtils.isMiUi()) {
            checkAndRequestPermissionsCallBack.onPermissionDeniedInMiUi(message);
            return;
        }
        // 其它对话框提示
        new MaterialAlertDialogBuilder(activity)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("去设置", (dialogInterface, i) -> SoulPermission.getInstance().goApplicationSettings(data -> {
                    // If you need to know when back from app detail.
                    if (checkPermissions(permissionNames(refusedPermissions))) {
                        checkAndRequestPermissionsCallBack.onAllPermissionOk();
                        return;
                    }
                    if (loopHint) {
                        multiPermissionsDenied(true, checkAndRequestPermissionsCallBack, refusedPermissions(refusedPermissions));
                        return;
                    }
                    checkAndRequestPermissionsCallBack.onPermissionDeniedWithoutLoopHint(message);
                })).setCancelable(false).show();
    }

    /**
     * 权限名
     *
     * @param permissions 权限
     * @return 权限名
     */
    private String[] permissionNames(Permission[] permissions) {
        List<String> permissionNames = new ArrayList<>(permissions.length);
        for (Permission permission : permissions) {
            if (!permission.isGranted()) {
                permissionNames.add(permission.permissionName);
            }
        }
        return permissionNames.toArray(new String[0]);
    }

    /**
     * 权限名描述
     *
     * @param permissions 权限
     * @return 权限名描述
     */
    private String[] permissionNameDescriptions(Permission[] permissions) {
        List<String> permissionNameDescriptions = new ArrayList<>();
        for (Permission permission : permissions) {
            permissionNameDescriptions.add(permission.getPermissionNameDesc());
        }
        return permissionNameDescriptions.toArray(new String[0]);
    }

    /**
     * 被拒权限
     *
     * @param refusedPermissions 被拒权限
     * @return 被拒权限
     */
    private Permission[] refusedPermissions(Permission[] refusedPermissions) {
        List<Permission> permissions = new ArrayList<>(refusedPermissions.length);
        for (Permission permission : refusedPermissions) {
            if (!checkSinglePermission(permission.permissionName)) {
                permissions.add(permission);
            }
        }
        return permissions.toArray(new Permission[0]);
    }

    /**
     * 检查多权限
     *
     * @param permissionNames 权限名
     * @return 已授予
     */
    private boolean checkPermissions(String... permissionNames) {
        boolean result = true;
        Permission[] checkResults = SoulPermission.getInstance().checkPermissions(permissionNames);
        for (Permission permission : checkResults) {
            if (!permission.isGranted()) {
                result = false;
            }
        }
        return result;
    }

    public interface CheckAndRequestPermissionCallBack {
        /**
         * 权限可以
         */
        void onPermissionOk();

        /**
         * MIUI权限被拒
         *
         * @param hint 提示
         */
        void onPermissionDeniedInMiUi(String hint);

        /**
         * 无循提权限被拒
         * <p>
         * 循提false有效。
         *
         * @param hint 提示
         */
        void onPermissionDeniedWithoutLoopHint(String hint);
    }

    public interface CheckAndRequestPermissionsCallBack {
        /**
         * 权限可以
         */
        void onAllPermissionOk();

        /**
         * MIUI权限被拒
         *
         * @param hint 提示
         */
        void onPermissionDeniedInMiUi(String hint);

        /**
         * 无循提权限被拒
         * <p>
         * 循提false有效。
         *
         * @param hint 提示
         */
        void onPermissionDeniedWithoutLoopHint(String hint);
    }
}
