package com.example.youdo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.youdo.Adapter.YouDoAdapter;
import com.example.youdo.Model.YouDoModel;
import com.example.youdo.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {
    private RecyclerView tasksRecyclerView;
    private YouDoAdapter taskAdapter;
    private List<YouDoModel> tasksList;
    private FloatingActionButton fab;
    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_screen);
        //getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        db.openDB();

        tasksList = new ArrayList<>();
        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new YouDoAdapter(db,this);
        tasksRecyclerView.setAdapter(taskAdapter);

        fab = findViewById(R.id.addTaskFloatingActionButton);
        tasksList = db.getAllTask();
        Collections.reverse(tasksList);
        taskAdapter.setTasks(tasksList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        tasksList = db.getAllTask();
        Collections.reverse(tasksList);
        taskAdapter.setTasks(tasksList);
        taskAdapter.notifyDataSetChanged();
    }
}