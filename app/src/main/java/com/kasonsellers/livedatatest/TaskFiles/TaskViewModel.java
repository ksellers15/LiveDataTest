package com.kasonsellers.livedatatest.TaskFiles;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mRepo;
    private LiveData<List<Task>> tasks;


    public TaskViewModel(@NonNull Application application) {
        super(application);
        mRepo = new TaskRepository(application);
        tasks = mRepo.getAllTasks();
    }

    public void insert(Task task){
        mRepo.insertTask(task);
    }

    public void delete(Task task){
        mRepo.deleteTask(task);
    }

    public void update(Task task){
        mRepo.updateTask(task);
    }

    public LiveData<List<Task>> getAllTasks(){
        return mRepo.getAllTasks();
    }

}
