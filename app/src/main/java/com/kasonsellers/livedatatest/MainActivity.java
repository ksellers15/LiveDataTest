package com.kasonsellers.livedatatest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kasonsellers.livedatatest.TaskFiles.Task;
import com.kasonsellers.livedatatest.TaskFiles.TaskAdapter;
import com.kasonsellers.livedatatest.TaskFiles.TaskViewModel;

import java.util.List;


public class MainActivity extends AppCompatActivity{

//    @BindView(R.id.rv)
    RecyclerView rv;
//    @BindView(R.id.button)
    Button button;

//    TaskRepository taskRepository;
    TaskAdapter mAdapter;
    private TaskViewModel taskViewModel;

    public LiveData<List<Task>> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        button = findViewById(R.id.button);
        mAdapter = new TaskAdapter(taskViewModel);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                mAdapter.submitList(tasks);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertFakeData();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START| ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                taskViewModel.delete(mAdapter.getTaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Task Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv);

        Log.e("---------", mAdapter.getItemCount()+ "");
    }


    public void insertFakeData(){
            Task task = new Task("I like turtles", 1);
            taskViewModel.insert(task);
            Task task2 = new Task("I like turtles too", 0);
            taskViewModel.insert(task2);
            Task task3 = new Task("nahh I like chickens", 0);
            taskViewModel.insert(task3);
    }

}
