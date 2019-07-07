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
     * @param context                     上下文
     * @param permissionName              权限名
     * @param soulPermissionUtils         SoulPermissionUtils
     * @param soulPermissionUtilsCallBack SoulPermissionUtilsCallBack
     * @param loopHint                    循提
     */
    public void checkAndRequestPermission(Context context,
                                          String permissionName,
                                          SoulPermissionUtils soulPermissionUtils,
                                          SoulPermissionUtilsCallBack soulPermissionUtilsCallBack,
                                          boolean loopHint) {
        SoulPermission.getInstance().checkAndRequestPermission(permissionName,
                // If you want do noting or no need all the callbacks, you may use SimplePermissionAdapter instead.
                new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        soulPermissionUtilsCallBack.onPermissionOk();
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        soulPermissionUtils.singlePermissionDenied(context, permission, soulPermissionUtilsCallBack, loopHint);
                    }
                });
    }

    /**
     * 单权限被拒
     *
     * @param context                     上下文
     * @param permission                  权限
     * @param soulPermissionUtilsCallBack SoulPermissionUtilsCallBack
     * @param loopHint                    循提
     */
    private void singlePermissionDenied(Context context,
                                        com.qw.soul.permission.bean.Permission permission,
                                        SoulPermissionUtilsCallBack soulPermissionUtilsCallBack,
                                        boolean loopHint) {
        String permissionNameDesc = permission.getPermissionNameDesc();
        String message = permissionNameDesc + "异常，前往设置->权限管理，打开" + permissionNameDesc + "。";
        if (permission.shouldRationale()) {
            ToastUtils.shortShow(context, message);
        } else {
            Activity activity = SoulPermission.getInstance().getTopActivity();
            if (null == activity) {
                return;
            }
            new MaterialAlertDialogBuilder(activity)
                    .setTitle("提示")
                    .setMessage(message)
                    .setPositiveButton("去设置", (dialogInterface, i) -> SoulPermission.getInstance().goApplicationSettings(data -> {
                        // If you need to know when back from app detail.
                        if (checkSinglePermission(permission.permissionName)) {
                            soulPermissionUtilsCallBack.onPermissionOk();
                            return;
                        }
                        if (loopHint) {
                            singlePermissionDenied(context, permission, soulPermissionUtilsCallBack, true);
                        }
                    })).show();
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
     * @param soulPermissionUtils         SoulPermissionUtils
     * @param soulPermissionUtilsCallBack SoulPermissionUtilsCallBack
     * @param loopHint                    循提
     * @param permissionNames             权限名
     */
    public void checkAndRequestPermissions(SoulPermissionUtils soulPermissionUtils,
                                           SoulPermissionUtilsCallBack soulPermissionUtilsCallBack,
                                           boolean loopHint,
                                           String... permissionNames) {
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(permissionNames),
                // If you want do noting or no need all the callbacks, you may use SimplePermissionsAdapter instead.
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        soulPermissionUtilsCallBack.onPermissionOk();
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        soulPermissionUtils.multiPermissionsDenied(soulPermissionUtilsCallBack, loopHint, refusedPermissions);
                    }
                });
    }

    /**
     * 多权限被拒
     *
     * @param soulPermissionUtilsCallBack SoulPermissionUtilsCallBack
     * @param loopHint                    循提
     * @param refusedPermissions          被拒权限
     */
    private void multiPermissionsDenied(SoulPermissionUtilsCallBack soulPermissionUtilsCallBack,
                                        boolean loopHint,
                                        Permission[] refusedPermissions) {
        String[] permissionNameDescriptions = permissionNameDescriptions(refusedPermissions);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : permissionNameDescriptions) {
            stringBuilder.append(s).append("\n");
        }
        String message = "异常权限：\n" + stringBuilder + "前往设置->权限管理页面处理。";
        Activity activity = SoulPermission.getInstance().getTopActivity();
        if (null == activity) {
            return;
        }
        new MaterialAlertDialogBuilder(activity)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("去设置", (dialogInterface, i) -> SoulPermission.getInstance().goApplicationSettings(data -> {
                    // If you need to know when back from app detail.
                    if (checkPermissions(permissionNames(refusedPermissions))) {
                        soulPermissionUtilsCallBack.onPermissionOk();
                        return;
                    }
                    if (loopHint) {
                        multiPermissionsDenied(soulPermissionUtilsCallBack, true, refusedPermissions(refusedPermissions));
                    }
                })).show();
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
            if (permission.isGranted()) {
                break;
            }
            permissionNames.add(permission.permissionName);
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
            if (permission.isGranted()) {
                break;
            }
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
            if (checkSinglePermission(permission.permissionName)) {
                break;
            }
            permissions.add(permission);
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

    public interface SoulPermissionUtilsCallBack {
        /**
         * 权限可以
         */
        void onPermissionOk();
    }
}
