package com.example.daily_diary;


import java.util.Date;

public class Diary {
    private int diaryImg;
    private String diaryComment;
    private Date diaryDate;

    public Diary(int diaryImg, String diaryComment, Date diaryDate) {
        this.diaryImg = diaryImg;
        this.diaryComment = diaryComment;
        this.diaryDate = diaryDate;
    }

    public int getDiaryImg() {
        return diaryImg;
    }

    public void setDiaryImg(int diaryImg) {
        this.diaryImg = diaryImg;
    }

    public String getDiaryComment() {
        return diaryComment;
    }

    public void setDiaryComment(String diaryName) {
        this.diaryComment = diaryName;
    }

    public Date getDiaryDate() {
        return diaryDate;
    }

    public void setDiaryDate(Date diaryDate) {
        this.diaryDate = diaryDate;
    }
}