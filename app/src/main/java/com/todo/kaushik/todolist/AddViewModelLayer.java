package com.todo.kaushik.todolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

public class AddViewModelLayer extends ViewModel
{

    private LiveData<TodoTask> todoTaskLiveData;

    public AddViewModelLayer(MyDatabase database, int taskId) {
        this.todoTaskLiveData = database.taskDao().loadTaskById(taskId);
    }


    public LiveData<TodoTask> getTodoTaskLiveData() {
        return todoTaskLiveData;
    }
}
