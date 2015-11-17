package com.cml.common.baseframework.db.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by cmlBeliever on 2015/11/17.
 * 用户数据类
 */
@Table(name = "t_user")
public class UserModel extends Model {
    @Column(unique = true)
    public String username;
    @Column
    public String password;
}
