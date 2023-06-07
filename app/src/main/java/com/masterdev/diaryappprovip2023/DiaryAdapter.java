package com.masterdev.diaryappprovip2023;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.MyViewHolder> {
    private Context context;
    private List<Diary> diaryList;
    private int positionSelected = -1;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private int clickTimes = 0;

    public DiaryAdapter(Context context, List<Diary> diaryList) {
        this.context = context;
        this.diaryList = diaryList;
    }

    @NonNull
    @Override
    public DiaryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryAdapter.MyViewHolder holder, int position) {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Diary diary = diaryList.get(position);
        String imageUrl = diary.getDiaryImageUrl();
        holder.bind(imageUrl);

        holder.ed_diary_topic.setText(diary.getDiaryTopic());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.tv_diary_date.setText("Diary day - " + dateFormat.format(diary.getDiaryDate().toDate()));

        holder.edml_diary_message.setText(diary.getDiaryMessage());
        holder.img_diary_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("diary-" + auth.getUid())
                        .document(String.valueOf(diary.getDiaryDate().getSeconds()))
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context.getApplicationContext(), "Delete diary success!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context.getApplicationContext(), "Delete diary failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
                Intent intentDiaryAdapter2MainActivity = new Intent(context.getApplicationContext(), MainActivity.class);
                context.startActivity(intentDiaryAdapter2MainActivity);
            }
        });

        holder.img_diary_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickTimes == 0) {
                    holder.ed_diary_topic.setEnabled(true);
                    holder.edml_diary_message.setEnabled(true);
                    clickTimes = 1;
                    Toast.makeText(context.getApplicationContext(), "Edit mode is ON!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (clickTimes == 1) {
                    String newTopic = holder.ed_diary_topic.getText().toString();
                    String newMessage = holder.edml_diary_message.getText().toString();
                    Map<String, Object> diaryUpdates = new HashMap<>();
                    diaryUpdates.put("diaryTopic", newTopic);
                    diaryUpdates.put("diaryMessage", newMessage);
                    clickTimes = 0;
                    holder.ed_diary_topic.setEnabled(false);
                    holder.edml_diary_message.setEnabled(false);
                    db.collection("diary-" + auth.getUid())
                            .document(String.valueOf(diary.getDiaryDate().getSeconds()))
                            .update(diaryUpdates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context.getApplicationContext(), "Update diary successful - Edit mode is OFF!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context.getApplicationContext(), "Update diary failed - Edit mode is OFF!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_diary_view;
        private EditText ed_diary_topic;
        private TextView tv_diary_date;
        private EditText edml_diary_message;
        private ImageView img_diary_delete;
        private ImageView img_diary_edit;

        public MyViewHolder(@NonNull View view) {
            super(view);
            img_diary_view = view.findViewById(R.id.img_diary_image);
            ed_diary_topic = view.findViewById(R.id.ed_diary_topic);
            tv_diary_date = view.findViewById(R.id.tv_diary_date);
            edml_diary_message = view.findViewById(R.id.edml_diary_message);
            img_diary_delete = view.findViewById(R.id.img_diary_delete);
            img_diary_edit = view.findViewById(R.id.img_diary_edit);
        }
        public void bind(String imageUrl) {
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .into(img_diary_view);
        }
    }
}
