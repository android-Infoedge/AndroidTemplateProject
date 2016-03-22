package com.android.base.demo.network.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gagandeep on 22/3/16.
 */
public class User {

    @SerializedName("id")
    int mId;

    @SerializedName("name")
    String mName;

    public User(int id, String name ) {
        this.mId = id;
        this.mName = name;
    }
}
