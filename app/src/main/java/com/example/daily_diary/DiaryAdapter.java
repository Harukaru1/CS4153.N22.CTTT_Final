package com.example.daily_diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Diary> diaryArrayList;

    public DiaryAdapter(Context context, ArrayList<Diary> diaryArrayList) {
        this.context = context;
        this.diaryArrayList = diaryArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Diary diary = diaryArrayList.get(position);
        holder.imageDiary.setImageResource(diary.getDiaryImg());
        holder.textDiaryComment.setText(diary.getDiaryComment());
        holder.textDiaryDatetime.setText(diary.getDiaryDate().toString());
    }

    @Override
    public int getItemCount() {
        return diaryArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageDiary;
        private TextView textDiaryComment;
        private TextView textDiaryDatetime;

        public MyViewHolder(@NonNull View view) {
            super(view);
            imageDiary = view.findViewById(R.id.imageDiary);
            textDiaryComment = view.findViewById(R.id.textDiaryComment);
            textDiaryDatetime = view.findViewById(R.id.textDiaryDatetime);
        }
    }

}