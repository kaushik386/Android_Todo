package com.todo.kaushik.todolist;



import android.arch.persistence.room.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {TodoTask.class},version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class MyDatabase extends RoomDatabase {


    public static final String DATABASE = "tasks";
    public static MyDatabase mInstance;
    private static final Object LOCK = new Object();
    public  abstract TaskDao taskDao();

    public static MyDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCK) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(),MyDatabase.class,MyDatabase.DATABASE).build();
            }
        }
        return  mInstance;
    }




}
