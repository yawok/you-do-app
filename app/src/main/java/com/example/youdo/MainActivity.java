package com.example.youdo;

import android.os.Bundle;

import com.example.youdo.Adapter.YouDoAdapter;
import com.example.youdo.Model.YouDoModel;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView tasksRecyclerView;
    private YouDoAdapter taskAdapter;
    private List<YouDoModel> tasksList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_screen);
        //getSupportActionBar().hide();

        tasksList = new ArrayList<>();
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new YouDoAdapter(this);
        tasksRecyclerView.setAdapter(taskAdapter);

        YouDoModel task1 = new YouDoModel();
        task1.setTask("New random task");
        task1.setStatus(0);
        task1.setId(1);

        tasksList.add(task1);
        tasksList.add(task1);
        tasksList.add(task1);

        taskAdapter.setTasks(tasksList);
    }

}