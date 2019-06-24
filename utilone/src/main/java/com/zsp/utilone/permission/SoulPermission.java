package com.zsp.utilone.permission;

import android.content.Context;

import com.qw.soul.permission.bean.Permission;
import com.zsp.utilone.toast.ToastUtils;

/**
 * Created on 2019/5/30.
 *
 * @author 郑少鹏
 * @desc SoulPermission
 */
public class SoulPermission {
    /**
     * 单权限被拒
     *
     * @param context    上下文
     * @param permission 权限
     */
    public void singlePermissionDenied(Context context, com.qw.soul.permission.bean.Permission permission) {
        if (permission.shouldRationale()) {
            ToastUtils.shortShow(context, permission.permissionName +
                    "\n you should show a explain for user then retry");
        } else {
            ToastUtils.shortShow(context, permission.permissionName +
                    "\n is refused you can not do next things");
        }
    }

    /**
     * 多权限被拒
     *
     * @param context 上下文
     */
    public void multiPermissionsDenied(Context context, Permission[] refusedPermissions) {
        ToastUtils.shortShow(context, refusedPermissions[0].toString() +
                "\n is refused you can not do next things");
    }
}
