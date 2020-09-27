package com.apps.project1024.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

public class Job implements Parcelable {
    private String jobId;
    private String title;
    private String company;
    private String category;
    private String industry;
    private String description;
    private String location;
    private boolean internship;
    private long datePosted;

    public Job() {}

    public Job(String jobId, String title, String company, String category, String industry, String description, String location, boolean internship, long datePosted) {
        this.jobId = jobId;
        this.title = title;
        this.company = company;
        this.category = category;
        this.industry = industry;
        this.description = description;
        this.location = location;
        this.internship = internship;
        this.datePosted = datePosted;
    }

    protected Job(Parcel in) {
        jobId = in.readString();
        title = in.readString();
        company = in.readString();
        category = in.readString();
        industry = in.readString();
        description = in.readString();
        location = in.readString();
        internship = in.readByte() != 0;
        datePosted = in.readLong();
    }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isInternship() {
        return internship;
    }

    public void setInternship(boolean internship) {
        this.internship = internship;
    }

    public void setDatePosted(long datePosted) {
        this.datePosted = datePosted;
    }

    public long getDatePosted() {
        return datePosted;
    }
    public String getTimeAgo() {
        PrettyTime p = new PrettyTime(Locale.getDefault());
        Date date = new Date(datePosted);
        return  p.format(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(jobId);
        parcel.writeString(title);
        parcel.writeString(company);
        parcel.writeString(category);
        parcel.writeString(industry);
        parcel.writeString(description);
        parcel.writeString(location);
        parcel.writeByte((byte) (internship ? 1 : 0));
        parcel.writeLong(datePosted);
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobId='" + jobId + '\'' +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", category='" + category + '\'' +
                ", industry='" + industry + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", internship=" + internship +
                ", datePosted=" + datePosted +
                '}';
    }
}
