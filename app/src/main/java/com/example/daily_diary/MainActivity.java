package com.example.daily_diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btnCreateDiary;

    private ArrayList<Diary> diaryArrayList;
    private RecyclerView recyclerView;
    private DiaryAdapter diaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateDiary = findViewById(R.id.btnCreateDiary);
        btnCreateDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khai báo Intent
                Intent intentMain2CreateDiary = new Intent(MainActivity.this, CreateDiary.class);
                startActivity(intentMain2CreateDiary);
            }
        });

        recyclerView = findViewById(R.id.recyclerDiary);
        diaryArrayList = new ArrayList<>();
        createDiaryList();
        diaryAdapter = new DiaryAdapter(this, diaryArrayList);
        recyclerView.setAdapter(diaryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createDiaryList() {
        Date currentDate = new Date();
        diaryArrayList.add(new Diary(R.drawable.meo_diary, "Beautiful picture today 1!", currentDate));
        diaryArrayList.add(new Diary(R.drawable.background, "Beautiful picture today 2!", currentDate));
        diaryArrayList.add(new Diary(R.drawable.background, "Beautiful picture today 3!", currentDate));
        diaryArrayList.add(new Diary(R.drawable.background, "Beautiful picture today 4!", currentDate));
    }
}