package com.cml.common.baseframework.api.response;

import com.cml.common.baseframework.db.model.UserModel;

import java.util.List;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class UserApiResponse extends BaseApiResponse {
    private List<UserModel> users;

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }
}
