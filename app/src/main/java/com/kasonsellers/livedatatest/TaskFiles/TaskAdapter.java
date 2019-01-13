package com.kasonsellers.livedatatest.TaskFiles;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kasonsellers.livedatatest.R;

import java.util.List;
public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskAdapterViewHolder> {

    TaskViewModel viewModel;

    public TaskAdapter(TaskViewModel viewModel) {
        super(DIFF_CALLBACK);
        this.viewModel = viewModel;
    }


    private static DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task task, @NonNull Task otherTask) {
            return task.getId() == otherTask.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task task, @NonNull Task newTask) {
            return (task.getDescription().equals(newTask.getDescription()) &&
                    task.getComplete() == newTask.getComplete());
        }
    };




    public Task getTaskAt(int position){
        return getItem(position);
    }

    @NonNull
    @Override
    public TaskAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_rv_layout, viewGroup, false);
        return new TaskAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapterViewHolder taskViewHolder, int i) {
        final Task task = getItem(i);
        if(task == null)
            return;

        taskViewHolder.idTextView.setText(String.valueOf(task.getId()));
//
//        taskViewHolder.itemView.setTag(task.getId());

        taskViewHolder.descriptionTextView.setText(task.getDescription());

        if(task.getComplete() == 1)
            taskViewHolder.completeCheckBox.setChecked(true);


        taskViewHolder.completeCheckBox.setOnCheckedChangeListener(null);
//        new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if(isChecked){
//                        buttonView.setChecked(false);
//                    }else{
//                        buttonView.setChecked(true);
//                    }
//                    Toast.makeText(buttonView.getContext(), "This attribute cannot be edited here.", Toast.LENGTH_SHORT).show();
////                    Task oldTask = getTaskAt(getAdapterPosition());
////                    Task newTask;
////                    if(isChecked)
////                        newTask = new Task(oldTask.getDescription(), 1);
////                    else
////                        newTask = new Task(oldTask.getDescription(), 0);
////
////                    newTask.setId(oldTask.getId());
////                    viewModel.update(newTask);
//                }
//            });

    }


    public class TaskAdapterViewHolder extends RecyclerView.ViewHolder{

//        @BindView(R.id.task_id)
        TextView idTextView;
//        @BindView(R.id.task_description)
        TextView descriptionTextView;
//        @BindView(R.id.task_complete_checkbox)
        CheckBox completeCheckBox;

        public TaskAdapterViewHolder(@NonNull final View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.task_id);
            descriptionTextView = itemView.findViewById(R.id.task_description);
            completeCheckBox = itemView.findViewById(R.id.task_complete_checkbox);

        }
    }
}
