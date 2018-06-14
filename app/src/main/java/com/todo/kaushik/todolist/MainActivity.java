package com.todo.kaushik.todolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoAdapter.ClickEvent{

MyDatabase database;

    RecyclerView recyclerView;
    TodoAdapter todoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.RecycleView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL ,false);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddTodoActivity.class);
                startActivity(intent);
                Snackbar.make(view, "Going to Details", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        todoAdapter =new TodoAdapter(this,this);

        database = MyDatabase.getInstance(this);
        retrieveTasks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void callDetails(int position) {

        Intent intent = new Intent(this,AddTodoActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);


    }
}
