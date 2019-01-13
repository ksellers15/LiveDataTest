package com.kasonsellers.livedatatest.TaskFiles;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


@Database(entities = {Task.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    //Create a static reference to the database and the abstract Dao
    private static TaskDatabase sDatabase;
    public abstract TaskDao taskDao();

    //This is the method to create the database. It follows the singleton pattern and
    // therefore must be synchronized to prevent initializing multiple instances
    public static synchronized TaskDatabase getInstance(Context context){
        if(sDatabase == null) {
            sDatabase = Room.databaseBuilder(context.getApplicationContext(), TaskDatabase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback) //This callback is added so data can be inputted upon the database's onCreate method seen below
                    .build();
        }
        return sDatabase;
    }

    //This is the custom RoomDatabase callback where we execute a custom AsyncTask
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(sDatabase).execute();

        }
    };

    //In this AsyncTask we input data into the database when it first gets initialized. We do this in an AsyncTask because
    //1 - room doesn't allow database ops on main ui thread and 2 - why not \_(-_-)_/
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        TaskDao taskDao;

        private PopulateDbAsyncTask(TaskDatabase db){
            taskDao = db.taskDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.insertTask(new Task("Go to the mall", 1));
            taskDao.insertTask(new Task("Finish next weeks assignment", 0));
            taskDao.insertTask(new Task("Complete this certification", 0));
            return null;
        }
    }
}
