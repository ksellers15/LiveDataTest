package com.kasonsellers.livedatatest.TaskFiles;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "task")
public class Task {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String description;

    @ColumnInfo(name = "is_complete")
    private int complete;

    public Task(String description, int complete){
        this.description = description;
        this.complete = complete;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getComplete() {
        return complete;
    }
}
