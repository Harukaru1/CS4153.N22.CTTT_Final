package com.masterdev.diaryappprovip2023;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreatDiary extends AppCompatActivity {
    private ImageView img_create_diary_image;
    private EditText ed_create_diary_topic;
    private EditText edml_create_diary_message;
    private Button btn_create_diary;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private Uri uri;

    public String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_diary);

        FirebaseApp.initializeApp(/*context=*/ this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance());

        auth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        // Put image to FirebaseStorage
        storage = FirebaseStorage.getInstance();

        img_create_diary_image = findViewById(R.id.img_create_diary_image);
        ed_create_diary_topic = findViewById(R.id.ed_diary_topic);
        edml_create_diary_message = findViewById(R.id.edml_create_diary_message);
        img_create_diary_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(CreatDiary.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
        btn_create_diary = findViewById(R.id.btn_create_diary);
        btn_create_diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewDiary();
//                finish();
                Intent intentCreateDiary2MainActivity = new Intent(CreatDiary.this, MainActivity.class);
                startActivity(intentCreateDiary2MainActivity);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        img_create_diary_image.setImageURI(uri);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            uploadImageToFirebase(uri);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadImageToFirebase(Uri uri) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedDateTime = now.format(formatter);
        String filename = "image_" + formattedDateTime+".jpg";
        String storagePath = "images/" + filename;
        StorageReference storageRef = storage.getReference().child(storagePath);

        UploadTask uploadTask = storageRef.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Image uploaded successfully
                // Get the download URL of the uploaded image
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageUrl = uri.toString();
                        // Do something with the download URL
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle unsuccessful uploads
            }
        });
    }

    public void createNewDiary() {
        String diaryTopic = ed_create_diary_topic.getText().toString();
        String diaryMessage = edml_create_diary_message.getText().toString();

        Timestamp currentDate = Timestamp.now();

        Diary diary = new Diary(this.imageUrl, diaryTopic, currentDate, diaryMessage);

        db.collection("diary-" + auth.getUid()).document(String.valueOf(currentDate.getSeconds()))
                .set(diary)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreatDiary.this, "Create diary success!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreatDiary.this, "Create diary failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}