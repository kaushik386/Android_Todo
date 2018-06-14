package com.todo.kaushik.todolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Date;

public class AddTodoActivity extends AppCompatActivity {

    MyDatabase myDatabase;
    LiveData<TodoTask> liveTodoTask;
    TextView mDescription;
    Button mSaveButton;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        mDescription = (TextView)findViewById(R.id.editTextTaskDescription);
        int position;
        if(getIntent()!=null)
        {
            position = getIntent().getIntExtra("position",-1);
            if(position!=-1)
                {
                    myDatabase = MyDatabase.getInstance(this);

                    AddViewModelFactory addViewModelFactory = new AddViewModelFactory(myDatabase,position);
                    liveTodoTask= ViewModelProviders.of(this,addViewModelFactory).get(AddViewModelLayer.class).getTodoTaskLiveData();
                    liveTodoTask.observe(this, new Observer<TodoTask>() {
                        @Override
                        public void onChanged(@Nullable TodoTask todoTask) {
                            liveTodoTask.removeObserver(this);
                            fillView(todoTask);
                        }
                    });

                }

        }

    }
    public int getPriorityFromViews() {
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radButton1:
                priority = PRIORITY_HIGH;
                break;
            case R.id.radButton2:
                priority = PRIORITY_MEDIUM;
                break;
            case R.id.radButton3:
                priority = PRIORITY_LOW;
        }
        return priority;
    }

    /**
     * setPriority is called when we receive a task from MainActivity
     *
     * @param priority the priority value
     */
    public void setPriorityInViews(int priority) {
        switch (priority) {
            case PRIORITY_HIGH:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
                break;
            case PRIORITY_MEDIUM:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
            case PRIORITY_LOW:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton3);
        }
    }

    void fillView(TodoTask todoTask)
    {
        mDescription.setText(todoTask.getDescription());
        setPriorityInViews(todoTask.getPriority());

        mSaveButton = (Button)findViewById(R.id.saveButton);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }
    public void onSaveButtonClicked() {
        String description = mDescription.getText().toString();
        int priority = getPriorityFromViews();
        Date date = new Date();

        final TodoTask task = new TodoTask(description, priority, date);


    }
}
