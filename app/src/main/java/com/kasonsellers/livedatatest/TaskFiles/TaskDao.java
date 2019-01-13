package com.kasonsellers.livedatatest.TaskFiles;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao  {

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM task WHERE id = :id")
    Task getTaskById(int id);

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM task")
    void deleteAllTasks();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Task task);
}
