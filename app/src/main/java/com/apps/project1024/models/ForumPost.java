package com.apps.project1024.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ForumPost implements Parcelable {
    private String docKey;
    private String forum;
    private String title;
    private String body;
    private String photoUrl;
    private String userId;
    private long timestamp;

    public ForumPost() {
    }

    public ForumPost(String docKey, String forum, String title, String body, String photoUrl, String userId, long timestamp) {
        this.docKey = docKey;
        this.forum = forum;
        this.title = title;
        this.body = body;
        this.photoUrl = photoUrl;
        this.userId = userId;
        this.timestamp = timestamp;
    }


    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
    }

    public String getForum() {
        return forum;
    }

    public void setForum(String forum) {
        this.forum = forum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    @Override
    public String toString() {
        return "ForumPost{" +
                "docKey='" + docKey + '\'' +
                ", forum='" + forum + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", userId='" + userId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
