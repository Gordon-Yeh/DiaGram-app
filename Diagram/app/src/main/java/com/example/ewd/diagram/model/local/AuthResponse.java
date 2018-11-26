package com.example.ewd.diagram.model.local;

import com.example.ewd.diagram.model.local.entities.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResponse {

    @SerializedName("jwt")
    @Expose
    private String jwt;
    @SerializedName("user")
    @Expose
    private User user;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
