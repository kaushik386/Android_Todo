package com.todo.kaushik.todolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class MainActivity extends AppCompatActivity implements TodoAdapter.ClickEvent{

MyDatabase database;

    RecyclerView recyclerView;
    TodoAdapter todoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set the RecyclerView to its corresponding view
        recyclerView = findViewById(R.id.recycleView);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        todoAdapter = new TodoAdapter(this, this);
        recyclerView.setAdapter(todoAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        recyclerView.addItemDecoration(decoration);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<TodoTask> todoTasks = todoAdapter.getmTodoList();
                        TodoTask task = todoTasks.get(position);
                        database.taskDao().deleteTodo(task);

                    }
                });


            }
        }).attachToRecyclerView(recyclerView);
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddTodoActivity.class);
                startActivity(intent);
            }
        });

        database = MyDatabase.getInstance(this);
        retrieveTasks();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void retrieveTasks() {
        MainActivityModelLayer mainActivityModelLayer = ViewModelProviders.of(this).get(MainActivityModelLayer.class);
        LiveData<List<TodoTask>> tasks = mainActivityModelLayer.gettodos();
        tasks.observe(this, new Observer<List<TodoTask>>() {
            @Override
            public void onChanged(@Nullable List<TodoTask> taskEntries) {
                todoAdapter.swap(taskEntries);
            }
        });
    }


    @Override
    public void callDetails(int position) {

        Intent intent = new Intent(this,AddTodoActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);


    }
}
