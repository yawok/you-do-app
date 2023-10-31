package com.example.youdo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youdo.MainActivity;
import com.example.youdo.Model.YouDoModel;
import com.example.youdo.R;

import java.util.List;

public class YouDoAdapter extends RecyclerView.Adapter<YouDoAdapter.ViewHolder> {
    private List<YouDoModel> todoList;
    private MainActivity activity;

    public YouDoAdapter(MainActivity activity) {
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
        YouDoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));

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
    @Override
    public int getItemCount() {
        return todoList.size();
    }
}
