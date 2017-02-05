package com.easyrest.framework.core.services.authentication.common;

/**
 * Created by liuhongyu.louie on 2016/10/9.
 */
public class PermissionRole {

    public enum PermissionRequired{

        GOD(0),

        BOSS(1),

        ADMINISTRATIVE(2),

        EMPLOYEE(3);

        int roleId;

        PermissionRequired(int roleId){
            this.roleId = roleId;
        }

        public int getRoleId(){
            return this.roleId;
        }

    }

}
