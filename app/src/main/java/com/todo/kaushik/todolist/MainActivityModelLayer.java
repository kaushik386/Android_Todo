package com.todo.kaushik.todolist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import java.util.List;

public class MainActivityModelLayer extends AndroidViewModel {

    LiveData<List<TodoTask>> todos;

    public MainActivityModelLayer(@NonNull Application application) {
        super(application);

        MyDatabase myDatabase = MyDatabase.getInstance(application);
        todos = myDatabase.taskDao().getList();
    }


    public LiveData<List<TodoTask>> gettodos() {
        return todos;
    }
}
