package com.masterdev.diaryappprovip2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn_move_to_form_create_diary;
    private ImageView img_diary_logout;
    private ArrayList<Diary> diaryArrayList;
    private RecyclerView rc_diary_item;
    private DiaryAdapter diaryAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_move_to_form_create_diary = findViewById(R.id.btn_move_form_create_diary);
        btn_move_to_form_create_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMainActivity2CreateDiary = new Intent(MainActivity.this, CreatDiary.class);
                startActivity(intentMainActivity2CreateDiary);
            }
        });

        auth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        img_diary_logout = findViewById(R.id.img_diary_logout);

        img_diary_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intentMainActivity2Login = new Intent(MainActivity.this, Login.class);
                startActivity(intentMainActivity2Login);
            }
        });

        rc_diary_item = findViewById(R.id.rc_diary_item);
        diaryArrayList = new ArrayList<>();
        loadListDiaryFromFireStore();
        diaryAdapter = new DiaryAdapter(this, diaryArrayList);
        rc_diary_item.setAdapter(diaryAdapter);
        rc_diary_item.setLayoutManager(new LinearLayoutManager(this));
    }

    public void loadListDiaryFromFireStore() {
        db.collection("diary-" + auth.getUid()).orderBy("diaryDate", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(MainActivity.this, "List diary is empty!", Toast.LENGTH_SHORT).show();
                        } else {
                            List<Diary> diaryListFromFireStore = queryDocumentSnapshots.toObjects(Diary.class);
                            diaryArrayList.clear();
                            diaryArrayList.addAll(diaryListFromFireStore);
                            diaryAdapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "Load list diary complete!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Connect to FireStore failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}