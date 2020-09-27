package com.apps.project1024.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {
    private String docKey;
    private String title;
    private String body;
    private String category;
    private String author;
    private boolean published;
    private String datePublished;
    private String imageUrl;
    private String videoUrl;

    public Article(){

    }

    public Article(String docKey, String title, String body, String category, String imageUrl, String videoUrl,  String author, boolean published, String datePublished) {
        this.docKey = docKey;
        this.title = title;
        this.body = body;
        this.category = category;
        this.author = author;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.published = published;
        this.datePublished = datePublished;
    }

    protected Article(Parcel in) {
        docKey = in.readString();
        title = in.readString();
        body = in.readString();
        category = in.readString();
        author = in.readString();
        published = in.readByte() != 0;
        datePublished = in.readString();
        imageUrl = in.readString();
        videoUrl = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getDocKey() {
        return docKey;
    }

    public void setDocKey(String docKey) {
        this.docKey = docKey;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }


    @Override
    public String toString() {
        return "Article{" +
                "docKey='" + docKey + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", category='" + category + '\'' +
                ", author='" + author + '\'' +
                ", published=" + published +
                ", datePublished='" + datePublished + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(docKey);
        parcel.writeString(title);
        parcel.writeString(body);
        parcel.writeString(category);
        parcel.writeString(author);
        parcel.writeByte((byte) (published ? 1 : 0));
        parcel.writeString(datePublished);
        parcel.writeString(imageUrl);
        parcel.writeString(videoUrl);
    }
}
