package com.example.youdo.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youdo.AddNewTask;
import com.example.youdo.MainActivity;
import com.example.youdo.Model.YouDoModel;
import com.example.youdo.R;
import com.example.youdo.Utils.DatabaseHandler;

import java.util.List;

public class YouDoAdapter extends RecyclerView.Adapter<YouDoAdapter.ViewHolder> {
    private List<YouDoModel> todoList;
    private MainActivity activity;
    private DatabaseHandler db;


    public YouDoAdapter(DatabaseHandler db, MainActivity activity) {
        this.db = db;
        this.activity = activity;

    }
    @NonNull
    @Override
    public YouDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task, parent, false);
        return new ViewHolder(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.taskCheckBox);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull YouDoAdapter.ViewHolder holder, int position) {
        db.openDB();
        YouDoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    private boolean toBoolean(int n) {
        boolean value = true;
        if (n == 0) value = false;
        return value;
    }

    public void setTasks(List<YouDoModel> list) {
        this.todoList = list;
        notifyDataSetChanged();
    }

    public void editItem( int position) {
        YouDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
}
