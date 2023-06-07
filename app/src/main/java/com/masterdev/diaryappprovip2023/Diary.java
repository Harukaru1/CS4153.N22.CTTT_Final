package com.masterdev.diaryappprovip2023;

import com.google.firebase.Timestamp;

public class Diary {
    private String diaryImageUrl;
    private String diaryTopic;
    private Timestamp diaryDate;
    private String diaryMessage;

    public Diary() {
    }

    public Diary(String diaryImageUrl, String diaryTopic, Timestamp diaryDate, String diaryMessage) {
        this.diaryImageUrl = diaryImageUrl;
        this.diaryTopic = diaryTopic;
        this.diaryDate = diaryDate;
        this.diaryMessage = diaryMessage;
    }

    public String getDiaryImageUrl() {
        return diaryImageUrl;
    }

    public void setDiaryImage(String diaryImageUrl) {
        this.diaryImageUrl = diaryImageUrl;
    }

    public String getDiaryTopic() {
        return diaryTopic;
    }

    public void setDiaryTopic(String diaryTopic) {
        this.diaryTopic = diaryTopic;
    }

    public Timestamp getDiaryDate() {
        return diaryDate;
    }

    public void setDiaryDate(Timestamp diaryDate) {
        this.diaryDate = diaryDate;
    }

    public String getDiaryMessage() {
        return diaryMessage;
    }

    public void setDiaryMessage(String diaryMessage) {
        this.diaryMessage = diaryMessage;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "diaryImageUrl='" + diaryImageUrl + '\'' +
                ", diaryTopic='" + diaryTopic + '\'' +
                ", diaryDate=" + diaryDate +
                ", diaryMessage='" + diaryMessage + '\'' +
                '}';
    }
}
