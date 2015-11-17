package com.cml.common.baseframework.db.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

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
    @Column
    public String headIcon;//头像地址

    /**
     * 批量更新或插入用户数据
     *
     * @param users
     * @return 受影响的记录数量
     */
    public static int insertOrUpdate(List<UserModel> users) {
        if (null == users || users.size() == 0) {
            return 0;
        }
        //开启事物批量插入
        ActiveAndroid.beginTransaction();
        int size = users.size();
        try {
            for (int i = 0; i < size; i++) {
                UserModel model = users.get(i);

                boolean exists = new Select().from(UserModel.class).where("username = ?", model.username).exists();

                if (exists) {
                    //相同数据删除
                    new Delete().from(UserModel.class).where("username = ?", model.username).execute();
                }
                model.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return size;
    }
}
