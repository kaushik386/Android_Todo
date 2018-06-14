package com.todo.kaushik.todolist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class AddViewModelFactory extends ViewModelProvider.NewInstanceFactory  {

    private final MyDatabase myDatabase;
    private  final int mTaskID;

    public AddViewModelFactory(MyDatabase myDatabase, int mTaskID) {
        this.myDatabase = myDatabase;
        this.mTaskID = mTaskID;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddViewModelLayer(myDatabase,mTaskID);
    }

}
