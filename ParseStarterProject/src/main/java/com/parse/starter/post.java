package com.parse.starter;

import android.app.LauncherActivity;
import android.content.Context;

import java.util.List;

public class post
{
    private String username;
    private String caption;
    private String objectId;

    public post(List<LauncherActivity.ListItem> listItems, Context applicationContext) {
        this.username = username;
        this.caption = caption;
        this.objectId = objectId;
    }

    public String getUsername() {
        return username;
    }

    public String getCaption() {
        return caption;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
