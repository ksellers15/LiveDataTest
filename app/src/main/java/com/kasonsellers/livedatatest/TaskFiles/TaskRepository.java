package com.kasonsellers.livedatatest.TaskFiles;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;


public class TaskRepository {

//The repository establishes a layer of abstraction
// and provides the data from a single unified location***


    private static TaskRepository mTaskRepo;
    private TaskDatabase mDatabase;
    private TaskDao taskDao;

    public LiveData<List<Task>> allTasks;

    public TaskRepository(Context context){
        mDatabase = TaskDatabase.getInstance(context);
        taskDao = mDatabase.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public static synchronized TaskRepository getInstance(Context context){
        if(mTaskRepo == null){
            mTaskRepo = new TaskRepository(context);
        }
        return mTaskRepo;
    }

    public LiveData<List<Task>> getAllTasks(){
       return this.allTasks;
    }
//    public Task getTaskByID(int id){
//        return taskDao.getTaskById(id);
//    }
    public void insertTask(Task task){
        new InsertTaskAsyncTask(taskDao).execute(task);
    }
    public void deleteTask(Task task){
        new DeleteTaskAsyncTask(taskDao).execute(task);
    }
    public void deleteTaskById(int id){
        new DeleteByIDAsyncTask(taskDao).execute(id);
    }
    public void updateTask(Task task){
        new UpdateTaskAsyncTask(taskDao).execute(task);
    }

    private static class DeleteByIDAsyncTask extends  AsyncTask<Integer, Void, Void>{

        TaskDao dao;
        private DeleteByIDAsyncTask(TaskDao taskDao){
            dao = taskDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            dao.getTaskById(integers[0]);
            return null;
        }
    }
    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;
        private InsertTaskAsyncTask(TaskDao dao){
            taskDao = dao;
        }
        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insertTask(tasks[0]);
            return null;
        }
    }
    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;
        private DeleteTaskAsyncTask(TaskDao dao){
            taskDao = dao;
        }
        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.deleteTask(tasks[0]);
            return null;
        }
    }
    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void>{

        private TaskDao taskDao;
        private UpdateTaskAsyncTask(TaskDao dao){
            taskDao = dao;
        }
        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.updateTask(tasks[0]);
            return null;
        }
    }


}
