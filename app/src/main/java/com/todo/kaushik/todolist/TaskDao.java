package com.todo.kaushik.todolist;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY priority ")
     LiveData<List<TodoTask>> getList();
    @Insert
    void insertTodo(TodoTask todoTask);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTodo(TodoTask todoTask);

    @Delete
    void deleteTodo(TodoTask todoTask);

    @Query("SELECT * FROM tasks WHERE id = :id")
    LiveData<TodoTask> loadTaskById(int id);

}
